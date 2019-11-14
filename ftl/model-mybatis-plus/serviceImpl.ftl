package ${serviceImpl.packageName};



import org.springframework.stereotype.Service;
import ${model.packageName}.${model.clazzName};
import ${dao.packageName}.${dao.clazzName};
import ${service.packageName}.${service.clazzName};

/**
* @author yu_chen
* @date ${.now?string("yyyy-MM-dd HH:mm")}
**/
@Service
public class ${model.clazzName}ServiceImpl extends BaseServiceImpl<${dao.clazzName}, ${model.clazzName}> implements ${model.clazzName}Service {


}