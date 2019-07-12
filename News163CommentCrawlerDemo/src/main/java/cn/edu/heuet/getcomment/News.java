package cn.edu.heuet.getcomment;

public class News {
    private String title;
    private String content;
    private String commentUrl;
    private String commentCount;


    public News(String title, String content, String commentUrl,String commentCount) {
        this.title = title;
        this.content = content;
        this.commentUrl = commentUrl;
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", commentUrl='" + commentUrl + '\'' +
                ", commentCount='"+commentCount+'\''+
                '}';
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }
}
