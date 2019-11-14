/**
 * 25 Aug 2016
 */
package com.yu.parser;

import com.yu.pojo.bo.Config;
import com.yu.pojo.bo.Table;
import com.yu.pojo.bo.BasePackage;
import com.yu.pojo.bo.DataBaseInfo;
import com.yu.util.ElementUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenYu
 */
public class ConfigParser {

    public static void main(String[] args) throws DocumentException {
        String path = System.getProperty("user.dir") + "\\GenerateConfig.xml";
        getConfig(path);
    }

    /**
     * 得到配置对象
     *
     * @param path 配置文件的路径
     * @return config对象
     * @throws DocumentException 文档异常
     */
    public static Config getConfig(String path) throws DocumentException {
        ConfigParser cfu = new ConfigParser();
        Document document = cfu.getDocument(path);
        return cfu.parseXml(document);
    }

    /**
     * 得到配置文件对象
     *
     * @return 返回文档对象
     * @throws DocumentException
     */
    private Document getDocument(String path) throws DocumentException {
        SAXReader reader = new SAXReader();
        System.out.println(path);
        return reader.read(new File(path));
    }

    /**
     * 解析配置文件
     *
     * @return
     */
    private Config parseXml(Document document) {
        Config config = new Config();
        Element root = document.getRootElement();
        //解析jdbc节点
        parseJdbc(root, config);
        //解析basePackage节点
        parseBasePackage(root, config);
        //解析table节点
        parseTable(root, config);
        //解析ftl节点
        parseFtl(root, config);
        return config;
    }


    /**
     * 解析数据库信息
     *
     * @param root 根元素
     */
    private void parseJdbc(Element root, Config config) {
        Element jdbc = root.element("jdbc");
        config.setJdbcType(ElementUtil.getAttributeFromElement(jdbc, "type"));
        config.setDataBaseName(ElementUtil.getAttributeFromElement(jdbc, "database"));

        List<Element> params = jdbc.elements("param");
        DataBaseInfo dataBaseInfo = new DataBaseInfo();
        for (Element param : params) {
            String name = ElementUtil.getAttributeFromElement(param, "name");
            //将数据库连接的信息设置到对象中去
            try {
                FieldUtils.writeDeclaredField(dataBaseInfo, name, param.getTextTrim(), true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        config.setDataBaseInfo(dataBaseInfo);
    }

    /**
     * 解析基类包的信息
     *
     * @param root 根元素
     */
    private void parseBasePackage(Element root, Config config) {
        //获取基类包
        Element basePackages = root.element("basePackage");
        String basePackage = basePackages.node(0).getText().trim();
        config.setBasePackage(basePackage);
        //生成路径的地址
        String basePath = "\\\\generate\\\\" + basePackage.replace(".", "\\\\") + "\\\\";
        config.setBasePath(basePath);
    }

    /**
     * 解析表的信息
     *
     * @param root 根元素
     */
    private void parseTable(Element root, Config config) {
        //设置要生成的表格对象
        Table tb = new Table();
        Element table = root.element("table");
        List<Element> els = table.elements();
        Map<String, String> map = new HashMap<>(32);
        StringBuilder tableNames = new StringBuilder();
        if (els != null && els.size() > 0) {
            for (Element element : els) {
                String tableName = ElementUtil.getAttributeFromElement(element, "tableName");
                String modelName = ElementUtil.getAttributeFromElement(element, "modelName");
                tableNames.append("'").append(tableName).append("',");
                //设置映射
                map.put(tableName.toLowerCase(), modelName);
            }
        }
        tb.setTableNameToEntityNameMapping(map);
        tb.setTableNamesJoin(StringUtils.isEmpty(tableNames.toString()) ? "" : StringUtils.substringBeforeLast(tableNames.toString(), ","));
        config.setTable(tb);
    }


    /**
     * 解析生成模板的配置信息
     *
     * @param root 根元素
     */
    private void parseFtl(Element root, Config config) {
        Element ftl = root.element("ftl");
        config.setFtlPath(ElementUtil.getAttributeFromElement(ftl, "path"));
        List<Element> ftlParams = ftl.elements("param");
        for (Element param : ftlParams) {
            //初始化各个包的名字文件名包名等信息
            String modelName = ElementUtil.getAttributeFromElement(param, "name");
            BasePackage model = config.getBasePackageMap().get(modelName);
            String basePackageName = ElementUtil.getAttributeFromElement(param, "basePackageName");
            String fileName = param.getText().trim();
            //设置各个类型对应的模板的名称
            model.setFtlName(modelName + ".ftl");
            model.setBasePackageName(basePackageName);
            model.setFileName(fileName);
            model.setFilePath(config.getBasePath() + basePackageName.replace(".", "\\\\"));
            model.setPackageName(config.getBasePackage() + "." + basePackageName);
        }
    }

}
