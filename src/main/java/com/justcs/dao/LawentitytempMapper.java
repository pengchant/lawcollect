package com.justcs.dao;

import com.justcs.entity.Lawentitytemp;
import com.justcs.view.InputLawView;
import com.justcs.view.ValidateLawView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LawentitytempMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Lawentitytemp record);

    int insertSelective(Lawentitytemp record);

    Lawentitytemp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Lawentitytemp record);

    int updateByPrimaryKeyWithBLOBs(Lawentitytemp record);

    int updateByPrimaryKey(Lawentitytemp record);

    /**
     * 通过法律的编号查询法律录入的内容
     *
     * @param lawid
     * @return
     */
    Lawentitytemp selectByLawId(@Param("lawid") String lawid);

    /**
     * 按条件查询录入的法律条文信息
     *
     * @param enterid
     * @param lawstatus
     * @param sortby
     * @param order
     * @param lawname
     * @return
     */
    List<InputLawView> selectInputingLaw(
            @Param("enterid") String enterid,
            @Param("lawstatus") String lawstatus,
            @Param("sortby") String sortby,
            @Param("order") String order,
            @Param("lawname") String lawname
    );

    /**
     * 批量删除记录
     *
     * @param array
     * @return
     */
    int batchDelLaws(@Param("array") int[] array);

    /**
     * 查询待校验的记录
     *
     * @param enterid
     * @param sortby
     * @param order
     * @return
     */
    List<ValidateLawView> selectValidatingLaw(
            @Param("enterid") String enterid,
            @Param("sortby") String sortby,
            @Param("order") String order,
            @Param("lawname") String lawname
    );
}