//package com.itoyoung;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class GenerateMybatisPlus {
//
//    /**
//     * 读取控制台内容
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入一下你的" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotEmpty(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//    public static void main(String[] args) {
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/cloud-provider-payment8001/src/main/java");
//        gc.setAuthor("itoyoung@163.com");
//        gc.setOpen(false);
////        gc.setBaseResultMap(true);
//        gc.setIdType(IdType.UUID);
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://192.168.218.100:3306/db2020?useUnicode=true&characterEncoding=utf-8&useSSL=false");
//        dsc.setDriverName("org.gjt.mm.mysql.Driver ");
//        dsc.setUsername("root");
//        dsc.setPassword("root");
//        mpg.setDataSource(dsc);
//        // 包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setParent("com.itoyoung.springcloud")
//                .setMapper("mapper")
//                .setService("service")
//                .setEntity("entities");
//        mpg.setPackageInfo(pc);
//
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//
//        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                //根据自己的位置修改
//                return projectPath + "/cloud-provider-payment8001/src/main/resources/mapper/" +tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//
//        mpg.setPackageInfo(pc);
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        // 配置自定义输出模板
//        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        // templateConfig.setEntity("templates/entity2.java");
//        // templateConfig.setService();
//        // templateConfig.setController();
//        //此处设置为null，就不会再java下创建xml的文件夹了
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);
//
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        // 表名生成策略(下划线转驼峰命名)
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        // 列名生成策略(下划线转驼峰命名)
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        // 是否启动Lombok配置
//        strategy.setEntityLombokModel(true);
//        // 是否启动REST风格配置
////        strategy.setRestControllerStyle(true);
//        // 自定义实体父类
////        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
//        // 自定义controller父类
////        strategy.setSuperControllerClass("pro.nbbt.base.controller.BaseController");
//        // 自定义service父接口
////        strategy.setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService");
//        // 自定义service实现类
////        strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl");
//        // 自定义mapper接口
////        strategy.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
//        strategy.setInclude(scanner("表名").split(","));
//        strategy.setSuperEntityColumns("id");
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.setStrategy(strategy);
//
//        // 执行
//        mpg.execute();
//    }
//
//}
