package com.mousycoder.biz.imp;

import com.mousycoder.biz.DealBiz;
import com.mousycoder.constant.DataConstant;
import com.mousycoder.middle.model.Student;
import com.mousycoder.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 7:47 PM
 */
public class DealBizImpl implements DealBiz {

    public static final Logger LOGGER = LoggerFactory.getLogger(DealBizImpl.class);

    @Override
    public void deal(List data) {
        List<com.mousycoder.our.model.Student> students = adapter(data);

        students.forEach(student -> {
            student.setName(student.getName()+"test");
            SqlSession ourSession = SqlSessionUtil.getSqlSession("our");
            try {
                ourSession.insert("com.mousycoder.our.mapper.StudentMapper.insertSelective",student);
                ourSession.commit();
                modifyMiddle(student.getId(),DataConstant.FINISH);
            }catch (Exception e){
                LOGGER.error("处理数据发生异常 " ,e);
                modifyMiddle(student.getId(), DataConstant.ERROR);
            }finally {
                ourSession.close();
            }
        });
    }

    private List<com.mousycoder.our.model.Student> adapter(List<Student> students){
        List<com.mousycoder.our.model.Student>  result = new ArrayList<>();
        students.forEach(stu -> {
            com.mousycoder.our.model.Student student  = new com.mousycoder.our.model.Student();
            student.setDepartment(stu.getDepartment());
            student.setAddTime(stu.getAddTime());
            student.setSex(stu.getSex());
            student.setName(stu.getName());
            student.setAddTime(new Date());
            student.setId(stu.getId());
            result.add(student);
        });
        return result;
    }

    private void modifyMiddle(int id,String status){
        Student student = new Student();
        student.setId(id);
        student.setDataStatus(status);
        student.setDealTime(new Date());

        SqlSession middle = SqlSessionUtil.getSqlSession("middle");
        try {
            middle.update("com.mousycoder.middle.mapper.StudentMapper.updateStatusById",student);
            middle.commit();
        }catch (Exception e){
            LOGGER.error("修改中间表状态失败 " ,e);
        }finally {
            middle.close();
        }

    }
}
