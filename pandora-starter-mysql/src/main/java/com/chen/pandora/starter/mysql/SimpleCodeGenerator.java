package com.chen.pandora.starter.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import lombok.experimental.Accessors;
import strman.Strman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author 陈添明
 * @date 2019/1/27
 */
@Data
@Accessors(chain = true)
public class SimpleCodeGenerator {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * 包名前缀
     */
    private String packagePrefix = "com.ke.jiaoyi";

    /**
     * 数据库url
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;


    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 字段前缀前缀
     */
    private String fieldPrefix;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 逻辑删除字段名称
     */
    private String logicDeleteFieldName = "valid";

    /**
     * 乐观锁属性名称
     */
    private String versionFieldName = "version";




    public void execute(){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String moduleName = scanner("模块名");
        setDataSource(mpg);
        setGlobalConfig(mpg);
        setPackageInfo(mpg, moduleName);
        setTemplateConfig(mpg);
        setStrategyConfig(mpg);
        setInjectionConfig(mpg, moduleName);
        mpg.execute();
    }



    /**
     * 自定义注入配置
     */
    private void setInjectionConfig(AutoGenerator mpg, String moduleName){
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return PROJECT_PATH + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }


    /**
     * 策略配置
     */
    private void setStrategyConfig(AutoGenerator mpg){
        StrategyConfig strategy = new StrategyConfig();
        if (!Strman.isBlank(tablePrefix)){
            strategy.setTablePrefix(tablePrefix + "_");
        }
        if (!Strman.isBlank(fieldPrefix)){
            strategy.setTablePrefix(fieldPrefix + "_");
        }
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperMapperClass("com.chen.pandora.starter.mysql.mapper.MysqlMapper");
        strategy.setSuperServiceImplClass("com.chen.pandora.starter.mysql.service.BaseService");
        strategy.setInclude(scanner("表名").split(","));
        strategy.setEntityLombokModel(true);
        strategy.setEntityBuilderModel(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setLogicDeleteFieldName(logicDeleteFieldName);
        strategy.setVersionFieldName(versionFieldName);
        mpg.setStrategy(strategy);
    }


    /**
     * 模板配置
     */
    private void setTemplateConfig(AutoGenerator mpg) {
        TemplateConfig templateConfig = new TemplateConfig();
        // 不生成controller和service接口
        templateConfig.setController(null);
        templateConfig.setService(null);
        // 不使用模板配置生成xml
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    }


    /**
     * 包名配置
     */
    private void setPackageInfo(AutoGenerator mpg, String moduleName){
        PackageConfig pc = new PackageConfig();
        if (Strman.isBlank(packagePrefix)){
            throw new IllegalArgumentException("包名前缀必须！");
        }
        if (Strman.isBlank(projectName)){
            throw new IllegalArgumentException("项目名称前缀必须！");
        }

        pc.setParent(String.format("%s.%s", packagePrefix, projectName));
        pc.setModuleName(moduleName);
        pc.setServiceImpl("service");
        mpg.setPackageInfo(pc);
    }


    /**
     * 数据源配置
     * @param mpg
     */
    private void setDataSource(AutoGenerator mpg) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);
    }

    /**
     * 全局配置
     * @param mpg
     */
    private void setGlobalConfig(AutoGenerator mpg) {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOpen(false);
        gc.setOutputDir(PROJECT_PATH + "/src/main/java");
        gc.setAuthor("贝壳");
        gc.setIdType(IdType.AUTO);
        gc.setSwagger2(true);
        gc.setServiceImplName("%sService");
        mpg.setGlobalConfig(gc);
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (!Strman.isBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }



}
