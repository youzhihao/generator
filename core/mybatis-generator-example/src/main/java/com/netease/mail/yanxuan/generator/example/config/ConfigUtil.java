package com.netease.mail.yanxuan.generator.example.config;

import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by youzhihao on 2017/3/9.
 */
public class ConfigUtil {


    private static volatile Properties PROPERTIES;

    public static Properties getProperties() {
        if (PROPERTIES == null) {
            synchronized (ConfigUtil.class) {
                if (PROPERTIES == null) {
                    //加载默认配置
                    PROPERTIES = ConfigUtil.loadProperties(ConfigFileConstants.GENERATOR_PROPERTIES);
                }
            }
        }
        return PROPERTIES;
    }

    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        //绝对路径
        if (fileName.startsWith("/")) {
            try {
                FileInputStream input = new FileInputStream(fileName);
                try {
                    properties.load(input);
                } finally {
                    input.close();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return properties;
        } else { //相对路径
            try {
                List<URL> list = new ArrayList<>();
                Enumeration<URL> urls = ConfigUtil.class.getClassLoader().getResources(fileName);
                while (urls.hasMoreElements()) {
                    list.add(urls.nextElement());
                }
                if (list.size() > 1) {
                    return properties;
                } else if (list.size() < 1) {
                    return properties;
                } else {
                    InputStream input = list.get(0).openStream();
                    if (input != null) {
                        try {
                            properties.load(input);
                        } finally {
                            try {
                                input.close();
                            } catch (Throwable t) {
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static String getString(String key) {
        return getProperties().getProperty(key);
    }

    public static int getInt(String key) {
        String value = getProperties().getProperty(key);
        return StringUtils.isNotBlank(value) ? Integer.valueOf(value) : 0;
    }

    public static boolean getBoolean(String key) {
        String value = getProperties().getProperty(key);
        return Boolean.valueOf(value);
    }


}
