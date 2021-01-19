package cn.edu.heuet.demo.room.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private Executor mIOExecutor;

    public AppExecutors() {
        mIOExecutor = Executors.newSingleThreadExecutor();
    }

    public Executor getDiskIO() {
        return mIOExecutor;
    }

}