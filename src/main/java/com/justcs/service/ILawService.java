package com.justcs.service;

import com.github.pagehelper.PageInfo;
import com.justcs.entity.*;
import com.justcs.form.*;
import com.justcs.view.*;

import java.util.List;

public interface ILawService {

    /**
     * 查询所有部门
     *
     * @return
     */
    List<Releasedep> queryAllReleaseDep();

    /**
     * 录入部门：如果有就默认不处理，如果没有就录入并将结果返回
     *
     * @param depname
     * @return
     */
    Releasedep queryReleaseDep(String depname);

    /**
     * 录入法律条文服务
     *
     * @param securitylaw
     * @return
     */
    boolean addLawRecordService(SecuritylawWithBLOBs securitylaw,
                                Short savetype,
                                Account account,
                                Lawentitytemp saveTemp);

    /**
     * 分页查询正在输入的法律条文信息
     *
     * @param page
     * @param recPerPage
     * @param enterid
     * @param lawstatus
     * @param sortby
     * @param order
     * @return
     */
    PageInfo<InputLawView> queryPagedInpting(
            int page,
            int recPerPage,
            String enterid,
            Short lawstatus,
            String sortby,
            String order,
            String search
    );

    /**
     * 删除某一个法律条文记录
     *
     * @param lawid
     * @return
     */
    boolean delInputLaw(int lawid);

    /**
     * 批量删除某个法律条文记录
     *
     * @param laws
     * @return
     */
    boolean batchDelInputLaw(int[] laws);

    /**
     * 通过法律的编号查询详情
     *
     * @param lawid
     * @return
     */
    LawSubmitForm queryLawDetail(int lawid);

    /**
     * 查询待验证的法律条文
     *
     * @param page
     * @param recPerPage
     * @param sortby
     * @param order
     * @param accountid
     * @param search
     * @return
     */
    PageInfo<ValidateLawView> queryStuValiInfoView(
            int page,
            int recPerPage,
            String sortby,
            String order,
            String accountid,
            String search
    );

    /**
     * 开始验证
     *
     * @param lawid
     * @param accountid
     * @return
     */
    boolean startValidate(int lawid, int accountid);


    /**
     * 通过验证法律条文
     *
     * @param securitylaw
     * @param lawentitytemp
     * @return
     */
    boolean validaeLaws(SecuritylawWithBLOBs securitylaw, Lawentitytemp lawentitytemp);


    /**
     * 分页查询工作量
     *
     * @param page
     * @param recPerPage
     * @param accountid
     * @param search
     * @param sortby
     * @param order
     * @param starttime
     * @param endtime
     * @return
     */
    PageInfo<WorkedLawView> workloadRecordsPageInfo(
            int page,
            int recPerPage,
            String accountid,
            String search,
            String sortby,
            String order,
            String starttime,
            String endtime
    );


    /**
     * 查询待审核的法律条文
     *
     * @param page
     * @param recPerPage
     * @param lawstatus
     * @param starttime
     * @param endtime
     * @param sortby
     * @param order
     * @param search
     * @return
     */
    PageInfo<LawValidatingView> queryLawvalidating(
            int page,
            int recPerPage,
            String lawstatus,
            String starttime,
            String endtime,
            String sortby,
            String order,
            String search
    );

    /**
     * 检索法律条文
     *
     * @param page
     * @param recPerPage
     * @param sortby
     * @param order
     * @param inputer
     * @param checker
     * @param lawname
     * @param attributes
     * @return
     */
    PageInfo<SearchLawView> queryValidatedLaw(
            int page,
            int recPerPage,
            String sortby,
            String order,
            String inputer,
            String checker,
            String lawname,
            String[] attributes
    );


    /**
     * 查询法律的录入和校验的详情
     *
     * @param lawid
     * @return
     */
    CheckLawEntryView queryCheckLawDetail(int lawid);


    /**
     * 校验法律
     *
     * @param checkLawSubForm
     * @param account
     * @param result
     * @return
     */
    boolean checkLaw(CheckLawSubForm checkLawSubForm, Account account, Short result);

    /**
     * 获取所有法律属性
     *
     * @return
     */
    List<LawAttributeNode> getLawAttributeNodes();
}
