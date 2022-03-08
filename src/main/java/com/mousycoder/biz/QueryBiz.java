package com.mousycoder.biz;

import java.util.List;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 5:30 PM
 */
public interface QueryBiz {

    List queryList(int count);

    int modifyListStatus(List data, String status);
}
