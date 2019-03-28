package com.justcs.dao;

import com.justcs.entity.Workloadrecord;
import com.justcs.view.WorkedLawView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkloadrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Workloadrecord record);

    int insertSelective(Workloadrecord record);

    Workloadrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Workloadrecord record);

    int updateByPrimaryKey(Workloadrecord record);

    /**
     * 查询工作实体
     *
     * @param accountid
     * @param search
     * @param sortby
     * @param order
     * @return
     */
    List<WorkedLawView> selectWorkedLaws(
            @Param("accountid") String accountid,
            @Param("search") String search,
            @Param("sortby") String sortby,
            @Param("order") String order,
            @Param("starttime") String starttime,
            @Param("endtime") String endtime
    );

    /**
     * 通过法律的编号和账户的编号查询工作量信息
     *
     * @param accountid
     * @param lawid
     * @return
     */
    Workloadrecord selectByLawidAndAcId(
            @Param("accountid") String accountid,
            @Param("lawid") String lawid
    );
}