/**
 * @(#)PageParam.java, 2014年4月22日.
 *
 * Copyright 2014 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netease.mail.yanxuan.generator.example.config.page;


/**
 * 分页参数
 *
 * @author 王国云(wangguoyun@corp.netease.com)
 */
public class PageParam{

    /**
	 *
	 */
    private static final long serialVersionUID = 780441301778777408L;

    public PageParam(){}

    public PageParam(int page, int size){
        this.page = page;
        this.size = size;
    }
    /**
     * 当前页，约定从1开始
     */
    private int page = 1;

    /**
     * 页大小，默认为10
     */
    private int size = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 获取分页时第一个条数据相对所有数据的偏移
     *
     * @return
     */
    public int getOffset() {
        return (page - 1) * size;
    }

    /**
     * 检查参数设置是否合法
     */
    public boolean checkValid() {
        return page > 0 && size > 0;
    }

}
