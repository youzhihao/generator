package org.mybatis.generator.config;


/**
 * Created by youzhihao on 2017/4/10.
 */
public class BalanceColumn {

    /** The column name. */
    protected String columnName;

    public BalanceColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
