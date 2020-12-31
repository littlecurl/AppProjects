package cn.edu.heuet.getcomment;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class GetPDDPictures {

    public static void main(String[] args) throws Exception {

        Launcher launcher = new Launcher();
        try (SessionFactory factory = launcher.launch(asList("--disable-gpu",
                "--headless"))) {
            String context = factory.createBrowserContext();
            try (Session session = factory.create(context)) {
                // 设置要爬的网站链接，必须要有http://或https://
                String url = args[0];
                session.navigate(url);
                // 默认timeout是10*1000 ms，也可以像下面这样手动设置
                session.waitDocumentReady(15 * 1000);
                // 通过session得到渲染后的html内容
                String html = session.getContent();

//                System.out.println(html);
                downloadImg(html);
            }// session创建结束

            // 处理浏览器上下文，源码：contexts.remove(browserContextId)
            // 意思应该是将后台浏览器进程关闭
            // 我曾经尝试将此举注释，只保留下面的launcher.getProcessManager().kill();
            // 依然可以关闭后台进程，但是官方给的代码有这句，那就带着吧，或许有其他作用。
            factory.disposeBrowserContext(context);
        }// factory创建结束

        // 真正的关闭后台进程
        launcher.getProcessManager().kill();
    }// main方法结束

    private static void downloadImg(String html) throws Exception {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        Pattern storeNamePattern = Pattern.compile("(.+1g9X2Rjz.>)(.+?)(</div>)");
        Matcher storeNameMatcher = storeNamePattern.matcher(html);
        String storeName = "";
        if (storeNameMatcher.find()) {
            /*
                group(0) == 全部
                group(1) == 第一个分组
                group(2) == 第二个分组
             */
            storeName = storeNameMatcher.group(2);
        }
        String desktopPath = fsv.getHomeDirectory().getAbsolutePath() + "/" + storeName + System.currentTimeMillis();
        createDir(desktopPath);
        Pattern pattern = Pattern.compile("//.+?pddpic.com/.*?(a\\.jpeg)");
        Matcher matcher = pattern.matcher(html);
        int name = 0;
        while (matcher.find()) {
            String imgUrl = matcher.group();
            if (imgUrl.contains("https:")) {
                imgUrl = imgUrl.replaceAll("https:", "");
            }
            imgUrl = "https:" + imgUrl;

            System.out.println(imgUrl);

            download(imgUrl, desktopPath + "/" + (name + 1) + ".jpg");
            name++;
        }
    }

    /**
     * 下载文件到本地
     *
     * @param urlString 被下载的文件地址
     * @param filename  本地文件名
     * @throws Exception 各种异常
     */
    public static void download(String urlString, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        OutputStream os = new FileOutputStream(filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        os.close();
        is.close();
    }

    //String dirName = "D:/work/temp/temp0/temp1";
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }
}