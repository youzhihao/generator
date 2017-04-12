1.table标签，增加一个均衡字段配置，如果是均衡字段，则默认update相关方法，忽略均衡字段的更新
    <table>
      <balanceColumn column="id"/>
    </table>
2.增加生成对应的selectOneByExample和selectOneByExampleWithBolbs方法
3.自动生成dao层，代理mapper中的非example参数方法,并且可以写自定义sql
4.  a.dao层自动生成，且不会被覆盖
    b.sqlmap.xml自动生成，且只会覆盖自动生成的方法
    c.不需要区分写两套sqlmap.xml


使用注意事项:
1.整体结构分为dao,mapper,sqlmap.xml
    a.dao层代理mapper类，可以使用mapper直接写sql，也可以引用sqlmap.xml的自定义sql
    b.mapper不允许在dao层意外的代码中进行使用
    c.mapper中的代码自动生成，不允许进行任何修改
    d.可以在dao和sqlmap.xml中进行代码的增加
