package com.justcs.utils;

public interface Constrants {

    public static final String SUCCESS = "success";

    public static final String ERROR = "error";

    public static final String SUCCESS_MESSAGE = "操作成功!";

    public static final String ERROR_MESSAGE = "操作失败!";

    public static final String CURRENT_USER = "current_user";

    public static final String NOT_DATA = "暂无数据!";


    ////////////账户类型/////////////
    /**
     * 学生账户类型
     */
    public static final Short STUDENT_TYPE = 0;

    /**
     * 教师账户类型
     */
    public static final Short TEACHER_TYPE = 1;


    //////////学生账户状态信息////////////
    /**
     * 学生 注册 - 0
     */
    public static final Short STUDENT_REGISTED = 0;

    /**
     * 学生 审核 -1
     */
    public static final Short STUDENT_VALIDED = 1;

    /**
     * 学生 审核不通过 -2
     */
    public static final Short STUDENT_UNPASS = 2;


    //////////////////管理员账户信息///////////////////
    public static final Short TEACHER_OK = 1;

    public static final Short TEACHER_ERROR = 0;


    //////////////法律条文状态///////////////
    /**
     * 正在录入
     */
    public static final Short LAW_INPUTING = 0;

    /**
     * 录入完成
     */
    public static final Short LAW_COMPLETE = 1;

    /**
     * 法律被锁
     */
    public static final Short LAW_LOCKED = 99;

    /**
     * 通过校验
     */
    public static final Short LAW_CHECKED = 2;

    /**
     * 通过审核
     */
    public static final Short LAW_PASSED = 3;


    /**
     * 作废法律条文(不能入库)
     */
    public static final Short LAW_DELETED = -1;


    ///////录入法律条文//////////////
    public static final Short TEMP_SAVE = 0;
    public static final Short FINAL_SAVE = 1;


    ////////////////工作量类型//////////////////
    public static final Short WORK_LURU = 1;

    public static final Short WORK_CHECK = 2;


}
