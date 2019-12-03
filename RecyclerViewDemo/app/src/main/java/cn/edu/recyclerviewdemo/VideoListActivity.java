package cn.edu.recyclerviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    private List<String> videoPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initVideoPath();
        // 拿到布局
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // 规定两列显示
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        // 调用VideoAdapter构造方法，初始化适配器（数据与视图的绑定都写在适配器中）
        VideoAdapter adapter = new VideoAdapter(videoPathList);
        recyclerView.setAdapter(adapter);
    }

    private void initVideoPath() {
        /*
        目前这里的视频地址都是写死在这里的
        如果想拿服务器返回的数据的话
        可以使用okhttp或者Ion这样的第三方库拿到Json数据
        然后再用Gson或者FastJson去解析Json，
        最终拿到url数据
     */
        // 假装这里有okhttp的代码
        // TODO：okhttp请求后台

        // 假装这里有Gson解析Json的代码
        //  TODO：Gson解析Json

        // 经过前两步的复杂流程，终于拿到url数据了
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        videoPathList.add("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
    }

}
