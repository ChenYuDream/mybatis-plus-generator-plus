package ${model.packageName};

 <#list attrs as a>
   <#if a.type=="Date">
import java.util.Date;
   	<#break>
   </#if>
</#list>
 <#list attrs as a>
   <#if a.type=="BigDecimal">
import java.math.BigDecimal;
   	<#break>
   </#if>
</#list>
import com.google.gson.Gson;

/**
* ${clazzComments!}
* @author yu_chen
* @create ${.now?string("yyyy-MM-dd HH:mm")}
**/
public class ${model.clazzName} implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

    <#list attrs as a>
     /**
     * ${a.comment!}
     */
    private ${a.type} ${a.field};
    </#list>

    <#list attrs as a>
    public ${a.type} get${a.field?cap_first}(){
        return this.${a.field};
    }

    public void set${a.field?cap_first}(${a.type} ${a.field}){
        this.${a.field} = ${a.field};
    }
    </#list>

    @Override
	public String toString() {

		return new Gson().toJson(this);
	}

}