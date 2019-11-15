/**
 * 24 Aug 2016
 */
package com.yu.pojo.vo;


import com.google.gson.Gson;
import com.yu.pojo.bo.BasePackage;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenYu 一个数据库表所具有的属性
 */
@Data
public class EntityVO {

    /**
     * 编码的作者
     */
    private String author;
    /**
     * 实体类的类名
     */
    private String className;
    /**
     * 实体类对应的数据库表名
     */
    private String tableName;
    /**
     * 基类包
     */
    private String basePackage;
    /**
     * 模板的路径
     */
    private String ftlPath;
    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 基本集合类
     */
    private Map<String, BasePackage> basePackageMap;

    /**
     * 实体类的属性集合
     */
    private List<EntityAttrVO> entityAttrs;

    public EntityVO() {
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
