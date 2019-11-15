package ${basePackageMap['model'].packageName};

import lombok.Data;

/**
* ${tableComment!}
* @author ${author!}
* @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
**/
@Data
@TableName("${tableName}")
public class ${basePackageMap['model'].className} extends BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;


    <#list entityAttrs as ea>
    /**
     * ${ea.comment!}
     */
        <#if ea.isPrimaryKey == "1">
    @TableId
        </#if>
        <#if ea.isPrimaryKey != "1">
    @TableField("${ea.jdbcFieldName}")
        </#if>
    private ${ea.javaType} ${ea.fieldName};
    </#list>

}