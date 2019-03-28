package com.justcs.controller;

import com.justcs.dao.AccountMapper;
import com.justcs.entity.Account;
import com.justcs.form.LoginForm;
import com.justcs.service.IAccountService;
import com.justcs.utils.Constrants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IAccountService accountService;

    @Resource
    private AccountMapper accountMapper;

    /**
     * 请求超管登录页面
     *
     * @return
     */
    @RequestMapping("/superuser")
    public String superuser() {
        return "super";
    }

    /**
     * 超管登录
     *
     * @param loginForm
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping("/surlogin")
    public String surlogin(@Valid LoginForm loginForm,
                           BindingResult bindingResult,
                           HttpServletRequest request
    ) {
        // 验证字段
        if (!bindingResult.hasErrors()) {
            Account usr = new Account();
            usr.setAccount(loginForm.getUserid());
            usr.setPwd(loginForm.getUserkl());
            Account record = accountService.userLogin(usr);
            if (record != null && record.getType() == 99) {
                record.setPwd("");
                HttpSession session = request.getSession();
                session.setAttribute(Constrants.CURRENT_USER, record);
                return "redirect:/test/reqadmin";
            }
        }
        return "redirect:/test/superuser";
    }

    /**
     * 请求管理员页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/reqadmin")
    public String adminOp(Model model) {
        List<Account> accountList = accountMapper.selectAllAdmins();
        model.addAttribute("objlist", accountList);
        return "admin";
    }

    /**
     * 添加账户
     *
     * @param acc
     * @param kl
     * @return
     */
    @RequestMapping("/addadmin")
    public String addadmin(@RequestParam(name = "acc") String acc,
                           @RequestParam(name = "kl") String kl,
                           Model model) {
        Account record = accountMapper.selectContainsAccount(acc);
        if (record == null) {
            Account account = new Account();
            account.setType(Constrants.TEACHER_TYPE);
            account.setAccount(acc);
            account.setPwd(kl);
            account.setSts((short) 0);
            account.setRegisttime(new Date(System.currentTimeMillis()));
            int rowaccount = accountMapper.insertSelective(account);
        } else {
            model.addAttribute("err", "添加账户失败，不能重复添加同账户名!");
        }
        return "redirect:/test/reqadmin";
    }


    /**
     * 启用账户
     *
     * @param id
     * @return
     */
    @RequestMapping("/pass")
    public String pass(@RequestParam(name = "accid") int id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        account.setSts((short) 1);
        int rowaccount = accountMapper.updateByPrimaryKeySelective(account);
        return "redirect:/test/reqadmin";
    }

    /**
     * 禁用账户
     *
     * @param id
     * @return
     */
    @RequestMapping("/unpass")
    public String unpass(@RequestParam(name = "accid") int id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        account.setSts((short) 0);
        int rowaccount = accountMapper.updateByPrimaryKeySelective(account);
        return "redirect:/test/reqadmin";
    }

    /**
     * 删除账户
     *
     * @param id
     * @return
     */
    @RequestMapping("/deladmin")
    public String deladmin(@RequestParam(name = "accid") int id) {
        int rowaccount = accountMapper.deleteByPrimaryKey(id);
        return "redirect:/test/reqadmin";
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(Constrants.CURRENT_USER, null);
        return "redirect:/test/superuser";
    }

}
