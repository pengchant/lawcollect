package com.justcs.dao;

import com.justcs.entity.Lawattrlevel;

import java.util.List;

public interface LawattrlevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lawattrlevel record);

    int insertSelective(Lawattrlevel record);

    Lawattrlevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lawattrlevel record);

    int updateByPrimaryKey(Lawattrlevel record);

    List<Lawattrlevel> selectAllAttributes();
}