package cn.edu.heuet.getcomment;

public class NewsList {
    private String newsTitle;
    private String newsHref;

    public NewsList(String newsTitle, String newsHref) {
        this.newsTitle = newsTitle;
        this.newsHref = newsHref;
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "newsTitle='" + newsTitle + '\'' +
                ", newsHref='" + newsHref + '\'' +
                '}';
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsHref() {
        return newsHref;
    }

    public void setNewsHref(String newsHref) {
        this.newsHref = newsHref;
    }
}
