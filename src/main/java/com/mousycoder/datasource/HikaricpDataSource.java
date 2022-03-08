package com.mousycoder.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * TODO
 *
 * @author mousycoder
 * @version 1.0
 * @date 2022/3/8 5:04 PM
 */
public class HikaricpDataSource extends UnpooledDataSourceFactory {

    public HikaricpDataSource() {
        this.dataSource = new HikariDataSource();
    }
}
