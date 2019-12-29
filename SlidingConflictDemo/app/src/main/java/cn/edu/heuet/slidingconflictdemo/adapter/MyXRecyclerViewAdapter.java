package cn.edu.heuet.slidingconflictdemo.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

import cn.edu.heuet.slidingconflictdemo.R;
import cn.edu.heuet.slidingconflictdemo.bean.ArticleItem;

/**
 * @ClassName MyXRecyclerViewAdapter
 * @Author littlecurl
 * @Date 2019/12/26 16:50
 * @Version 1.0.0
 * @Description RecyclerView的适配器
 *
 * 构造方法传入RecyclerView的引入和需要绑定的数据
 * 构造方法内部写上RecyclerView每一项的布局R.layout.item_article_cardview
 * bindData()方法进行数据绑定
 *
 * 更多详情参考：https://github.com/youth5201314/XFrame/wiki/XRecyclerViewAdapter%E8%AF%A6%E8%A7%A3
 */
public class MyXRecyclerViewAdapter extends XRecyclerViewAdapter<ArticleItem> {

    public MyXRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView, List<ArticleItem> dataLists) {
        super(mRecyclerView, dataLists, R.layout.item_article_cardview);
    }

    @Override
    protected void bindData(XViewHolder holder, ArticleItem data, int position) {
        holder.setImageUrl(R.id.iv_thumbnail, data.getThumbnail());
        holder.setText(R.id.tv_summary, data.getSummary());
    }
}
