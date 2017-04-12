package com.netease.mail.yanxuan.generator.example.config;

import com.netease.mail.yanxuan.generator.example.run.GeneratorRun;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;

import java.net.URL;
import java.util.List;

/**
 * @author hzyouzhihao on 2016/6/5.
 * 深度定制mybatis-generator,集成自动分页功能
 */
public class CustomPluginAdapter extends PluginAdapter {

    final private FullyQualifiedJavaType pageParamType = new FullyQualifiedJavaType(ConfigUtil.getString("page.param.class"));

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    /**
     * 自定义新的sql
     * @author youzhihao
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement root = document.getRootElement();
        selectByPrimaryKeyLockedSql(root);
        selectOneByExampleSql(root);
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //集成分页
        if (ConfigUtil.getBoolean("page.param.switch")) {
            Field pageParam = new Field();
            pageParam.setName("pageParam");
            pageParam.setType(pageParamType);
            pageParam.setVisibility(JavaVisibility.PRIVATE);
            topLevelClass.addField(pageParam);
            Method setPageParam = new Method();
            setPageParam.setName("setPageParam");
            setPageParam.addParameter(new Parameter(pageParamType, "pageParam"));
            setPageParam.addBodyLine("this.pageParam=pageParam;");
            setPageParam.setVisibility(JavaVisibility.PUBLIC);
            topLevelClass.addMethod(setPageParam);
            Method getBasePageAO = new Method();
            getBasePageAO.setName("getPageParam");
            getBasePageAO.setReturnType(pageParamType);
            getBasePageAO.addBodyLine("return pageParam;");
            getBasePageAO.setVisibility(JavaVisibility.PUBLIC);
            topLevelClass.addMethod(getBasePageAO);
            topLevelClass.addImportedType(pageParamType);
            List<Method> methods = topLevelClass.getMethods();
            for (Method method : methods) {
                if ("clear".equals(method.getName())) {
                    method.addBodyLine("pageParam = null;");
                }
            }
        }

        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (ConfigUtil.getBoolean("page.param.switch")) {
            XmlElement ifElment = new XmlElement("if");
            ifElment.addAttribute(new Attribute("test", "pageParam != null"));
            ifElment.addElement(new TextElement("limit #{pageParam.offset},#{pageParam.size} "));
            element.addElement(ifElment);
        }
        return true;
    }


    /**
     * 增加一个找到selectByPrimaryKeyLocked方法，和事务配合使用，形成行锁
     * @author youzhihao
     */
    private void selectByPrimaryKeyLockedSql(XmlElement root) {
        //找到selectByPrimaryKey元素
        XmlElement selectByPrimaryKey = null;
        for (Element element : root.getElements()) {
            XmlElement xmlElement = (XmlElement) element;
            for (Attribute attribute : ((XmlElement) element).getAttributes()) {
                if ("id".equals(attribute.getName()) && "selectByPrimaryKey".equals(attribute.getValue())) {
                    selectByPrimaryKey = xmlElement;
                }
            }
        }
        XmlElement temp = new XmlElement("select");
        List<Attribute> attributes = selectByPrimaryKey.getAttributes();
        for (Attribute attribute : attributes) {
            if ("id".equals(attribute.getName())) {
                temp.addAttribute(new Attribute("id", "selectByPrimaryKeyLocked"));
            } else {
                temp.addAttribute(attribute);
            }
        }
        for (Element element : selectByPrimaryKey.getElements()) {
            temp.addElement(element);
        }
        temp.addElement(new TextElement("for update "));
        root.addElement(temp);
    }

    /**
     * 增加一个selectOneByExample
     * @author youzhihao
     */
    private void selectOneByExampleSql(XmlElement root) {
        /**增加一个selectOneByExample*/
        XmlElement selectByExample = null;
        for (Element element : root.getElements()) {
            XmlElement xmlElement = (XmlElement) element;
            for (Attribute attribute : ((XmlElement) element).getAttributes()) {
                if ("id".equals(attribute.getName()) && "selectByExample".equals(attribute.getValue())) {
                    selectByExample = xmlElement;
                }
            }
        }
        XmlElement temp = new XmlElement("select");
        List<Attribute> attributes = selectByExample.getAttributes();
        for (Attribute attribute : attributes) {
            if ("id".equals(attribute.getName())) {
                temp.addAttribute(new Attribute("id", "selectOneByExample"));
            } else {
                temp.addAttribute(attribute);
            }
        }
        for (Element element : selectByExample.getElements()) {
            if (element instanceof XmlElement) {
                XmlElement xmlElement = (XmlElement) element;
                boolean isPageElement = false;
                //过滤掉自定义的pageParam元素
                if ("if".equals(xmlElement.getName())) {
                    for (Attribute attribute : xmlElement.getAttributes()) {
                        if ("test".equals(attribute.getName()) && "pageParam != null".equals(attribute.getValue())) {
                            isPageElement = true;
                        }
                    }
                }
                if (!isPageElement) {
                    temp.addElement(element);
                }
            } else {
                temp.addElement(element);
            }

        }
        temp.addElement(new TextElement("limit 1 "));
        root.addElement(temp);
    }

    /**
     * 增加selectByPrimaryKeyLocked的对应mapper类方法，和事务配合使用，形成行锁
     * @author youzhihao
     */

    private void selectByPrimaryKeyLockedMethod(Interface interfaze) {
        /***/
        List<Method> methods = interfaze.getMethods();
        Method selectByPrimaryKey = null;
        //找到selectByPrimaryKey方法
        for (Method method : methods) {
            if ("selectByPrimaryKey".equals(method.getName())) {
                selectByPrimaryKey = method;
            }
        }
        Method temp = new Method();
        temp.setName("selectByPrimaryKeyLocked");
        for (Parameter parameter : selectByPrimaryKey.getParameters()) {
            temp.addParameter(parameter);
        }
        temp.setReturnType(selectByPrimaryKey.getReturnType());
        interfaze.addMethod(temp);
    }

    /**
     * 增加一个selectOneByExample
     * @author youzhihao
     */
    private void selectOneByExampleMethod(Interface interfaze) {
        List<Method> methods = interfaze.getMethods();
        Method selectByExample = null;
        //找到selectByExample方法
        for (Method method : methods) {
            if ("selectByExample".equals(method.getName())) {
                selectByExample = method;
            }
        }
        Method temp = new Method();
        temp.setName("selectOneByExample");
        for (Parameter parameter : selectByExample.getParameters()) {
            temp.addParameter(parameter);
        }
        temp.setReturnType(selectByExample.getReturnType());
        //将selectByExample方法的list中的泛型类作为返回参数
        temp.setReturnType(selectByExample.getReturnType().getTypeArguments().get(0));
        interfaze.addMethod(temp);
    }
}
