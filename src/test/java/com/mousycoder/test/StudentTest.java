package com.mousycoder.test;

import com.mousycoder.biz.QueryBiz;
import com.mousycoder.biz.imp.QueryBizImpl;
import org.junit.Test;

import java.util.List;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 5:44 PM
 */
public class StudentTest {

    @Test
    public void testList(){
        QueryBiz queryBiz = new QueryBizImpl();
        List list = queryBiz.queryList(2);
        System.out.println(list);
    }

    @Test
    public void testUpdate(){
        QueryBiz queryBiz = new QueryBizImpl();
        List list = queryBiz.queryList(2);
        queryBiz.modifyListStatus(list,"10D");
    }
}
