package cn.edu.heuet.pagingdemofinal_java.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import cn.edu.heuet.pagingdemofinal_java.api.GitHubClient;
import cn.edu.heuet.pagingdemofinal_java.db.GithubLocalCache;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;

import static cn.edu.heuet.pagingdemofinal_java.config.ConstantConfig.NETWORK_PAGE_SIZE;

/**
 * @ClassName RepoBoundaryCallback
 * @Author littlecurl
 * @Date 2020/1/8 13:01
 * @Version 1.0.0
 * @Description TODO
 */
public class RepoBoundaryCallback extends PagedList.BoundaryCallback<Repo> {
    private String query;

    private static final String TAG = "RepoBoundaryCallback";

    // keep the last requested page. When the request is successful, increment the page number.
    public static int lastRequestedPage = 1;
    // avoid triggering multiple requests in the same time
    public static boolean isRequestInProgress = false;


    // LiveData of network errors.
    public static MutableLiveData<String> networkErrors = new MutableLiveData<String>();

    private GithubLocalCache cache;

    public RepoBoundaryCallback(GithubLocalCache cache, String query) {
        this.cache = cache;
        this.query = query;
    }

    /*
        一开始，PagedList为空，RecyclerView想要渲染数据时，触发此方法
        相当于初始化数据，获取第一页数据
     */
    @Override
    public void onZeroItemsLoaded() {
        Log.d(TAG, "onZeroItemsLoaded");
        requestAndSaveData(query);
    }

    /*
        RecyclerView渲染PagedList中最后一项数据时（不算prefetch），触发此方法
        相当于上拉加载
    */
    @Override
    public void onItemAtEndLoaded(Repo itemAtEnd) {
        Log.d(TAG, "onItemAtEndLoaded");
        Log.d(TAG, "query：" + query);
        requestAndSaveData(query);
    }

    /*
    ViewModel请求
     */
    public void requestMore(String query) {
        requestAndSaveData(query);
    }

    private void requestAndSaveData(String query) {
        // 避免同一时刻重复请求
        if (isRequestInProgress) return;
        isRequestInProgress = true;
        // retrofit对象实例化，请求网络数据并插入到数据库中
        GitHubClient.getInstance().searchRepos(
                cache,
                query,
                lastRequestedPage,
                NETWORK_PAGE_SIZE
        );
        Log.d(TAG, "NETWORK_PAGE_SIZE： " + NETWORK_PAGE_SIZE);
    }

}
