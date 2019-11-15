/**
 * 25 Aug 2016
 */
package com.yu.util;

import com.yu.constant.DataBaseConstant;
import com.yu.enums.DataTypeEnum;
import com.yu.pojo.vo.EntityAttrVO;
import com.yu.pojo.bo.Config;
import com.yu.pojo.bo.DbColumn;
import com.yu.pojo.vo.EntityVO;
import com.yu.parser.ConfigParser;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ChenYu 初始化数据库类
 */
public class InitDb {

    /**
     * 配置文件对象
     */
    private Config config;

    /**
     * 基类包
     */
    private String basePackage;

    /**
     * 测试类
     *
     * @param args
     * @throws DocumentException
     */
    public static void main(String[] args) throws DocumentException {
        String path = System.getProperty("user.dir") + "\\GenerateConfig.xml";
        List<EntityVO> entitys = InitDb.getInstence(ConfigParser.getConfig(path)).initTables();

        System.out.println(entitys);
    }

    /**
     * 构造方法
     *
     * @param config
     */
    private InitDb(Config config) {
        super();
        System.out.println("初始化方法");
        this.config = config;
        basePackage = config.getBasePackage();
    }

    /**
     * 得到Init数据库实例
     *
     * @return
     */
    public static InitDb getInstence(Config config) {
        return new InitDb(config);
    }

    /**
     * 从数据库查询出所需要生成的表并存放在集合中
     *
     * @return
     */
    public List<EntityVO> initTables() {
        StringBuilder sql = new StringBuilder();
        String tableNamesJoin = config.getTable().getTableNamesJoin();
        if (DataBaseConstant.DATABASE_ORACLE.equals(config.getJdbcType())) {
            sql.append("select tb.table_name, tc.comments from user_tables tb ,user_tab_comments tc where tb.TABLE_NAME=tc.table_name");
            if (StringUtils.isNotEmpty(tableNamesJoin)) {
                sql.append(" and tb.TABLE_NAME in (").append(tableNamesJoin.toUpperCase()).append(")");
            }
        } else if (DataBaseConstant.DATABASE_MYSQL.equals(config.getJdbcType())) {
            sql.append("SELECT table_name,table_comment FROM TABLES WHERE 1=1");
            sql.append(" and table_schema = '").append(config.getDataBaseName()).append("'");
            sql.append(" and table_name IN(").append(tableNamesJoin.toLowerCase()).append(")");
        } else {
            throw new RuntimeException("数据库类型错误：" + config.getJdbcType());

        }
        System.out.println("查询所有表信息sql：" + sql);
        JdbcUtil dbc = null;
        PreparedStatement ps = null;
        List<EntityVO> entitys = new ArrayList<>();
        try {
            dbc = new JdbcUtil(config);
            ps = dbc.getConnection().prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            List<DbColumn> dbColumns = initDbVo();
            while (rs.next()) {
                //得到表名
                EntityVO entity = initEntity(rs.getString(1));
                List<EntityAttrVO> entityAttrs = getColumnsByTableName(rs.getString(1), dbColumns);
                entity.setEntityAttrs(entityAttrs);
                entity.setTableComment(rs.getString(2));
                entitys.add(entity);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(dbc).close();
        }

        return entitys;
    }

    private List<DbColumn> initDbVo() {
        StringBuilder sb = new StringBuilder();
        String tableNamesJoin = config.getTable().getTableNamesJoin();
        if (DataBaseConstant.DATABASE_ORACLE.equals(config.getJdbcType())) {
            sb.append("select tb.table_name,tb.column_name,tb.data_type,cc.comments,(case when cu.column_name = tb.COLUMN_NAME then 'PRI' ELSE '' END) as column_key");
            sb.append(" from user_tab_columns tb");
            sb.append(" left join user_col_comments cc on tb.TABLE_NAME = cc.table_name and tb.column_name = cc.column_name");
            sb.append(" left join user_cons_columns cu on cu.table_name = tb.TABLE_NAME");
            sb.append(" left join user_constraints  au on au.constraint_name=cu.constraint_name");
            sb.append(" where 1=1 and au.constraint_type = 'P'");
            if (StringUtils.isNotEmpty(tableNamesJoin)) {
                sb.append(" and tb.TABLE_NAME in (").append(tableNamesJoin.toUpperCase()).append(")");
            }
            sb.append(" order by tb.COLUMN_ID");
        } else if (DataBaseConstant.DATABASE_MYSQL.equals(config.getJdbcType())) {
            sb.append("SELECT table_name,column_name,data_type,column_comment,column_key FROM COLUMNS WHERE 1=1");
            if (StringUtils.isNotEmpty(tableNamesJoin)) {
                sb.append(" and table_schema = '").append(config.getDataBaseName()).append("'");
                sb.append(" and table_name in (").append(tableNamesJoin.toLowerCase()).append(")");
            }
            sb.append(" ORDER BY ordinal_position");
        }
        System.out.println("查询所有列信息sql：" + sb.toString());
        JdbcUtil dbc = null;
        PreparedStatement ps = null;
        List<DbColumn> dbs = new ArrayList<>();
        try {
            dbc = new JdbcUtil(config);
            ps = dbc.getConnection().prepareStatement(sb.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DbColumn db = new DbColumn();
                db.setTableName(rs.getString(1));
                db.setColumnName(rs.getString(2));
                db.setColumnDataType(rs.getString(3));
                db.setComments(rs.getString(4));
                db.setIsPrimaryKey("PRI".equalsIgnoreCase(rs.getString(5)) ? "1" : "0");
                dbs.add(db);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(dbc).close();
        }
        return dbs;
    }

    /**
     * 根据表名初始化Entity对象
     * 一张表对应
     *
     * @param tableName 表名
     * @return
     */
    private EntityVO initEntity(String tableName) {
        tableName = tableName.toLowerCase();
        String className = config.getTable().getTableNameToEntityNameMapping().get(tableName);
        String ftlPath = config.getFtlPath();
        EntityVO entity = new EntityVO();
        entity.setAuthor(config.getAuthor());
        entity.setBasePackage(basePackage);
        entity.setClassName(className);
        entity.setTableName(tableName);
        entity.setFtlPath(ftlPath);
        entity.setBasePackageMap(config.getBasePackageMap());
        return entity;
    }

    /**
     * 根据数据库集合以及表名得到该表的属性集合
     *
     * @param tableName 表名
     * @param dbColumns 数据库列的集合
     * @return 属性对应值
     */
    private List<EntityAttrVO> getColumnsByTableName(String tableName, List<DbColumn> dbColumns) {
        List<EntityAttrVO> entityAttrs = new ArrayList<>();
        for (DbColumn dbColumn : dbColumns) {
            if (tableName.equals(dbColumn.getTableName())) {
                EntityAttrVO entityAttrVO = new EntityAttrVO();
                //使用枚举类进行操作
                DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(dbColumn.getColumnDataType().toUpperCase());
                entityAttrVO.setJavaType(dataTypeEnum.getJavaType());
                entityAttrVO.setFieldName(FieldNameUtil.underLineToUpperCase(dbColumn.getColumnName()));
                entityAttrVO.setIsPrimaryKey(dbColumn.getIsPrimaryKey());
                entityAttrVO.setComment(dbColumn.getComments());
                entityAttrVO.setJdbcFieldName(dbColumn.getColumnName());
                entityAttrVO.setJdbcType(dataTypeEnum.getMapperJdbcType());
                entityAttrs.add(entityAttrVO);
            }
        }
        return entityAttrs;
    }

}
