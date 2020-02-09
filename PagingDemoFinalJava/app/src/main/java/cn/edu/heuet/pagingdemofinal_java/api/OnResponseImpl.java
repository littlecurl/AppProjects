package cn.edu.heuet.pagingdemofinal_java.api;

import android.util.Log;

import java.util.List;

import cn.edu.heuet.pagingdemofinal_java.data.RepoBoundaryCallback;
import cn.edu.heuet.pagingdemofinal_java.db.GithubLocalCache;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;

import static cn.edu.heuet.pagingdemofinal_java.config.ConstantConfig.NETWORK_PAGE_SIZE;

/**
 * @ClassName OnResponseImpl
 * @Author littlecurl
 * @Date 2020/1/8 15:27
 * @Version 1.0.0
 * @Description 此类主要处理成功回调时下一页的问题
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
        if (RepoBoundaryCallback.hasMore) {
            cache.insert(repos);
        }
        // 设置当前页 +1,注意这里设置静态值需要使用 类名.静态值
        // 否则使用new对象，然后调用对象的Setter方法是不管用的，
        // 会导致无法加载下一页
        // 另外，只有当请求的数量够数之后，才让页数 + 1，否则会漏查数据
        if (repos.size() == NETWORK_PAGE_SIZE ) {
            RepoBoundaryCallback.lastRequestedPage = RepoBoundaryCallback.lastRequestedPage + 1;
            Log.d(TAG, "设置当前页 +1---" + RepoBoundaryCallback.lastRequestedPage);
        }else{
            RepoBoundaryCallback.hasMore = false;
            Log.d(TAG, "请求到的数量为：" + repos.size() + " 不够：" + NETWORK_PAGE_SIZE + " 没有递增下一页");
        }
        RepoBoundaryCallback.isRequestInProgress = false;

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
