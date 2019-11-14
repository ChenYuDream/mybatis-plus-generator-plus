package ${model.packageName};

 <#list attrs as a>
   <#if a.type=="Date">
import java.time.LocalDateTime;
   	<#break>
   </#if>
</#list>
 <#list attrs as a>
   <#if a.type=="BigDecimal">
import java.math.BigDecimal;
   	<#break>
   </#if>
</#list>
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
* ${clazzComments!}
* @author yu_chen
* @date ${.now?string("yyyy-MM-dd HH:mm")}
**/
@Data
@TableName("${tableName}")
public class ${model.clazzName} implements java.io.Serializable{

	private static final long serialVersionUID = 1L;


    <#list attrs as a>
    /**
     * ${a.comment!}
     */
        <#if a.isPrimaryKey == "1">
    @TableId
        </#if>
        <#if a.isPrimaryKey != "1">
    @TableField("${a.jdbcField}")
        </#if>
    private ${a.type} ${a.field};
    </#list>

}