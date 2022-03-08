package com.mousycoder.thread;

import com.mousycoder.biz.QueryBiz;
import com.mousycoder.constant.DataConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 5:29 PM
 */
public class Producer implements Runnable {

    public static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private QueryBiz queryBiz;

    private LinkedBlockingQueue<Runnable> consumers;

    private ThreadPoolExecutor executor;

    public Producer(QueryBiz queryBiz, LinkedBlockingQueue<Runnable> consumers, ThreadPoolExecutor executor) {
        this.queryBiz = queryBiz;
        this.consumers = consumers;
        this.executor = executor;
    }

    @Override
    public void run() {
        while (true) {
            List list = queryBiz.queryList(10);
            try {
                if (list != null && list.size() > 0) {
                    System.out.println("生产者处理");
                    queryBiz.modifyListStatus(list, DataConstant.DEALING);
                    Consumer consumer = (Consumer) consumers.take();
                    consumer.setList(list);
                    executor.execute(consumer);
                } else {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                LOGGER.error("生产者异常 --> ", e);
                queryBiz.modifyListStatus(list, DataConstant.ERROR);
            }


        }
    }
}
