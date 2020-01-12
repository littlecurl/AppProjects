package cn.edu.heuet.pagingdemofinal_java.db;

import android.util.Log;

import androidx.paging.DataSource;

import java.util.List;
import java.util.concurrent.Executor;

import cn.edu.heuet.pagingdemofinal_java.model.Repo;

/**
 * @ClassName GithubLocalCache
 * @Author littlecurl
 * @Date 2020/1/8 12:47
 * @Version 1.0.0
 * @Description TODO
 */
public class GithubLocalCache {

    private Executor ioExecutor;
    private RepoDao repoDao;
    private static GithubLocalCache mInstance;

    public static synchronized GithubLocalCache getInstance(Executor ioExecutor, RepoDao repoDao) {
        if (mInstance == null) {
            mInstance = new GithubLocalCache(ioExecutor, repoDao);
        }
        return mInstance;
    }

    public GithubLocalCache(Executor ioExecutor, RepoDao repoDao) {
        this.ioExecutor = ioExecutor;
        this.repoDao = repoDao;
    }

    public void insert(final List<Repo> repos) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("GithubLocalCache", "插入 " + repos.size() + " 条数据");
                try {
                    repoDao.insert(repos);
                } catch (Exception e) {
                    CRUDApi crudApi = new CRUDApiImpl();
                    crudApi.insertFinished("GithubLocalCache: Insert Error");
                }
            }
        });
    }

    /**
     * Request a LiveData<List<Repo>> from the Dao, based on a repo name. If the name contains
     * multiple words separated by spaces, then we're emulating the GitHub API behavior and allow
     * any characters between the words.
     *
     * @param name repository name
     */
    //changed return type to DataSource
    public DataSource.Factory<Integer, Repo> reposByName(String name) {
        // appending '%' so we can allow other characters to be before and after the query string
        String query = "%" + name.replace(' ', '%') + "%";
        return repoDao.reposByName(query);
    }
}
