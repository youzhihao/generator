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
 * mybatis3的dao层生成器
 * dao层代理部分非example的mapper方法
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
        answer.addImportedType("org.springframework.beans.factory.annotation.Autowired");


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
        String poName = introspectedTable.getTableConfiguration().getDomainObjectName();
        String name = poName.substring(0, 1).toLowerCase() + poName.substring(1) + "Mapper";
        field.setName(name);
        field.setType(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Autowired");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(field.getType());
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


    protected Interface getInterfaceShell() {
        Interface answer = new Interface(new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType()));
        answer.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(answer);
        return answer;
    }

    protected void addSelectByPrimaryKeyMethod(TopLevelClass topLevelClass,
                                               Interface interfaze) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.SelectByPrimaryKeyMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }


    protected void addDeleteByPrimaryKeyMethod(TopLevelClass topLevelClass,
                                               Interface interfaze) {
        if (introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.DeleteByPrimaryKeyMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }

    protected void addInsertMethod(TopLevelClass topLevelClass,
                                   Interface interfaze) {
        if (introspectedTable.getRules().generateInsert()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.InsertMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }

    protected void addInsertSelectiveMethod(TopLevelClass topLevelClass,
                                            Interface interfaze) {
        if (introspectedTable.getRules().generateInsertSelective()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.InsertSelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }

    protected void addUpdateByPrimaryKeySelectiveMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.UpdateByPrimaryKeySelectiveMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }

    protected void addUpdateByPrimaryKeyWithBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.UpdateByPrimaryKeyWithBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
    }

    protected void addUpdateByPrimaryKeyWithoutBLOBsMethod(
            TopLevelClass topLevelClass, Interface interfaze) {
        if (introspectedTable.getRules()
                .generateUpdateByPrimaryKeyWithoutBLOBs()) {
            AbstractDAOElementGenerator methodGenerator = new org.mybatis.generator.codegen.ibatis2.dao.elements.UpdateByPrimaryKeyWithoutBLOBsMethodGenerator();
            initializeAndExecuteGenerator(methodGenerator, topLevelClass,
                    interfaze);
        }
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
