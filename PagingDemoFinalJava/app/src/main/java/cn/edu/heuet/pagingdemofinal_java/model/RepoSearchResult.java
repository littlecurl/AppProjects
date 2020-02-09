package cn.edu.heuet.pagingdemofinal_java.model;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

/**
 * @ClassName RepoSearchResult
 * @Author littlecurl
 * @Date 2020/1/8 10:04
 * @Version 1.0.0
 * @Description 注意和RepoSearchResponse的区别
 *
 * RepoSearchResponse 是为了Json反序列化而写的
 * 这里是为了返回给UI使用PagedList、networkErrors数据而写的
 */
public class RepoSearchResult {
    public LiveData<PagedList<Repo>> data;
    public LiveData<String> networkErrors;
    public RepoSearchResult(LiveData<PagedList<Repo>> data, LiveData<String> networkErrors) {
        this.data = data;
        this.networkErrors = networkErrors;
    }
}
