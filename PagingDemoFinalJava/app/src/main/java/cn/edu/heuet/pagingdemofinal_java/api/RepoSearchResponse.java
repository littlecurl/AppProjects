package cn.edu.heuet.pagingdemofinal_java.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.pagingdemofinal_java.model.Repo;

/**
 * @ClassName RepoSearchResponse
 * @Author littlecurl
 * @Date 2020/1/8 10:27
 * @Version 1.0.0
 * @Description Data class to hold repo responses from searchRepo API calls.
 */
public class RepoSearchResponse {

    @SerializedName("total_count")
    int total = 0;

    @SerializedName("items")
    List<Repo> items = new ArrayList<>();

    int nextPage;
}
