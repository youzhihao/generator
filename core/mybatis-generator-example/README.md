深度定制mybatis
1.增加自动生成dao层，代理mapper的方法，不允许在除dao层外的代码出现mapper方法的引用。
2.不重复生成dao和sql-xml,自定义和自动生成的dao和sql-xml合并，不会出现覆盖
3.增加均衡字段的忽略设置
4.增加一个key生成的钩子
