package cn.edu.heuet.slidingconflictdemo.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ArticleItem
 * @Author littlecurl
 * @Date 2019/12/26 16:54
 * @Version 1.0.0
 * @Description RecyclerView中每一项显示的内容的JavaBean
 */
public class ArticleItem implements Serializable {
    private String summary;
    private String thumbnail;

    // 缩略图Url和文章摘要
    public ArticleItem(String thumbnail, String summary) {
        this.thumbnail = thumbnail;
        this.summary = summary;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
