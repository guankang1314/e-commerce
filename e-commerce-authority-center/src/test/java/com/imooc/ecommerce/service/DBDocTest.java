package com.imooc.ecommerce.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.service
 * @date 2021/12/6 11:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DBDocTest {

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void buildDBDoc() {

        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);

        //生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件的路徑
                .fileOutputDir("D:\\workspace\\imooc\\e-commerce-springcloud")
                //打開目錄
                .openOutputDir(false)
                .fileType(EngineFileType.HTML)
                .produceType(EngineTemplateType.freemarker).build();

        //生成文檔配置，包含自定義版本號，描述
        Configuration configuration = Configuration.builder()
                .version("1.0.0")
                .description("e-commerce-springcloud")
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(getProduceConfig())
                .build();

        //執行生成
        new DocumentationExecute(configuration).execute();
    }

    /**
     * 配置想要生成的表和想要忽略
     * @return
     */
    private ProcessConfig getProduceConfig() {

        //想要忽略的表
        List<String> ignoreTableName = Collections.singletonList("undo_log");
        //忽略表prefix
        List<String> ignorePrefix = Arrays.asList("a","b");
        //忽略表suffix
        List<String> ignoreSuffix = Arrays.asList("_test","_Test");

        return ProcessConfig.builder()
                .designatedTableName(Collections.emptyList())
                .designatedTablePrefix(Collections.emptyList())
                .designatedTableSuffix(Collections.emptyList())
                .ignoreTableName(ignoreTableName)
                .ignoreTablePrefix(ignorePrefix)
                .ignoreTableSuffix(ignoreSuffix)
                .build();
    }
}
