<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${dao.packageName}.${dao.clazzName}" >

    <resultMap id="BaseResultMap" type="${model.packageName}.${model.clazzName}" >
        <#list attrs as a>
        <#if a.isPrimaryKey == "1">
        <id column="${a.jdbcField}" property="${a.field}" jdbcType="${a.jdbcType}" />
        </#if>
        </#list>
        <#list attrs as a>
        <#if a.isPrimaryKey != "1">
        <result column="${a.jdbcField}" property="${a.field}" jdbcType="${a.jdbcType}" />
        </#if>
        </#list>
    </resultMap>
  
    <sql id="Base_Column_List" >
        <#list attrs as a><#if (attrs?size-1>a_index) >${a.jdbcField},<#else>${a.jdbcField}</#if></#list>
    </sql>


</mapper>