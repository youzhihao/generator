package com.netease.mail.yanxuan.generator.example.config;

import org.mybatis.generator.api.IntrospectedColumn;

/**
 * @author hzyouzhihao on 2016/6/5.
 * 解决自动生成meta类字段名非驼峰形式
 */
public class CustomIntrospectedColumn extends IntrospectedColumn {

    @Override
    public void setJavaProperty(String javaProperty) {
        String fieldName = this.getActualColumnName().substring(0, 1).toLowerCase() + this.getActualColumnName().substring(1);
        super.setJavaProperty(fieldName);
    }
}
