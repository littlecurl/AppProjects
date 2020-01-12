package cn.edu.heuet.pagingdemofinal_java.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import cn.edu.heuet.pagingdemofinal_java.db.GithubLocalCache;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;
import cn.edu.heuet.pagingdemofinal_java.model.RepoSearchResult;

import static cn.edu.heuet.pagingdemofinal_java.config.ConstantConfig.DATABASE_PAGE_SIZE;

/**
 * @ClassName GithubDataSource
 * @Author littlecurl
 * @Date 2020/1/8 12:55
 * @Version 1.0.0
 * @Description TODO
 */
public class GithubDataSource {
    private GithubLocalCache cache;
    public LiveData<PagedList<Repo>> itemPagedList;

    public GithubDataSource(GithubLocalCache cache) {
        this.cache = cache;
    }

    /**
     * Search repositories whose names match the query.
     */
    public RepoSearchResult search(String query) {
        Log.d("GithubDataSource", "New query: " + query);
        // 从本地数据库缓存中查询
        DataSource.Factory<Integer, Repo> dataSourceFactory = cache.reposByName(query);

        // 边界回调
        // every new query creates a new BoundaryCallback
        // The BoundaryCallback will observe when the user reaches to the edges of
        // the list and update the database with extra data
        RepoBoundaryCallback boundaryCallback = new RepoBoundaryCallback(cache, query);
        LiveData<String> networkErrors = RepoBoundaryCallback.networkErrors;

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(2 * DATABASE_PAGE_SIZE)  // 初始加载数据，默认 3*PageSize
                .setPageSize(DATABASE_PAGE_SIZE)  // 每页请求数据量
                .setMaxSize(Integer.MAX_VALUE)  // 保存在内存中的最大数据量
                // 通常会定义为PageSize的好多倍，当超出时，旧页面的数据会被丢弃
                // MaxSize 必须满足 MaxSize >= PageSize + 2 * PrefetchDistance 来避免多次提取数据
                // 如果数据不够2页，则不会被丢弃
                // 如果数据源是PageKeyedDataSource，则此项设置将会被忽略
                // 如果未设置，将不会主动丢弃内存中的数据
                .setPrefetchDistance((int) (0.25 * DATABASE_PAGE_SIZE)) // 距底部还有几条数据时，加载下一页数据
                .setEnablePlaceholders(false) // 是否启用占位符，默认为true，
                // 若为true，则视为固定数量的item，比如PositionalDataSource
                .build();

        // Get the paged list
        itemPagedList = new LivePagedListBuilder(dataSourceFactory, config) // 从本地数据库中拿数据
                .setBoundaryCallback(boundaryCallback) // 如果触发边界回调，再从网络中获取
                .build();
        // Get the network errors exposed by the boundary callback
        return new RepoSearchResult(itemPagedList, networkErrors);
    }


}
