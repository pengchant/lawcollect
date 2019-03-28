package com.justcs.service;

import com.github.pagehelper.PageInfo;
import com.justcs.entity.Account;
import com.justcs.entity.Student;
import com.justcs.view.StuInfoView;
import com.justcs.view.StuValiInfoView;

public interface IAccountService {

    PageInfo<Account> getAccount(int page, int recPerPage, String sortBy, String order, String search);

    /**
     * 学生注册
     *
     * @param account
     * @param student
     * @return
     */
    boolean registAccount(Account account, Student student);

    /**
     * 用户登录
     *
     * @param account
     * @return
     */
    Account userLogin(Account account);

    /**
     * 分页查询学生的记录
     *
     * @param page       页号
     * @param recPerPage 每页的记录数
     * @param status     学生记录的状态
     * @param starttime  开始时间
     * @param endtime    结束时间
     * @param stuname    模糊查询学生的姓名
     * @param sortby     按照某个字段
     * @param order      某个字段的排序方式
     * @return
     */
    PageInfo<StuValiInfoView> queryStuInfo(
            int page,
            int recPerPage,
            String status,
            String starttime,
            String endtime,
            String stuname,
            String sortby,
            String order
    );

    /**
     * 通过审核
     *
     * @param account
     * @return
     */
    boolean passStuInfo(int account);

    /**
     * 批量通过审核
     *
     * @param accounts
     * @return
     */
    boolean batchPassStuInfo(int[] accounts);


    /**
     * 不通过审核
     *
     * @param account
     * @return
     */
    boolean unpassStuInfo(int account);

    /**
     * 批量不通过审核
     *
     * @param accounts
     * @return
     */
    boolean batchUnpassStuInfo(int[] accounts);


    /**
     * 删除账户信息
     *
     * @param account
     * @return
     */
    boolean removeAccount(int account);


    /**
     * 批量删除账户(包括学生信息一起删除)
     *
     * @param accounts
     * @return
     */
    boolean batchRemoveAccount(int[] accounts);

    /**
     * 根据账户的编号查询学生的信息
     *
     * @param accountid
     * @return
     */
    StuInfoView queryStuInfo(String accountid);

    /**
     * 修改学生的信息
     *
     * @param student
     * @return
     */
    boolean modifyPersonalInfo(Student student);

    /**
     * 修改账户的密码
     * @param account
     * @return
     */
    boolean modifyPwd(Account account);

}
