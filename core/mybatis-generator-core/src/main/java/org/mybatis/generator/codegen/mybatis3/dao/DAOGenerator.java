package org.mybatis.generator.codegen.mybatis3.dao;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.ibatis2.dao.elements.AbstractDAOElementGenerator;
import org.mybatis.generator.codegen.ibatis2.dao.templates.AbstractDAOTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youzhihao on 2017/4/11.
 * mybatis3的dao层生成器:代理非example的mapper方法
 * 需要配合是用的jar包:
 * 1.spring-context
 * 2.spring-beans
 * 3.mybatis-spring
 */
public class DAOGenerator extends AbstractJavaClientGenerator {

    private AbstractDAOTemplate daoTemplate;

    public DAOGenerator(AbstractDAOTemplate daoTemplate) {
        super(true);
        this.daoTemplate = daoTemplate;
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        TopLevelClass daoClass = createTopLevelClass();
        addNameSpaceStr(daoClass);
        addSqlSessionTemplateField(daoClass);
        addMapperField(daoClass);
        addInsertMethod(daoClass);
        addInsertSelectiveMethod(daoClass);
        addDeleteByPrimaryKeyMethod(daoClass);
        addUpdateByPrimaryKeyWithoutBLOBsMethod(daoClass);
        addUpdateByPrimaryKeySelectiveWithoutBLOBsMethod(daoClass);
        addSelectByPrimaryKeyMethod(daoClass);
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        //如果不生成client或者dao类文件在loader中存在，则不生成dao文件
        if (context.getPlugins().clientGenerated(null, daoClass, introspectedTable) && !isExist(daoClass)) {
            answer.add(daoClass);
        }
        return answer;
    }

    protected TopLevelClass createTopLevelClass() {
        FullyQualifiedJavaType classType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaDaoType());
        TopLevelClass answer = new TopLevelClass(classType);
        answer.setVisibility(JavaVisibility.PUBLIC);
        answer.addImportedType(introspectedTable.getMyBatis3JavaMapperType());
        answer.addImportedType(introspectedTable.getBaseRecordType());
        answer.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        answer.addImportedType("org.springframework.stereotype.Repository");
        answer.addAnnotation("@Repository");
        return answer;
    }

    private void addNameSpaceStr(TopLevelClass topLevelClass) {
        Field field = new Field();
        field.setName("nameSpace");
        field.setType(new FullyQualifiedJavaType("String"));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setStatic(true);
        field.setFinal(true);
        field.setInitializationString(introspectedTable.getTableConfiguration().getDomainObjectName() + "Mapper.class.getName()");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(field.getType());
    }

    private void addSqlSessionTemplateField(TopLevelClass topLevelClass) {
        Field field = new Field();
        field.setName("sqlSessionTemplate");
        field.setType(new FullyQualifiedJavaType("org.mybatis.spring.SqlSessionTemplate"));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(field.getType());
    }

    private void addMapperField(TopLevelClass topLevelClass) {
        Field field = new Field();
        field.setName(getMapperName());
        field.setType(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(field.getType());
    }


    private void addInsertMethod(TopLevelClass topLevelClass) {
        if (introspectedTable.getRules().generateInsert()) {
            Method method = new Method();
            method.setName("insert");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), getPoName()));
            method.setReturnType(new FullyQualifiedJavaType("int"));
            StringBuilder sb = new StringBuilder();
            sb.append("return " + getMapperName() + ".insert(" + getPoName() + ");");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
    }

    private void addInsertSelectiveMethod(TopLevelClass topLevelClass) {
        if (introspectedTable.getRules().generateInsertSelective()) {
            Method method = new Method();
            method.setName("insertSelective");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), getPoName()));
            method.setReturnType(new FullyQualifiedJavaType("int"));
            StringBuilder sb = new StringBuilder();
            sb.append("return " + getMapperName() + ".insertSelective(" + getPoName() + ");");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
    }


    private void addDeleteByPrimaryKeyMethod(TopLevelClass topLevelClass) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            Method method = new Method();
            method.setName("deleteByPrimaryKey");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(new FullyQualifiedJavaType("Long"), "id"));
            method.setReturnType(new FullyQualifiedJavaType("int"));
            StringBuilder sb = new StringBuilder();
            sb.append("return " + getMapperName() + ".deleteByPrimaryKey(id);");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
    }


    protected void addUpdateByPrimaryKeyWithoutBLOBsMethod(TopLevelClass topLevelClass) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
            Method method = new Method();
            method.setName("updateByPrimaryKey");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), getPoName()));
            method.setReturnType(new FullyQualifiedJavaType("int"));
            StringBuilder sb = new StringBuilder();
            sb.append("return " + getMapperName() + ".updateByPrimaryKey(" + getPoName() + ");");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
    }


    private void addUpdateByPrimaryKeySelectiveWithoutBLOBsMethod(TopLevelClass topLevelClass) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
            Method method = new Method();
            method.setName("updateByPrimaryKeySelective");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), getPoName()));
            method.setReturnType(new FullyQualifiedJavaType("int"));
            StringBuilder sb = new StringBuilder();
            sb.append("return " + getMapperName() + ".updateByPrimaryKeySelective(" + getPoName() + ");");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
    }

    private void addSelectByPrimaryKeyMethod(TopLevelClass topLevelClass) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            Method method = new Method();
            method.setName("selectByPrimaryKey");
            method.setVisibility(JavaVisibility.PUBLIC);
            method.addParameter(new Parameter(new FullyQualifiedJavaType("Long"), "id"));
            method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
            StringBuilder sb = new StringBuilder();
            sb.append("return " + getMapperName() + ".selectByPrimaryKey(id);");
            method.addBodyLine(sb.toString());
            topLevelClass.addMethod(method);
        }
    }


    private boolean isExist(TopLevelClass topLevelClass) {
        //client接口类不需要每次生成，如果有就不生成
        String classPath = topLevelClass.getType().getFullyQualifiedName().replace(".", "/") + ".class";
        URL url = DAOGenerator.class.getClassLoader().getResource(classPath);
        if (url == null) {
            return false;
        }
        return true;
    }

    private String getMapperName() {
        String poName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String name = poName.substring(0, 1).toLowerCase() + poName.substring(1) + "Mapper";
        return name;
    }

    private String getPoName() {
        String poName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String name = poName.substring(0, 1).toLowerCase() + poName.substring(1);
        return name;
    }


    protected void initializeAndExecuteGenerator(
            AbstractDAOElementGenerator methodGenerator,
            TopLevelClass topLevelClass, Interface interfaze) {
        methodGenerator.setDAOTemplate(daoTemplate);
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addImplementationElements(topLevelClass);
        methodGenerator.addInterfaceElements(interfaze);
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        // this method is not called for iBATIS2
        return null;
    }
}
