package com.mousycoder.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 5:09 PM
 */
public class SqlSessionUtil {

    private static SqlSessionFactory outSqlSessionFactory;

    private static SqlSessionFactory middleSqlSessionFactory;

    private static final String OUR = "our";

    public static final String MIDDLE_XML = "mybatis-config-middle.xml";

    public static final String OUR_XML = "mybatis-config-our.xml";

    static {

        Reader middleResourceAsReader = null;
        Reader ourResourceAsReader = null;
        try {
            middleResourceAsReader = Resources.getResourceAsReader(MIDDLE_XML);
            ourResourceAsReader = Resources.getResourceAsReader(OUR_XML);

            middleSqlSessionFactory = new SqlSessionFactoryBuilder().build(middleResourceAsReader);
            outSqlSessionFactory = new SqlSessionFactoryBuilder().build(ourResourceAsReader);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                middleResourceAsReader.close();
                ourResourceAsReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SqlSession getSqlSession(String code) {
        if (code.equals(OUR)) {
            return outSqlSessionFactory.openSession();
        }
        return middleSqlSessionFactory.openSession();
    }


}
