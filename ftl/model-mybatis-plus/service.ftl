package ${basePackageMap['service'].packageName};

import ${basePackageMap['model'].packageName}.${basePackageMap['model'].className};

/**
* @author ${author!}
* @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
**/
public interface ${basePackageMap['service'].className} extends BaseService<${basePackageMap['model'].className}> {


}
