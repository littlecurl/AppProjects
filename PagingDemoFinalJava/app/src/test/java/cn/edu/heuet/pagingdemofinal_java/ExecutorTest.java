package cn.edu.heuet.pagingdemofinal_java;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ExecutorTest
 * @Author littlecurl
 * @Date 2020/1/11 10:51
 * @Version 1.0.0
 * @Description TODO
 */
public class ExecutorTest {
    static class ThreadA implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
            }
        }
    }
    static class ThreadB implements Runnable {
        @Override
        public void run() {
            for (int i = 100; i > 0; i--) {
                System.out.println(i);
            }
        }
    }


    @Test
    public void singleThreadExecutorTest() throws InterruptedException {
        // SingleThreadExecutor 保证顺序执行
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Thread(new ThreadA()));
        executor.execute(new Thread(new ThreadB()));
    }

    @Test
    public void simpleThreadRunTest() {
        new ThreadA().run();
        new ThreadB().run();
    }

}
