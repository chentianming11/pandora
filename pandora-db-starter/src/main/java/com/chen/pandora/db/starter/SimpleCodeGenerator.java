package com.chen.pandora.db.starter;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
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

    protected static final String PROJECT_PATH = System.getProperty("user.dir");
    protected static final String SUPPER_MAPPER_CLASS = "com.chen.pandora.db.starter.mapper.ExtendMapper";
    protected static final String SUPPER_SERVICE_CLASS = "com.chen.pandora.db.starter.service.ExtendService";

    /**
     * 数据库驱动 默认mysql
     */
    protected String driverName = "com.mysql.cj.jdbc.Driver";

    /**
     * 包名前缀
     */
    protected String packagePrefix;

    /**
     * 数据库url
     */
    protected String url;

    /**
     * 数据库用户名
     */
    protected String username;

    /**
     * 数据库密码
     */
    protected String password;


    /**
     * 表前缀
     */
    protected String tablePrefix;

    /**
     * 字段前缀前缀
     */
    protected String fieldPrefix;

    /**
     * 项目名称
     */
    protected String projectName;

    /**
     * 逻辑删除字段名称
     */
    protected String logicDeleteFieldName;

    /**
     * 乐观锁属性名称
     */
    protected String versionFieldName;

    /**
     * 表填充字段
     */
    protected List<TableFill> tableFillList = new ArrayList<>();

    /**
     * 开启 swagger2 模式 默认false
     */
    protected boolean swagger2 = false;

    public final void execute(){
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
            }
        };
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return PROJECT_PATH + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        fileOutConfigHook(focList, moduleName);
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }

    /**
     * 自定义文件输出钩子
     * @param focList
     * @param moduleName
     */
    protected void fileOutConfigHook(List<FileOutConfig> focList, String moduleName){

    }

    /**
     * 策略配置
     */
    private void setStrategyConfig(AutoGenerator mpg){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setEntityBuilderModel(true);
        // 字段注解默认打开，目前判断实体是否转换有bug，不开启的话，会导致转换后的字段不会自动生成对应的tableField
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        if (!Strman.isBlank(tablePrefix)){
            strategy.setTablePrefix(tablePrefix);
        }
        if (!Strman.isBlank(fieldPrefix)){
            strategy.setTablePrefix(fieldPrefix);
        }
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperMapperClass(SUPPER_MAPPER_CLASS);
        strategy.setSuperServiceImplClass(SUPPER_SERVICE_CLASS);
        strategy.setInclude(scanner("表名").split(","));
        strategy.setLogicDeleteFieldName(logicDeleteFieldName);
        strategy.setVersionFieldName(versionFieldName);
        strategy.setTableFillList(tableFillList);
        strategyConfigHook(strategy);
        mpg.setStrategy(strategy);
    }

    /**
     * 策略配置钩子
     * @param strategy
     */
    protected void strategyConfigHook(StrategyConfig strategy) {

    }


    /**
     * 模板配置
     */
    private void setTemplateConfig(AutoGenerator mpg) {
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        TemplateConfig templateConfig = new TemplateConfig();
        // 不生成controller和service接口
        templateConfig.setController(null);
        templateConfig.setService(null);
        // 不使用模板配置生成xml
        templateConfig.setXml(null);
        templateConfigHook(templateConfig);
        mpg.setTemplate(templateConfig);

    }

    /**
     * 模板配置钩子
     * @param templateConfig
     */
    protected void templateConfigHook(TemplateConfig templateConfig) {

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
        packageHook(pc, moduleName);
        mpg.setPackageInfo(pc);
    }

    /**
     * 包名配置钩子
     * @param pc
     * @param moduleName
     */
    protected void packageHook(PackageConfig pc, String moduleName) {

    }


    /**
     * 数据源配置
     * @param mpg
     */
    private void setDataSource(AutoGenerator mpg) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dataSourceHook(dsc);
        mpg.setDataSource(dsc);
    }


    /**
     * 数据源配置钩子
     * @param dsc
     */
    protected void dataSourceHook(DataSourceConfig dsc) {

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
        gc.setAuthor("pandora");
        gc.setIdType(IdType.AUTO);
        gc.setSwagger2(swagger2);
        gc.setServiceImplName("%sService");
        globalConfigHook(gc);
        mpg.setGlobalConfig(gc);
    }

    /**
     * 全局配置钩子
     * 子类可以GlobalConfig进行自定义配置
     * @param gc
     */
    protected void globalConfigHook(GlobalConfig gc) {

    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private String scanner(String tip) {
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
