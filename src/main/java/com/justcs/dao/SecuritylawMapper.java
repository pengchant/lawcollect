package com.justcs.dao;

import com.justcs.entity.Securitylaw;
import com.justcs.entity.SecuritylawWithBLOBs;
import com.justcs.view.LawValidatingView;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecuritylawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SecuritylawWithBLOBs record);

    int insertSelective(SecuritylawWithBLOBs record);

    SecuritylawWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecuritylawWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SecuritylawWithBLOBs record);

    int updateByPrimaryKey(Securitylaw record);

    /**
     * 查询是否已经存在相同的法律条文
     *
     * @param securitylaw
     * @return
     */
    Securitylaw selectContainsLaw(Securitylaw securitylaw);


    /**
     * 待审核的法律条文
     *
     * @param lawstatus
     * @param starttime
     * @param endtime
     * @param sortby
     * @param order
     * @return
     */
    List<LawValidatingView> selectValidating(
            @Param("lawstatus") String lawstatus,
            @Param("starttime") String starttime,
            @Param("endtime") String endtime,
            @Param("sortby") String sortby,
            @Param("order") String order,
            @Param("search") String search
    );


    /**
     * 检索法律
     *
     * @param sortby
     * @param order
     * @param inputer
     * @param checker
     * @param lawname
     * @param attribute
     * @return
     */
    List<LawValidatingView> selectValidated(
            @Param("sortby") String sortby,
            @Param("order") String order,
            @Param("inputer") String inputer,
            @Param("checker") String checker,
            @Param("lawname") String lawname,
            @Param("attribute") String attribute
    );


}