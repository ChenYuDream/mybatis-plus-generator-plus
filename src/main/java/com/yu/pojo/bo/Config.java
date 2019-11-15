/**
 * 25 Aug 2016
 */
package com.yu.pojo.bo;

import com.google.gson.Gson;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenYu
 */
@Data
public class Config {

    /**
     * 编码的作者名 用于解析配置文件中的信息
     */
    private String author;
    /**
     * 生成文件的路径
     */
    private String basePath;
    /**
     * 基类包
     */
    private String basePackage;
    /**
     * 判断是什么数据库
     */
    private String jdbcType;

    /**
     * 数据库连接信息
     */
    private DataBaseInfo dataBaseInfo;
    /**
     * 模板的路径
     */
    private String ftlPath;
    /**
     * 数据库名
     */
    private String dataBaseName;
    /**
     * 要生成的表
     */
    private Table table;


    /**
     * 各种模块对应的属性集合
     */
    private Map<String, BasePackage> basePackageMap;

    public Config() {
        basePackageMap = new HashMap<String, BasePackage>() {
            {
                put("model", new BasePackage());
                put("dao", new BasePackage());
                put("service", new BasePackage());
                put("serviceImpl", new BasePackage());
                put("controller", new BasePackage());
                put("mapper", new BasePackage());
            }
        };

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
