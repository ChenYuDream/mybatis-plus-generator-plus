/**
 * 12 Sep 2016
 */
package com.yu.pojo.bo;

import lombok.Data;

import java.util.Map;

/**
 * @author ChenYu
 */
@Data
public class Table {

    /**
     * 由map中得到的tableNames
     */
    private String tableNamesJoin;
    /**
     * 表名与实体类名的映射
     */
    private Map<String, String> tableNameToEntityNameMapping;


}
