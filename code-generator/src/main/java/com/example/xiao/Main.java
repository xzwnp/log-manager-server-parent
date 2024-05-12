package com.example.xiao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import org.apache.ibatis.annotations.Mapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        String moduleName = "log-manager-server-service-base/log-manager-server-statistic";
        String packageName = "com.example.xiao.logmanager.server.statistic";
        String author = "xiaozhiwei";

        String url = "jdbc:mysql://localhost:3306/log_manager_server_statistic?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&useServerPrepStmts=true";
        String username = "root";
        String password = "x.z.w.91";

        String schema = "log_manager_server_statistic";
        List<String> tables = List.of(".*");


        String modulePath = projectPath + "/" + moduleName;
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    String javaFilePath = modulePath + "/src/main/java";
                    builder.author(author) // 设置作者
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir(javaFilePath); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder
                        .schema(schema)
                        .keyWordsHandler(new MySqlKeyWordsHandler())
                        .build()
                )
                .packageConfig(builder -> {
                    String mapperXmlPath = modulePath + "/src/main/resources/xml";
                    builder.parent(packageName) // 设置父包名
                            .entity("entity.po")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("dao")
                            .xml("mapper")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXmlPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                            //实体类配置
                            .entityBuilder()
                            .enableFileOverride() //覆盖已生成文件
                            .disableSerialVersionUID() //禁用生成 serialVersionUID
                            .enableChainModel()
                            .enableLombok()
                            .enableRemoveIsPrefix() //列转为属性时去掉is前缀
                            .logicDeleteColumnName("delete_flag")
                            .naming(NamingStrategy.underline_to_camel)
                            .addTableFills(new Column("created_time", FieldFill.INSERT))
                            .addTableFills(new Column("updated_time", FieldFill.INSERT_UPDATE))
                            .addTableFills(new Column("creator_id", FieldFill.INSERT))
                            .addTableFills(new Column("updater_id", FieldFill.INSERT_UPDATE))
                            .idType(IdType.AUTO)
                            .formatFileName("%sPo")
                            //mapper配置
                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class)
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatMapperFileName("%sDao")
                            .formatXmlFileName("%sDao")
                            //service配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
		
    }
}