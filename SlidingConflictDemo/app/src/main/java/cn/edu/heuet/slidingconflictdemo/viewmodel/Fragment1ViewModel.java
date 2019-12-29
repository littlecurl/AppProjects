package cn.edu.heuet.slidingconflictdemo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.slidingconflictdemo.bean.ArticleItem;
/**
 * ViewModel 为Activity或Fragment提供数据
 */
public class Fragment1ViewModel extends AndroidViewModel {
    public Fragment1ViewModel(@NonNull Application application) {
        super(application);
    }

    public List<String> getImages() {
        List<String> images = new ArrayList<>();
        images.add("https://i0.hdslb.com/bfs/album/cbb7371630af6e28aa83ac4110791b7d1c3a0873.png");
        images.add("https://i0.hdslb.com/bfs/album/cbb7371630af6e28aa83ac4110791b7d1c3a0873.png");
        return images;
    }

    public List<String> getTitles(){
        List<String> titles = new ArrayList<>();
        titles.add("1");
        titles.add("asdhasiudh");
        return titles;
    }

    public List<ArticleItem> getArticleItems(){
        List<ArticleItem> articleItems = new ArrayList<>();
        String thumbnail = "https://i0.hdslb.com/bfs/album/76a9eb87af79bd3d9c17019399c6ca3560fb7169.png";
        String summary = "点击阅读详情";
        for (int i=0; i< 20; i++) {
            articleItems.add(new ArticleItem(thumbnail, summary));
        }
        return articleItems;
    }


}
