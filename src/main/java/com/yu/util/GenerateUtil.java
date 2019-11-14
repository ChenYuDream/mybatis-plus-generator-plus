/**
 * 24 Aug 2016
 */
package com.yu.util;

import com.yu.pojo.vo.Entity;
import com.yu.pojo.bo.BasePackage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author ChenYu
 * 自动生成的工具类
 */
public class GenerateUtil {


    /**
     * 根据Entity自动生成model
     *
     * @param entity 数据库对象
     * @param type   生成什么文件  1 model 2 dao 3 daoImpl 4 service 5 serviceImpl 6 controller
     * @throws IOException
     * @throws TemplateException
     */
    public static void AllGenerate(Entity entity, String type) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        //模板的的路径
        String ftlPath = entity.getFtlPath();
        //类名
        String className = entity.getClassName();
        //模板在类路径下的地址
        String path = System.getProperty("user.dir") + ftlPath;
        //获取要生成的basePackage
        BasePackage basePackage = makeBasePackage(entity.getBasePackageMap().get(type), className);
        //新生成的文件的路径
        String newPath = System.getProperty("user.dir") + basePackage.getFilePath();
        //判断生成路径是否存在  不存在就创建
        PathUtil.checkDirAndCreate(newPath);
        //创建配置对象
        cfg.setDirectoryForTemplateLoading(new File(path));
        //得到模板对象
        Template template = cfg.getTemplate(basePackage.getFtlName(), "utf-8");
        PathUtil.printFileByObject(entity, template, newPath, basePackage.getFileName());
    }

    /**
     * 根据类名初始化basePackage
     *
     * @param basePackage
     * @param className
     * @return
     */
    public static BasePackage makeBasePackage(BasePackage basePackage, String className) {
        String fileName = basePackage.getFileName().replace("*", className);
        String substring = StringUtils.substringBeforeLast(fileName, ".");
        basePackage.setFileName(fileName);
        basePackage.setClazzName(substring);
        return basePackage;
    }

    public static void main(String[] args) {
        String clazzName = "StudentRjkl.java";
        String substring = clazzName.substring(0, clazzName.indexOf("."));
        System.out.println(substring);
    }
}
