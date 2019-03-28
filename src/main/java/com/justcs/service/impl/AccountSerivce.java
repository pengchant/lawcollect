package com.justcs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.justcs.dao.AccountMapper;
import com.justcs.dao.StudentMapper;
import com.justcs.entity.Account;
import com.justcs.entity.Student;
import com.justcs.view.StuInfoView;
import com.justcs.view.StuValiInfoView;
import com.justcs.service.IAccountService;
import com.justcs.utils.Constrants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("accountService")
public class AccountSerivce implements IAccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private StudentMapper studentMapper;

    @Override
    public PageInfo<Account> getAccount(int page, int recPerPage, String sortBy, String order, String search) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<Account> testPaperPageInfo = new PageInfo<>(accountMapper.selectAllAccount(sortBy, order, search));
        return testPaperPageInfo;
    }

    /**
     * 学生注册
     *
     * @param account
     * @param student
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean registAccount(Account account, Student student) {
        if (account != null && student != null) {
            // 1.查询是否存在学生
            Account recordAccount = accountMapper.selectContainsAccount(account.getAccount());
            // 2.插入账户信息
            if (recordAccount != null) {
                return false;
            }
            int affedrows = accountMapper.insertSelective(account);
            // 3.插入学生信息
            if (affedrows <= 0) {
                return false;
            }
            student.setAccountid(account.getId());
            int affedsturows = studentMapper.insertSelective(student);
            if (affedsturows > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用户登录
     *
     * @param account
     * @return
     */
    @Override
    public Account userLogin(Account account) {
        if (account != null) {
            // 查询账户信息
            Account record = accountMapper.selectContainsAccount(account.getAccount());
            // 匹配和密码是否相同
            if (record != null) {
                if (StringUtils.equals(account.getPwd(), record.getPwd())) {
                    return record;
                }
            }
        }
        return null;
    }

    /**
     * 按照条件分页查询学生的记录数
     *
     * @param page       页号
     * @param recPerPage 每页的记录数
     * @param status     *学生记录的状态
     * @param starttime  *开始时间
     * @param endtime    *结束时间
     * @param stuname    *模糊查询学生的姓名
     * @param sortby     按照某个字段
     * @param order      某个字段的排序方式
     * @return
     */
    @Override
    public PageInfo<StuValiInfoView> queryStuInfo(int page, int recPerPage, String status, String starttime, String endtime, String stuname, String sortby, String order) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<StuValiInfoView> stuPageData = new PageInfo<>(accountMapper.selectStudentsInfo(
                status,
                starttime,
                endtime,
                stuname,
                sortby,
                order
        ));
        return stuPageData;
    }

    /**
     * 通过审核
     *
     * @param account
     * @return
     */
    @Override
    public boolean passStuInfo(int account) {
        int[] accs = {account};
        return batchPassStuInfo(accs);
    }

    /**
     * 批量通过审核
     *
     * @param accounts
     * @return
     */
    @Override
    public boolean batchPassStuInfo(int[] accounts) {
        if (accounts != null && accounts.length > 0) {
            int rows = accounts.length;
            int affected = accountMapper.batupdsts(accounts, String.valueOf(Constrants.STUDENT_VALIDED));
            return rows == affected;
        }
        return false;
    }

    /**
     * 不通过审核
     *
     * @param account
     * @return
     */
    @Override
    public boolean unpassStuInfo(int account) {
        int[] accs = {account};
        return batchUnpassStuInfo(accs);
    }

    /**
     * 批量不通过审核
     *
     * @param accounts
     * @return
     */
    @Override
    public boolean batchUnpassStuInfo(int[] accounts) {
        if (accounts != null && accounts.length > 0) {
            int rows = accounts.length;
            int affected = accountMapper.batupdsts(accounts, String.valueOf(Constrants.STUDENT_UNPASS));
            return rows == affected;
        }
        return false;
    }

    /**
     * 删除账户学生信息
     *
     * @param account
     * @return
     */
    @Override
    public boolean removeAccount(int account) {
        int[] accs = {account};
        return batchRemoveAccount(accs);
    }

    /**
     * 批量删除学生账户信息
     *
     * @param accounts
     * @return
     */
    @Override
    public boolean batchRemoveAccount(int[] accounts) {
        if (accounts != null && accounts.length > 0) {
            int rows = accounts.length;
            int affected = accountMapper.batdelstsandaccount(accounts);
            return affected >= rows;
        }
        return false;
    }

    /**
     * 查询账户的信息
     *
     * @param accountid
     * @return
     */
    @Override
    public StuInfoView queryStuInfo(String accountid) {
        return accountMapper.selectRegistStuInfo(accountid);
    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @Override
    public boolean modifyPersonalInfo(Student student) {
        return studentMapper.updateByPrimaryKeySelective(student) > 0;
    }

    /**
     * 修改密码
     *
     * @param account
     * @return
     */
    @Override
    public boolean modifyPwd(Account account) {
        return accountMapper.updateByPrimaryKeySelective(account) > 0;
    }


}
