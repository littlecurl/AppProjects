package cn.edu.heuet.pagingdemofinal_java.ui;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import cn.edu.heuet.pagingdemofinal_java.data.GithubDataSource;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;
import cn.edu.heuet.pagingdemofinal_java.model.RepoSearchResult;

/**
 * @ClassName SearchRepositoriesViewModel
 * @Author littlecurl
 * @Date 2020/1/8 13:57
 * @Version 1.0.0
 * @Description TODO
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class SearchRepositoriesViewModel extends AndroidViewModel {
    private GithubDataSource githubDataSource;
    private MutableLiveData<String> queryLiveData = new MutableLiveData<String>();

    /**
     * 构造方法
     *
     * @param application
     * @param githubDataSource
     */
    public SearchRepositoriesViewModel(@NonNull Application application, GithubDataSource githubDataSource) {
        super(application);
        this.githubDataSource = githubDataSource;
    }

    // Activity.onCreate()中调用
    // 将用户输入的String转为LiveDat<String>
    public void searchRepo(String queryString) {
        queryLiveData.postValue(queryString);
    }

    // 传入LiveData<String> 拿到查询结果
    private LiveData<RepoSearchResult> repoResult = Transformations.map(
            queryLiveData,
            it -> githubDataSource.search(it)
    );

    // 取出repoResult中的PagedList
    public LiveData<PagedList<Repo>> repos = Transformations.switchMap(
            repoResult,
            it -> it.data
    );

    // 取出repoResult中的networkErrors
    public LiveData<String> networkErrors = Transformations.switchMap(
            repoResult,
            it -> it.networkErrors
    );

    // Activity.onSaveInstanceState()中调用
    public String lastQueryValue() {
        return queryLiveData.getValue();
    }


}
