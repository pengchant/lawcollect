package com.justcs.dao;

import com.justcs.entity.Bodyofentry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BodyofentryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bodyofentry record);

    int insertSelective(Bodyofentry record);

    Bodyofentry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bodyofentry record);

    int updateByPrimaryKeyWithBLOBs(Bodyofentry record);

    int updateByPrimaryKey(Bodyofentry record);

    /**
     * 批量录入法律正文
     *
     * @param bodyofentryList
     * @return
     */
    int batchInsertLawEntity(@Param("list") List<Bodyofentry> bodyofentryList);

    /**
     * 通过法律的编号查询所有的法律条文正文
     *
     * @param lawid
     * @return
     */
    List<Bodyofentry> selectLawItems(@Param("lawid") String lawid);
}