package com.justcs.dao;

import com.justcs.entity.Account;
import com.justcs.view.StuInfoView;
import com.justcs.view.StuValiInfoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> selectAllAccount(@Param("sortby") String sortBy, @Param("order") String order, @Param("search") String search);


    /**
     * 查询是否存在某个人的账号
     *
     * @param account
     * @return
     */
    Account selectContainsAccount(@Param("account") String account);

    /**
     * @param status    学生记录的状态
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @param stuname   模糊查询学生的姓名
     * @param sortby    排序的字段名称
     * @param order     排序的方式 升序还是降序
     * @return
     */
    List<StuValiInfoView> selectStudentsInfo(
            @Param("status") String status,
            @Param("starttime") String starttime,
            @Param("endtime") String endtime,
            @Param("stuname") String stuname,
            @Param("sortby") String sortby,
            @Param("order") String order
    );

    /**
     * 批量更新用户的状态
     *
     * @param accounts
     * @param stusts
     * @return
     */
    int batupdsts(@Param("array") int[] accounts, @Param("stusts") String stusts);

    /**
     * 批量删除账户+学生的信息
     *
     * @param accounts
     * @return
     */
    int batdelstsandaccount(@Param("array") int[] accounts);

    /**
     * 查询学生的信息
     *
     * @param accountid
     * @return
     */
    StuInfoView selectRegistStuInfo(@Param("accountid") String accountid);

    List<Account> selectAllAdmins();
}