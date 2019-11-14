package ${dao.packageName};

import ${model.packageName}.${model.clazzName};

import java.util.List;
import java.util.Map;

/**
* @author yu_chen
* @create ${.now?string("yyyy-MM-dd HH:mm")}
**/
public interface ${dao.clazzName} {

    /**
    * 保存
    * 所有字段全都保存
    * @param ${model.clazzName?uncap_first}
    * @return 保存的数量
    */
    int save${model.clazzName}(${model.clazzName} ${model.clazzName?uncap_first});

    /**
    * 根据ID删除
    * @param id 主键ID
    * @return 删除记录数
    */
    int delete${model.clazzName}ById(String id);

    /**
    * 更新所有字段
    * @param ${model.clazzName?uncap_first}
    * @return 保存后的对象包括ID
    */
    int update${model.clazzName}(${model.clazzName} ${model.clazzName?uncap_first});

    /**
    * 按ID查询
    * @param id 主键ID
    * @return ${model.clazzName}
    */
    ${model.clazzName} select${model.clazzName}ById(String id);

    /**
    * 按条件查询全部的
    * @param map  查询条件
    * @return  List<${model.clazzName}>
    */
    List<${model.clazzName}> selectAllByMap(Map<String,Object> map);

}
