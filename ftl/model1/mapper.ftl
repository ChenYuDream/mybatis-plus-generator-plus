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
  
    <insert id="save${model.clazzName}" parameterType="${model.packageName}.${model.clazzName}" >
        insert into ${tableName} (
        <#list attrs as a><#if (attrs?size-1>a_index) >${a.jdbcField},<#else> ${a.jdbcField}</#if>
        </#list>)
        values (
        <#list attrs as a><#if (attrs?size-1>a_index) >${'#'}{${a.field},jdbcType=${a.jdbcType}},<#else>${'#'}{${a.field},jdbcType=${a.jdbcType}}</#if>
        </#list>)
    </insert>
  
    <delete id="delete${model.clazzName}ById" parameterType="java.lang.String" >
        delete from ${tableName}
        where ID = ${'#'}{id,jdbcType=VARCHAR}
    </delete>

    <update id="update${model.clazzName}" parameterType="${model.packageName}.${model.clazzName}" >
        update ${tableName}
        <set >
        <#list attrs as a>
        <#if (attrs?size-1>a_index) >
        ${a.jdbcField}=${'#'}{${a.field},jdbcType=${a.jdbcType}},
        <#else>
        ${a.jdbcField}=${'#'}{${a.field},jdbcType=${a.jdbcType}}
        </#if>
        </#list>
        </set>
        where ID = ${'#'}{id,jdbcType=VARCHAR}
    </update>
  
    <select id="select${model.clazzName}ById" resultMap="BaseResultMap" parameterType="java.lang.String" >
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        where ID = ${'#'}{id,jdbcType=VARCHAR}
    </select>

    <select id="selectAllByMap" resultMap="BaseResultMap" parameterType="java.util.Map" >
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        where 1=1
    </select>


</mapper>