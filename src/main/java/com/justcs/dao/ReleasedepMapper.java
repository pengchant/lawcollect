package com.justcs.dao;

import com.justcs.entity.Releasedep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReleasedepMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Releasedep record);

    int insertSelective(Releasedep record);

    Releasedep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Releasedep record);

    int updateByPrimaryKey(Releasedep record);

    /**
     * 查询所有部门
     *
     * @return
     */
    List<Releasedep> selectAllDep();


    /**
     * 通过名称查询部门信息
     *
     * @param depname
     * @return
     */
    Releasedep selectDepByName(@Param("depname") String depname);
}