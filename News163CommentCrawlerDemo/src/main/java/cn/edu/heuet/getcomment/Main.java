package cn.edu.heuet.getcomment;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.event.Events;
import io.webfolder.cdp.event.network.ResponseReceived;
import io.webfolder.cdp.listener.EventListener;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.network.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.webfolder.cdp.event.Events.NetworkResponseReceived;
import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        // 程序执行开始时间
        long startTime = System.currentTimeMillis();

        // 获得新闻列表
        List<NewsList> newsList = getNewsListFromUrl("https://news.163.com/domestic/");

        List<News> newsDetailList = new ArrayList<>(70);
        for (NewsList newslist : newsList) {
            // 这条打印新闻列表保留在这里，每打印一条，代表拿到一篇新闻
            System.out.println(newslist.toString());
            // 根据新闻列表中的每一个url获得新闻详细信息
            News newsDetail = getNewsDetailFromUrl(newslist.getNewsHref());
            newsDetailList.add(newsDetail);
            // 空值获取的数量
            if (newsDetailList.size() == 20) {
                break;
            }
        }

        for (News newsDetail : newsDetailList) {
            // 从新闻详细页面获取评论链接
            String commentUrl = newsDetail.getCommentUrl();
            // 根据评论链接获取评论Json API 地址
            List<String> commentJsonUrlList = getCommentJsonUrlListFromUrl(commentUrl);
            for (String commentJsonUrl : commentJsonUrlList) {
                // 根据评论Json API地址获取对应html内容
                String commentJsonHtml = getCommentsJsonHtmlFromUrl(commentJsonUrl);
                // 解析对应Html内容成为评论
                List<Comment> commentList = getCommentsContentFromHtml(commentJsonHtml);
                for (Comment comment : commentList) {
                    System.out.println(comment.toString());
                }
            }
        }

        // 程序执行结束时间
        long endTime = System.currentTimeMillis();

        System.out.println("获取前20篇新闻评论共耗时：" + (endTime - startTime) / 1000 + " 秒");
    }


    public static List<NewsList> getNewsListFromUrl(String url) {
        Launcher launcher = new Launcher();
        List<NewsList> newsList;
        try (SessionFactory factory = launcher.launch(asList("--disable-gpu", "--headless"))) {
            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {
                // 打开url，并等待Document准备好
                session.navigate(url)
                        .waitDocumentReady();

                newsList = new ArrayList<>(70);
                // session执行xPath，获取文本和属性对应内容
                for (int i = 0; i < 70; i++) {
                    String titleText = session.getText("//div[@class='ndi_main']/div[@i='" + i + "']/div/div/h3/a");
                    String titleHref = session.getAttribute("//div[@class='ndi_main']/div[@i='" + i + "']/div/div/h3/a", "href");
                    newsList.add(new NewsList(titleText, titleHref));
                }
            }
            // 处理浏览器上下文，源码：contexts.remove(browserContextId)
            factory.disposeBrowserContext(context);
        }
        // 关闭后台进程，由于历史原因，关闭进程习惯使用kill
        launcher.getProcessManager().kill();
        return newsList;
    }

    public static News getNewsDetailFromUrl(String url) {
        Launcher launcher = new Launcher();
        News news;
        try (SessionFactory factory = launcher.launch(asList("--disable-gpu", "--headless"))) {
            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {
                // 打开url，并等待Document准备好
                session.navigate(url)
                        .waitDocumentReady();
                String newsTitle = session.getText("//div[@class='post_content_main']/h1");
                String newsContent = session.getText("//div[@class='post_text']");
                String newsCommentUrl = session.getAttribute("//a[@class='js-tiecount js-tielink']", "href");
                String newsCommentCount = session.getText("//a[@class='js-tiecount js-tielink']");
                news = new News(newsTitle, newsContent, newsCommentUrl, newsCommentCount);
            }
            // 处理浏览器上下文，源码：contexts.remove(browserContextId)
            factory.disposeBrowserContext(context);
        }
        // 关闭后台进程，由于历史原因，关闭进程习惯使用kill
        launcher.getProcessManager().kill();
        return news;
    }

    public static List<String> getCommentJsonUrlListFromUrl(final String url) {
        Launcher launcher = new Launcher();
        List<String> commentJsonUrlList = new ArrayList<>();
        try (SessionFactory factory = launcher.launch(asList("--disable-gpu", "--headless"))) {
            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {
                // 连通网络
                session.getCommand().getNetwork().enable();
                // 事件监听器
                session.addEventListener(
                        new EventListener() {
                            @Override
                            public void onEvent(Events event, Object value) {
                                if (NetworkResponseReceived.equals(event)) {
                                    ResponseReceived rr = (ResponseReceived) value;
                                    Response response = rr.getResponse();
                                    // 从response对象中获得url
                                    String commentJsonUrl = response.getUrl();
                                    // 正则匹配，检测网站 https://regex101.com 匹配3条约用时2ms
                                    Pattern pattern = Pattern.compile(".*List\\?ibc.*");
                                    Matcher matcher = pattern.matcher(commentJsonUrl);
                                    if (matcher.find()) {
                                        commentJsonUrlList.add(commentJsonUrl);
                                    }
                                }
                            }
                        });
                // 监听器写在导航之前
                // 一定要有连接超时设置,且不小于2s（多次测试得出结论，具体时间和网速有关系），否则无法获取
                session.navigate(url).wait(4 * 1000);
            }
            // 处理浏览器上下文，源码：contexts.remove(browserContextId)
            factory.disposeBrowserContext(context);
        }
        // 关闭后台进程，由于历史原因，关闭进程习惯使用kill
        launcher.getProcessManager().kill();
        return commentJsonUrlList;
    }

    /**
     * 此方法未实现分页，
     * 可以通过sendKeys，模拟点击事件实现分页
     * <p>
     * 另外，此方法目前会得到两个hotList和一个newList的api链接，
     * 这里面包含重复的内容，需要去重！
     *
     * @param url
     * @return
     */
    public static String getCommentsJsonHtmlFromUrl(String url) {
        String commentsJsonHtml = "";
        Launcher launcher = new Launcher();
        try (SessionFactory factory = launcher.launch(asList("--disable-gpu", "--headless"))) {
            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {
                session.navigate(url);
                // 默认超时时间是10*1000
                session.waitDocumentReady(15 * 1000);
                commentsJsonHtml = session.getContent();
            }
            // 处理浏览器上下文，源码：contexts.remove(browserContextId)
            factory.disposeBrowserContext(context);
        }
        // 关闭后台进程，由于历史原因，关闭进程习惯使用kill
        launcher.getProcessManager().kill();
        return commentsJsonHtml;
    }

    public static List<Comment> getCommentsContentFromHtml(String commentsHtml) {
        List<Comment> comments = new ArrayList<Comment>();
        String getcontent = Jsoup.parse(commentsHtml).select("pre").text();
        int length = getcontent.length();
        int beginIndex = getcontent.indexOf("(") + 1;
        String strJsonData = getcontent.substring(beginIndex, length - 2);

        String str;
        HashSet<String> set = new HashSet<String>();
        JSONObject json = new JSONObject(strJsonData);

        JSONArray arrayIds = json.getJSONArray("commentIds");
        for (Object obj : arrayIds) {
            str = obj.toString();
            String[] ids = str.split(",");
            for (String s : ids) {
                set.add(s);
            }
        }


        JSONObject arrayments = json.getJSONObject("comments");
        JSONObject jobj;
        for (String s : set) {
            jobj = arrayments.getJSONObject(s);
            Comment comment = new Comment();

            Object o = jobj.get("content");
            if (o != null) {
                comment.setContent(o.toString());
            }
            o = jobj.get("createTime");
            if (o != null) {
                comment.setTime(o.toString());
            }

            o = jobj.get("anonymous");
            if (o != null) {
                if (o.toString().equals("false")) {
                    o = jobj.get("user");
                    JSONObject j = new JSONObject(o.toString());
                    comment.setAuthor(j.getString("nickname"));
                } else {
                    comment.setAuthor("anonymous");
                }
            }

            comments.add(comment);

        }
        return comments;
    }
}