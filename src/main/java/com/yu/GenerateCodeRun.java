package com.yu;

import com.yu.pojo.bo.Config;
import com.yu.pojo.vo.Entity;
import com.yu.parser.ConfigParser;
import com.yu.util.GenerateUtil;
import com.yu.util.InitDb;
import freemarker.template.TemplateException;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.List;

/**
 * 自动生成代码的类
 *
 * @author ChenYu
 */
public class GenerateCodeRun {

    public static void main(String[] args) throws DocumentException, IOException, TemplateException {

        String path = System.getProperty("user.dir") + "\\GenerateConfig.xml";
        final Config config = ConfigParser.getConfig(path);

        //这里可以自行实现如何有表名得到类名,表明全小写
        List<Entity> entitys = InitDb.getInstence(config).initTables();

        for (Entity entity : entitys) {
            GenerateUtil.AllGenerate(entity, "model");
            GenerateUtil.AllGenerate(entity, "dao");
            GenerateUtil.AllGenerate(entity, "mapper");
            GenerateUtil.AllGenerate(entity, "service");
            GenerateUtil.AllGenerate(entity, "serviceImpl");
            GenerateUtil.AllGenerate(entity, "controller");
        }
        System.out.println("------------生成完毕!-------------");
    }

}
