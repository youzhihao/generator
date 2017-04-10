package com.netease.mail.yanxuan.generator.example.run;

import com.netease.mail.yanxuan.generator.example.config.ConfigFileConstants;
import com.netease.mail.yanxuan.generator.example.config.ConfigUtil;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author hzyouzhihao on 2016/6/4.
 * 自动生成mybatis相应的
 * 不要随意使用这个生成类
 */
public class GeneratorRun {

    /**配置文件路径*/
    public final static String CONFIG_PATH = GeneratorRun.class.getClassLoader().getResource(ConfigFileConstants.GENERATOR_CONFIG).getPath();

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        try {
            boolean overwrite = true;
            File configFile = new File(CONFIG_PATH);
            Properties properties = ConfigUtil.getProperties();
            ConfigurationParser cp = new ConfigurationParser(properties, warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
            warnings.add(e.toString());
        } finally {
            System.out.println(warnings);
        }
    }
}

