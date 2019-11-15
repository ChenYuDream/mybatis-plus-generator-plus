<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackageMap['dao'].packageName}.${basePackageMap['dao'].className}" >

    <resultMap id="BaseResultMap" type="${basePackageMap['dao'].packageName}.${basePackageMap['model'].className}" >
        <#list entityAttrs as a>
            <#if a.isPrimaryKey == "1">
        <id column="${a.jdbcFieldName}" property="${a.fieldName}" jdbcType="${a.jdbcType}" />
            </#if>
            <#if a.isPrimaryKey != "1">
        <result column="${a.jdbcFieldName}" property="${a.fieldName}" jdbcType="${a.jdbcType}" />
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List" >
        <#list entityAttrs as a><#if (entityAttrs?size-1>a_index) >${a.jdbcFieldName},<#else>${a.jdbcFieldName}</#if></#list>
    </sql>


</mapper>