package com.imooc.ecommerce.conf;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author Guank
 * @version 1.0
 * @description: Seata 所需要的数据源代理
 * @date 2022/6/19 14:20
 */
@Configuration
public class DataSourceProxyAutoConfiguration {

    private final DataSourceProperties dataSourceProperties;

    public DataSourceProxyAutoConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    /**
     * 配置 Seata 的数据源代理 用于全局事务的回滚
     * Seata 记录一次全局事务的 before image + after image -> undo log
     * @return
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());

        return new DataSourceProxy(dataSource);
    }

}
