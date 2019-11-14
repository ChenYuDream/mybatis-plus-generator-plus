package com.yu.pojo.bo;

import lombok.Data;

/**
 * @author yu_chen
 * @date 2019/11/13 17:54
 */
@Data
public class DataBaseInfo {

    /**
     * 数据库连接驱动
     */
    private String driverClassName;
    /**
     * 数据库连接URL
     */
    private String jdbcUrl;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;
}
