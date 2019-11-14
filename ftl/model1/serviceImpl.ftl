package ${serviceImpl.packageName};


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ${model.packageName}.${model.clazzName};
import ${dao.packageName}.${dao.clazzName};
import ${service.packageName}.${service.clazzName};
import ${basePackage}.util.StringUtil;

@Service
public class ${model.clazzName}ServiceImpl implements ${model.clazzName}Service {

    @Resource
    private ${dao.clazzName} ${dao.clazzName?uncap_first};

    /**
    * 保存
    * 所有字段全都保存
    * @param ${model.clazzName?uncap_first}
    * @return 保存后的对象包括ID
    */
    @Override
    public int save${model.clazzName}(${model.clazzName} ${model.clazzName?uncap_first}){
        if(${model.clazzName?uncap_first}.getId()==null||${model.clazzName?uncap_first}.getId()==""){
            String uuid =UUID.randomUUID().toString().replace("-", "").trim();
            ${model.clazzName?uncap_first}.setId(uuid);
        }
        return ${dao.clazzName?uncap_first}.save${model.clazzName}(${model.clazzName?uncap_first});
    }

    /**
    * 根据ID删除
    * @param id 主键ID
    * @return 删除记录数
    */
    @Override
    public int delete${model.clazzName}ById(String id){

        return ${dao.clazzName?uncap_first}.delete${model.clazzName}ById(id);
    }

    /**
    * 更新
    * 更新所有字段
    * @param ${model.clazzName?uncap_first}
    * @return 保存后的对象包括ID
    */
    @Override
    public int update${model.clazzName}(${model.clazzName} ${model.clazzName?uncap_first}){

        return ${dao.clazzName?uncap_first}.update${model.clazzName}(${model.clazzName?uncap_first});
    }

    /**
    * 按ID查询
    * @parm id 主键ID
    * @return ${model.clazzName}
    */
    @Override
    public ${model.clazzName} select${model.clazzName}ById(String id){

        return ${dao.clazzName?uncap_first}.select${model.clazzName}ById(id);
    }

    /**
    * 按条件查询全部的
    * @param map  查询条件
    * @return  List<${model.clazzName}>
    */
    @Override
    public List<${model.clazzName}> selectAllByMap(Map<String,Object> map){
        return ${dao.clazzName?uncap_first}.selectAllByMap(map);
    }


    /**
    * 按条件查询全部的
    * @param ${model.clazzName?uncap_first}  查询条件
    * @return  List<${model.clazzName}>
    */
    @Override
    public List<${model.clazzName}> selectAllBy${model.clazzName}(${model.clazzName} ${model.clazzName?uncap_first}){

        return ${dao.clazzName?uncap_first}.selectAllBy${model.clazzName}(${model.clazzName?uncap_first});
    }

}