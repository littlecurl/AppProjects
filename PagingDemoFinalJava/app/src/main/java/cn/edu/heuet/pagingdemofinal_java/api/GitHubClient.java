package cn.edu.heuet.pagingdemofinal_java.api;

import android.util.Log;

import java.util.List;

import cn.edu.heuet.pagingdemofinal_java.data.RepoBoundaryCallback;
import cn.edu.heuet.pagingdemofinal_java.db.GithubLocalCache;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;
import cn.edu.heuet.pagingdemofinal_java.model.RepoSearchResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.edu.heuet.pagingdemofinal_java.config.ConstantConfig.BASE_URL;
import static cn.edu.heuet.pagingdemofinal_java.config.ConstantConfig.IN_QUALIFIER;
import static cn.edu.heuet.pagingdemofinal_java.config.ConstantConfig.NETWORK_PAGE_SIZE;

/**
 * Github API communication setup via Retrofit.
 */
public class GitHubClient {

    private static final String TAG = "GitHubClient";

    private static GitHubClient mInstance;
    private Retrofit retrofit;

    /*
    构造方法，用于单例中生成新的对象
     */
    public GitHubClient() {
        retrofit = new Retrofit.Builder()
                .client(getOkhttpClientBuilder().build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /*
    单例模式，实例化
    关键点：方法加synchronized同步修饰，内部判空
     */
    public static synchronized GitHubClient getInstance() {
        if (mInstance == null) {
            mInstance = new GitHubClient();
        }
        return mInstance;
    }

    /*
    返回实例化后的API
    因为此方法在Client类之内，所以使用方法是：
    先单例模式实例化Client对象，再用Client对象调用Client实例化API
     */
    public GitHubApi getApi() {
        return retrofit.create(GitHubApi.class);
    }

    /**
     * 查询
     * @param query 用户输入查询关键字
     * @param page 当前页码
     * @param itemsPerPage 每页item数量
     */
    public void searchRepos(
            GithubLocalCache cache,
            String query,
            int page,
            int itemsPerPage) {

        Log.d(TAG, "query: " + query + ", page: " + page + ", itemsPerPage: " + itemsPerPage);

        String apiQuery = query + IN_QUALIFIER;

        GitHubClient.getInstance()
                .getApi()
                .searchRepos(apiQuery, page, itemsPerPage)
                .enqueue(new Callback<RepoSearchResponse>() {
                    @Override
                    public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {
                        Log.d(TAG, "got a response " + response);
                        if (response.isSuccessful()) {
                            List<Repo> repos = response.body().items;
                            if (repos.size() == NETWORK_PAGE_SIZE){
                                RepoBoundaryCallback.hasMore = true;
                            }
                            if (!RepoBoundaryCallback.hasMore){
                                Log.d(TAG,"NO MORE");
                                return;
                            }
                            // 成功回调
                            OnResponse onResponse = new OnResponseImpl(cache);
                            onResponse.onSuccess(repos);
                        } else {
                            // 失败回调
                            OnResponse onResponse = new OnResponseImpl();
                            String errorMsg = response.errorBody().toString();
                            onResponse.onError(errorMsg == null ? "Unknown error" : errorMsg);
                        }
                    }

                    @Override
                    public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                        Log.d(TAG, "fail to get data");
                        OnResponse onResponse = new OnResponseImpl();
                        onResponse.onFailure("网络请求异常，请检查您的网络");
                    }
                });
    }


    /*
    拦截Http请求并打印日志
     */
    private OkHttpClient.Builder getOkhttpClientBuilder() {
        //日志显示级别。
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

        //http拦截器。
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, message);
            }
        });
        loggingInterceptor.setLevel(level);

        //定制OkHttp。
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient
                .Builder();

        okHttpClientBuilder.addInterceptor(loggingInterceptor);
        return okHttpClientBuilder;
    }
}
