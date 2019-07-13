package cn.edu.heuet.getcomment;

/**
 * Created by TOSHIBA on 2018/3/21.
 */
public class Comment {
    private String author;//作者
    private String time;//时间
    private String content;//作者
    private String location;//位置

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
