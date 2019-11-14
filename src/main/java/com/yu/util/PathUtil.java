package com.yu.util;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

public class PathUtil {

	/**
	 * 判断路径是否存在并创建此路径
	 * @param path 路径
	 */
	public static void Path_Judge_Exist(String path) {
		File file = new File(path);
		if (!file.exists()) {
            file.mkdirs();
        }
	}
	/**
	 * 输出到文件
	 * @param entity 映射到模板文件中的对象
	 * @param template 模板对象
	 * @param filePath 要输出的文件路径
	 * @param fileName 要输出的文件名称包含后缀名
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void printFileByObject(Object entity, Template template, String filePath, String fileName)
			throws IOException, TemplateException {
		File file = new File(filePath, fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
		template.process(entity, out);
		out.close();
		System.out.println("------------生成"+fileName+"文件");
	}

	/**
	 * 输出到控制台
	 * @param entity 映射到模板文件中的对象
	 * @param template  模板对象
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void printConsoleByObject(Object entity, Template template) throws TemplateException, IOException {
		StringWriter out = new StringWriter();
		template.process(entity, out);
		System.out.println(out.toString());
	}
}
