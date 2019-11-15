package com.yu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类属性对象
 *
 * @author ChenYu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityAttrVO {

    /**
     * 生成model的字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String javaType;
    /**
     * 字段注释
     */
    private String comment;
    /**
     * 数据库中的字段
     */
    private String jdbcFieldName;
    /**
     * jdbc类型
     */
    private String jdbcType;
    /**
     * 是否是主键 1是主键  0是非主键
     */
    private String isPrimaryKey;


}
