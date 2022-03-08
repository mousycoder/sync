package com.mousycoder.main;

import com.mousycoder.biz.DealBiz;
import com.mousycoder.biz.QueryBiz;
import com.mousycoder.biz.imp.DealBizImpl;
import com.mousycoder.biz.imp.QueryBizImpl;
import com.mousycoder.thread.Consumer;
import com.mousycoder.thread.Producer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 8:23 PM
 */
public class Main {

    public static void main(String[] args) {
        QueryBiz queryBiz = new QueryBizImpl();
        DealBiz dealBiz = new DealBizImpl();

        LinkedBlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>(10);

        for (int i = 0; i < 10; i++) {
            try {
                runnables.put(new Consumer(dealBiz,runnables));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));

        Producer producer = new Producer(queryBiz, runnables, threadPoolExecutor);
        new Thread(producer).start();




    }
}
