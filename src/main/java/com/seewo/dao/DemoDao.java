package com.seewo.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.seewo.po.DemoPo;

/** 
 * @Author: Nuwa 
 * @Description: 由IDEA插件Nuwa生成的接口
 */
@Repository
@Mapper
public interface DemoDao {

    void add(DemoPo demoPo);

    String getByName(@Param("name") String name);
}

