package com.mousycoder.thread;

import com.mousycoder.biz.DealBiz;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 7:35 PM
 */
public class Consumer implements Runnable{

    private List list;

    private DealBiz dealBiz;

    private LinkedBlockingQueue<Runnable> consumers;

    public Consumer(DealBiz dealBiz, LinkedBlockingQueue<Runnable> consumers) {
        this.dealBiz = dealBiz;
        this.consumers = consumers;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        try {
            System.out.println("消费者处理==========");
            dealBiz.deal(list);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                consumers.put(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
