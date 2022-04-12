//package org.jeecg;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class selfCodeGenerator {
//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
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
//
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/jeecg-boot-module-system/src/main/java");
//        gc.setAuthor("LZQ");    //1、修改作者名
//        gc.setEntityName("%sEntity");   //2、自定义生成的文件名，%s 表示生成的文件前缀
//        gc.setOpen(false);
//        gc.setSwagger2(true);//3、实体属性 Swagger2 注解
//        gc.setIdType(IdType.ASSIGN_ID);//主键策略
//        mpg.setGlobalConfig(gc);
//
//        // 4、修改数据源配置，数据库驱动名、用户名、密码
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/jeecg-boot?useUnicode=true&useSSL=false&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
//        dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("root");
//        mpg.setDataSource(dsc);
//        //多数据源
//////        DataSourceConfig dsc = new DataSourceshipConfig();
////        dsc.setUrl("jdbc:mysql://172.20.3.29:3306/db_hgyq_hsjc?useUnicode=true&useSSL=false&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
////        dsc.setSchemaName("public");
////        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
////        dsc.setUsername("hgjtuser");
////        dsc.setPassword("mwyohFrcNiWaa2GS");
////        mpg.setDataSource(dsc);
//
//        //5、 修改包名，生成文件目录，模块名从控制台读入
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
//        pc.setParent("org.jeecg.modules.srzb");//包名
//        pc.setController("controller");
//        pc.setMapper("mapper");
//        pc.setEntity("dao.entity");
//        pc.setService("service");
//        pc.setServiceImpl("service.impl");
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
//        // 默认模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//        // 如果模板引擎是 velocity
//        // String templatePath = "/templates/mapper.xml.vm";
//
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 6、自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！，这里做字符串处理更改了生成的文件名,生成空的mapper.xml文件
//                return projectPath + "/jeecg-boot-module-system/src/main/java/org/jeecg/modules/srzb/"+pc.getModuleName()+"/mapper/xml/" + "/" + tableInfo.getEntityName().replace("Entity","") + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        /*
//        cfg.setFileCreate(new IFileCreate() {
//            @Override
//            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//                // 判断自定义文件夹是否需要创建
//                checkDir("调用默认方法创建的目录，自定义目录用");
//                if (fileType == FileType.MAPPER) {
//                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
//                    return !new File(filePath).exists();
//                }
//                // 允许生成模板文件
//                return true;
//            }
//        });
//        */
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
////         配置自定义输出模板
////        指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
////         templateConfig.setEntity("templates/entity2.java");
////         templateConfig.setService();
////         templateConfig.setController();
//
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setEntityLombokModel(true);
//        strategy.setRestControllerStyle(true);
//        // 公共父类
////        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        // 写于父类中的公共字段
//        //strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//        strategy.setControllerMappingHyphenStyle(true);
////        strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();
//    }
//}
