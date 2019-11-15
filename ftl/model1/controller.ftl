package ${basePackageMap['controller'].packageName};

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @author ${author!}
* @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
**/
@Controller
@RequestMapping("${basePackageMap['model'].className?uncap_first}")
public class ${basePackageMap['controller'].className} {


}