package cn.edu.heuet.pagingdemofinal_java.api;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;

import cn.edu.heuet.pagingdemofinal_java.data.RepoBoundaryCallback;
import cn.edu.heuet.pagingdemofinal_java.db.GithubLocalCache;
import cn.edu.heuet.pagingdemofinal_java.db.RepoDatabase;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;

/**
 * @ClassName OnResponseImpl
 * @Author littlecurl
 * @Date 2020/1/8 15:27
 * @Version 1.0.0
 * @Description TODO
 */
public class OnResponseImpl implements OnResponse {

    private GithubLocalCache cache;
    private static final String TAG = "OnResponseImpl";

    public OnResponseImpl() {
    }

    public OnResponseImpl(GithubLocalCache cache) {
        this.cache = cache;
    }

    /*
    请求成功回调
     */
    @Override
    public void onSuccess(List<Repo> repos) {
        Log.d(TAG, "请求成功回调");
        // 本地数据库缓存对象
        // 本地数据库插入返回值
        cache.insert(repos);
        // 设置当前页 +1,注意这里设置静态值需要使用 类名.静态值
        // 否则使用new对象，然后调用对象的Setter方法是不管用的，
        // 会导致无法加载下一页
        RepoBoundaryCallback.lastRequestedPage = RepoBoundaryCallback.lastRequestedPage + 1;
        RepoBoundaryCallback.isRequestInProgress = false;
        Log.d(TAG, "设置当前页 +1---" + RepoBoundaryCallback.lastRequestedPage);
    }

    @Override
    public void onError(String errorMsg) {
        Log.d(TAG, "请求失败回调");
        RepoBoundaryCallback.networkErrors.postValue(errorMsg);
        RepoBoundaryCallback.isRequestInProgress = false;
    }

    @Override
    public void onFailure(String failureMsg) {
        RepoBoundaryCallback.networkErrors.postValue(failureMsg);
    }
}
