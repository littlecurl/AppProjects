package cn.edu.heuet.pagingdemofinal_java.ui;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import cn.edu.heuet.pagingdemofinal_java.data.GithubDataSource;

/**
 * @ClassName ViewModelFactory
 * @Author littlecurl
 * @Date 2020/1/8 14:26
 * @Version 1.0.0
 * @Description TODO
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private GithubDataSource githubDataSource;
    private Application application;
    /*
    构造Factory
     */
    public ViewModelFactory(Application application, GithubDataSource githubDataSource) {
        this.application = application;
        this.githubDataSource = githubDataSource;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel.class)) {
            return (T)new SearchRepositoriesViewModel(application, githubDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
