package com.mousycoder.biz.imp;

import com.mousycoder.biz.QueryBiz;
import com.mousycoder.middle.model.Student;
import com.mousycoder.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 5:32 PM
 */
public class QueryBizImpl implements QueryBiz {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBizImpl.class);

    @Override
    public List queryList(int count) {
        SqlSession middle = SqlSessionUtil.getSqlSession("middle");
        List<Object> objects = null;
        try {
            objects = middle.selectList("com.mousycoder.middle.mapper.StudentMapper.selectList", count);
        } catch (Exception e) {
            LOGGER.error("查询发生异常", e);
        } finally {
            middle.close();
        }
        return objects;
    }

    @Override
    public int modifyListStatus(List data, String status) {
        List<Student> students = data;
        students.forEach(student->{
            student.setDataStatus(status);
            SqlSession middle = SqlSessionUtil.getSqlSession("middle");
            try {
                middle.update("com.mousycoder.middle.mapper.StudentMapper.updateByPrimaryKey",student);
                middle.commit();
            }catch (Exception e){
                LOGGER.error("修改状态发生异常", e);
            } finally {
                middle.close();
            }

        });
        return 0;
    }
}
