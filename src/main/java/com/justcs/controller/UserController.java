package com.justcs.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.justcs.entity.Account;
import com.justcs.entity.Student;
import com.justcs.form.*;
import com.justcs.service.IAccountService;
import com.justcs.service.ILawService;
import com.justcs.utils.*;
import com.justcs.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/usr")
public class UserController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ILawService lawService;

    /**
     * 请求登录页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "login";
    }

    /**
     * 请求注册页面
     *
     * @return
     */
    @RequestMapping("/regist")
    public String sturegist() {
        return "regist";
    }

    /**
     * 注册
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/doregist")
    public WebJSONResult doregist(@Valid @RequestBody RegistForm registForm,
                                  BindingResult result,
                                  HttpServletRequest request) {
        if (result.hasErrors()) {
            return WebJSONResult.errorMsgData("字段验证不通过",
                    WebJSONResult.convertErrorToMap(result));
        }
        // 验证码校验
        HttpSession session = request.getSession();
        String valicode = (String) session.getAttribute(RandomCodeUtil.RANDOMCODEKEY);
        if (StringUtils.equalsIgnoreCase(valicode, registForm.getValicode())) {
            // 封装account
            Account account = new Account();
            account.setAccount(registForm.getStuid());
            account.setPwd(registForm.getStupwd());
            account.setSts(Constrants.STUDENT_REGISTED);
            account.setType(Constrants.STUDENT_TYPE);
            account.setRegisttime(new Date(System.currentTimeMillis()));
            // 封装Student
            Student student = new Student();
            student.setStuid(registForm.getStuid());
            student.setStuname(registForm.getStuname());
            student.setClassno(registForm.getClassno());
            student.setTips(registForm.getTips());
            boolean flag = accountService.registAccount(account, student);
            return flag ? WebJSONResult.ok()
                    : WebJSONResult.errorMsg("注册失败，您的学号可能已被注册!");
        } else {
            return WebJSONResult.errorMsg("验证码错误");
        }
    }

    /**
     * 登录接口
     *
     * @param loginForm
     * @param bindingResult
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public WebJSONResult dologin(@Valid @RequestBody LoginForm loginForm,
                                 BindingResult bindingResult,
                                 HttpSession session) {
        // 验证字段
        if (bindingResult.hasErrors()) {
            Map errors = WebJSONResult.convertErrorToMap(bindingResult);
            return WebJSONResult.errorMsg("字段验证不通过");
        }
        // 执行登录逻辑
        Account valirecord = new Account();
        valirecord.setAccount(loginForm.getUserid());
        valirecord.setPwd(loginForm.getUserkl());
        // 调用服务层接口
        Account record = accountService.userLogin(valirecord);
        if (record != null) { // 登录成功
            // 设置session(服务器端保存用户信息)
            session.setAttribute(Constrants.CURRENT_USER, record);
            // 如果是学生
            if (record.getType() == Constrants.STUDENT_TYPE) {
                // 查看学生的账号是否被审核
                Short sts = record.getSts();
                // 如果通过审核
                if (sts == Constrants.STUDENT_VALIDED) {
                    // 学生默认跳转到添加法律条文页面
                    return WebJSONResult.ok("/law/stuadd");
                } else {
                    return WebJSONResult.errorMsg("您的账号等待后台老师审核，请稍后重试!");
                }
            } else if (record.getType() == Constrants.TEACHER_TYPE) { // 如果是老师
                Short tsts = record.getSts();
                if (tsts == Constrants.TEACHER_OK) {
                    // 老师默认跳转到审核学生页面上
                    return WebJSONResult.ok("/usr/checkstu");
                } else {
                    return WebJSONResult.errorMsg("对不起您的账号已被系统冻结!");
                }
            }
        }
        // 登录失败
        return WebJSONResult.errorMsg("账号或者密码输入错误!");
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 清空session
        HttpSession session = request.getSession();
        session.removeAttribute(Constrants.CURRENT_USER);
        return "redirect:/";
    }

    /**
     * 按照条件查询分页学生的信息
     *
     * @param page
     * @param recPerPage
     * @param sortBy
     * @param order
     * @param search
     * @return
     */
    @RequestMapping("/getRegStus")
    @ResponseBody
    public JsonResult getRegStus(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int recPerPage,
                                 @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
                                 @RequestParam(name = "order", defaultValue = "") String order,
                                 @RequestParam(name = "search", defaultValue = "") String search) {
        // 解析search里面的字段
        StuValiInfoView_S stuSearch = null;
        if (StringUtils.isNotBlank(search)) {
            Gson gson = new Gson();
            try {
                stuSearch = gson.fromJson(search, StuValiInfoView_S.class);
            } catch (JsonSyntaxException e) {
                stuSearch = new StuValiInfoView_S(
                        "",
                        "",
                        String.valueOf(Constrants.STUDENT_REGISTED),
                        "");
            }
        } else {
            stuSearch = new StuValiInfoView_S("", "",
                    String.valueOf(Constrants.STUDENT_REGISTED), "");
        }
        // 查询分页数据
        PageInfo<StuValiInfoView> stuData = accountService.queryStuInfo(
                page, recPerPage, stuSearch.getStatus(),
                stuSearch.getStarttime(),
                stuSearch.getEndtime(),
                stuSearch.getStuname(),
                sortBy, order
        );
        if (stuData != null && stuData.getList().size() > 0) {
            return PageDataConvertTool.convert(
                    stuData,
                    Constrants.SUCCESS,
                    Constrants.SUCCESS_MESSAGE);
        }
        return PageDataConvertTool.convert(
                stuData,
                Constrants.ERROR,
                Constrants.NOT_DATA);
    }

    /**
     * 通过审核
     *
     * @param accountid
     * @return
     */
    @ResponseBody
    @RequestMapping("/passstu/{accountid}")
    public WebJSONResult passstu(@PathVariable(name = "accountid") int accountid) {
        if (accountid > 0) {
            boolean flag = accountService.passStuInfo(accountid);
            return flag ?
                    WebJSONResult.ok() :
                    WebJSONResult.errorMsg("通过审核失败，请稍后重试!");
        }
        return WebJSONResult.errorMsg("账户字段不能为空!");
    }

    /**
     * 不通过审核
     *
     * @param accountid
     * @return
     */
    @ResponseBody
    @RequestMapping("/unpassstu/{accountid}")
    public WebJSONResult unpassstu(@PathVariable(name = "accountid") int accountid) {
        if (accountid > 0) {
            boolean flag = accountService.unpassStuInfo(accountid);
            return flag ?
                    WebJSONResult.ok() :
                    WebJSONResult.errorMsg("请稍后重试!");
        }
        return WebJSONResult.errorMsg("账户字段不能为空!");
    }

    /**
     * 删除学生信息
     *
     * @param accountid
     * @return
     */
    @ResponseBody
    @RequestMapping("/delstu/{accountid}")
    public WebJSONResult delstu(@PathVariable(name = "accountid") int accountid) {
        if (accountid > 0) {
            boolean flag = accountService.removeAccount(accountid);
            return flag ?
                    WebJSONResult.ok() :
                    WebJSONResult.errorMsg("请稍后重试!");
        }
        return WebJSONResult.errorMsg("账户字段不能为空!");
    }


    /**
     * 批量通过
     *
     * @param accounts
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchpass")
    public WebJSONResult batchpass(@RequestBody int[] accounts) {
        boolean flag = accountService.batchPassStuInfo(accounts);
        return flag ?
                WebJSONResult.ok()
                : WebJSONResult.errorMsg("请稍后重试!");
    }


    /**
     * 批量不通过
     *
     * @param accounts
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchunpass")
    public WebJSONResult batchunpass(@RequestBody int[] accounts) {
        boolean flag = accountService.batchUnpassStuInfo(accounts);
        return flag ?
                WebJSONResult.ok()
                : WebJSONResult.errorMsg("请稍后重试!");
    }

    /**
     * 批量删除账户信息
     *
     * @param accounts
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchremovestu")
    public WebJSONResult batchremovestu(@RequestBody int[] accounts) {
        boolean flag = accountService.batchRemoveAccount(accounts);
        return flag ?
                WebJSONResult.ok()
                : WebJSONResult.errorMsg("请稍后重试!");
    }


    /**
     * 请求查看个人工作量
     *
     * @return
     */
    @RequestMapping("/personalwork")
    public String personalwork() {
        return "/student/personalwork";
    }


    /**
     * 查看个人工作量结果
     *
     * @param page
     * @param recPerPage
     * @param sortBy
     * @param order
     * @param search
     * @return
     */
    @ResponseBody
    @RequestMapping("/workedLaw")
    public JsonResult workedLaw(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int recPerPage,
                                @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
                                @RequestParam(name = "order", defaultValue = "") String order,
                                @RequestParam(name = "search", defaultValue = "") String search,
                                HttpServletRequest request) {
        // 当前用户
        Account current = WebJSONResult.getCurrentAccount(request);
        // 解析search里面的字段
        WorkedLawView_S stuSearch = null;
        if (StringUtils.isNotBlank(search)) {
            Gson gson = new Gson();
            try {
                stuSearch = gson.fromJson(search, WorkedLawView_S.class);
            } catch (JsonSyntaxException e) {
                stuSearch = new WorkedLawView_S("", "", "");
            }
        } else {
            stuSearch = new WorkedLawView_S("", "", "");
        }
        // 调用服务层组件查询
        PageInfo<WorkedLawView> workedLawViewPageInfo = lawService.workloadRecordsPageInfo(
                page,
                recPerPage,
                String.valueOf(current.getId()),
                stuSearch.getLawname(),
                sortBy,
                order,
                stuSearch.getStarttime(),
                stuSearch.getEndtime()
        );
        if (workedLawViewPageInfo != null && workedLawViewPageInfo.getList().size() > 0) {
            return PageDataConvertTool.convert(
                    workedLawViewPageInfo,
                    Constrants.SUCCESS,
                    Constrants.SUCCESS_MESSAGE);
        }
        return PageDataConvertTool.convert(
                workedLawViewPageInfo,
                Constrants.ERROR,
                Constrants.NOT_DATA);
    }

    /**
     * 请求查看个人信息
     *
     * @return
     */
    @RequestMapping("/persoalinfo")
    public String persoalinfo(Model model, HttpServletRequest request) {
        Account curuser = WebJSONResult.getCurrentAccount(request);
        StuInfoView stuInfoView = accountService.queryStuInfo(String.valueOf(curuser.getId()));
        model.addAttribute("curuser", stuInfoView);
        return "/student/personalinfo";
    }

    /**
     * 修改用户信息接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyPersonal")
    public WebJSONResult modifyPersonal(@RequestBody StuInfoView stuInfoView) {
        boolean flag = false;
        if (stuInfoView != null) {
            Student student = new Student();
            student.setId(Integer.valueOf(stuInfoView.getStuid()));
            student.setStuname(stuInfoView.getStuname());
            student.setClassno(stuInfoView.getClassno());
            student.setTips(stuInfoView.getTips());
            flag = accountService.modifyPersonalInfo(student);
        }
        return flag ? WebJSONResult.ok() : WebJSONResult.errorMsg("对不起操作失败，请稍后重试!");
    }

    /**
     * 请求修改密码页面
     *
     * @return
     */
    @RequestMapping("/modipwd")
    public String modipwd() {
        return "/basic/modifypass";
    }


    /**
     * 修改账户密码
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/modiStuPwd")
    public WebJSONResult modiStuPwd(@Valid @RequestBody ModifyUserPassForm modifyUserPassForm,
                                    HttpServletRequest request,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return WebJSONResult.errorMsgData("字段验证不通过",
                    WebJSONResult.convertErrorToMap(result));
        }
        Account currentAccount = WebJSONResult.getCurrentAccount(request);
        if (StringUtils.equals(currentAccount.getPwd(), modifyUserPassForm.getOldpass())) {
            // 执行更新密码操作
            currentAccount.setPwd(modifyUserPassForm.getNewPass());
            boolean flag = accountService.modifyPwd(currentAccount);
            return flag ? WebJSONResult.ok() : WebJSONResult.errorMsg("修改失败，请稍后重试!");
        } else {
            return WebJSONResult.errorMsg("旧密码不正确!");
        }
    }

    /**
     * 请求审核注册的学生页面
     *
     * @return
     */
    @RequestMapping("/checkstu")
    public String checkstu() {
        return "/teacher/checkstu";
    }


    /**
     * 输出验证码图片
     *
     * @param request
     * @param response
     */
    @RequestMapping("/checkcode")
    public void checkcode(HttpServletRequest request, HttpServletResponse response) {
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        RandomCodeUtil randomValidateCode = new RandomCodeUtil();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
