package com.justcs.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.justcs.entity.Account;
import com.justcs.entity.Lawentitytemp;
import com.justcs.entity.Releasedep;
import com.justcs.entity.SecuritylawWithBLOBs;
import com.justcs.form.CheckLawSubForm;
import com.justcs.form.LawSubmitForm;
import com.justcs.service.ILawService;
import com.justcs.utils.Constrants;
import com.justcs.utils.JsonResult;
import com.justcs.utils.PageDataConvertTool;
import com.justcs.utils.WebJSONResult;
import com.justcs.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping("/law")
public class LawController {

    @Autowired
    private ILawService lawService;

    /**
     * 请求正在录入列表
     *
     * @return
     */
    @RequestMapping("/stuadd")
    public String stuadd() {
        return "/student/addlaw";
    }

    /**
     * 查询正在录入的法律条文
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/stuinputing")
    public JsonResult stuinputing(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int recPerPage,
                                  @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
                                  @RequestParam(name = "order", defaultValue = "") String order,
                                  @RequestParam(name = "search", defaultValue = "") String search,
                                  HttpServletRequest request) {
        InputLawView_S searchstr = null;
        if (StringUtils.isNotBlank(search)) {
            try {
                Gson gson = new Gson();
                searchstr = gson.fromJson(search, InputLawView_S.class);
            } catch (JsonSyntaxException e) {
                searchstr = new InputLawView_S("");
            }
        } else {
            searchstr = new InputLawView_S("");
        }
        Account current = WebJSONResult.getCurrentAccount(request);
        PageInfo<InputLawView> inputLawViewPageInfo = lawService.queryPagedInpting(
                page,
                recPerPage,
                String.valueOf(current.getId()),
                Constrants.LAW_INPUTING,
                sortBy,
                order,
                searchstr.getLawname()
        );
        if (inputLawViewPageInfo != null && inputLawViewPageInfo.getList().size() > 0) {
            return PageDataConvertTool.convert(
                    inputLawViewPageInfo,
                    Constrants.SUCCESS,
                    Constrants.SUCCESS_MESSAGE);
        }
        return PageDataConvertTool.convert(
                inputLawViewPageInfo,
                Constrants.ERROR,
                Constrants.NOT_DATA);
    }


    /**
     * 请求添加/(暂存)法律条文页面
     *
     * @return
     */
    @RequestMapping("/stuaddetail")
    public String stuaddetail(Model model, @RequestParam(defaultValue = "0") int lawid) {
        // 查询所有部门
        model.addAttribute("releasedeps", lawService.queryAllReleaseDep());
        // 查询所有的法律属性
        model.addAttribute("lawattribute", lawService.getLawAttributeNodes());
        // 查询对应的法律条文信息
        if (lawid != 0) {
            LawSubmitForm lawSubmitForm = lawService.queryLawDetail(lawid);
            model.addAttribute("lawsadded", lawSubmitForm);
        }
        return "/student/addlaw_detail";
    }

    /**
     * 删除某个法律条文
     *
     * @param lawid
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteLaw")
    public WebJSONResult deleteLaw(@RequestParam(required = true) int lawid) {
        boolean flag = lawService.delInputLaw(lawid);
        return flag ? WebJSONResult.ok() : WebJSONResult.errorMsg("对不起删除失败，请稍后重试");
    }

    /**
     * 批量删除法律条文
     *
     * @param laws
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchdeleteLaw")
    public WebJSONResult batchdeleteLaw(@RequestBody int[] laws) {
        boolean flag = lawService.batchDelInputLaw(laws);
        return flag ? WebJSONResult.ok() : WebJSONResult.errorMsg("对不起删除失败，请稍后重试");
    }

    /**
     * 录入法律条文
     *
     * @param lawSubmitForm
     * @param optype
     * @return
     */
    @RequestMapping("/tempsave/{optype}")
    @ResponseBody
    public WebJSONResult tempsave(@RequestBody LawSubmitForm lawSubmitForm,
                                  @PathVariable(name = "optype") Short optype,
                                  HttpServletRequest request) {
        // 检查法律条文和标准是否为空
        if (StringUtils.isNotBlank(lawSubmitForm.getLawname())
                || StringUtils.isNotBlank(lawSubmitForm.getVersion())) {
            SecuritylawWithBLOBs securitylaw = new SecuritylawWithBLOBs();

            // 获取当前账户信息
            Account currentUser = WebJSONResult.getCurrentAccount(request);
            if (currentUser != null) {
                // 设置法律信息
                securitylaw.setId(lawSubmitForm.getLawid());
                securitylaw.setLawname(lawSubmitForm.getLawname());
                securitylaw.setVersion(lawSubmitForm.getVersion());
                securitylaw.setKeyterms(lawSubmitForm.getKeyterms());
                // 属性去掉"[]只保留,
                String attri = lawSubmitForm.getLawattributes();
                if (attri != null) {
                    attri = attri.replaceAll("[\\]\\[\\\"]", "");
                }
                securitylaw.setLawattributes(attri);
                securitylaw.setUpperregulations(lawSubmitForm.getUpperregulations());
                securitylaw.setEnterid(currentUser.getId());
                securitylaw.setEntertime(new Date(System.currentTimeMillis()));
                // 执行发布部门逻辑
                String firstdep = lawSubmitForm.getFirstreleasedep();
                String secondep = lawSubmitForm.getCopubdep();
                Releasedep firstDep = lawService.queryReleaseDep(lawSubmitForm.getFirstreleasedep());
                Releasedep secondDep = lawService.queryReleaseDep(lawSubmitForm.getCopubdep());
                if (firstDep != null) {
                    securitylaw.setFirstreldepid(firstDep.getId());
                }
                if (secondDep != null) {
                    securitylaw.setCopubdepid(secondDep.getId());
                }
                // 法律条文正文
                Lawentitytemp lawentitytemp = new Lawentitytemp();
                lawentitytemp.setId(lawSubmitForm.getLawcontentid());
                lawentitytemp.setLawid(lawSubmitForm.getLawid());
                lawentitytemp.setInputtime(new Date(System.currentTimeMillis()));
                lawentitytemp.setAccountid(currentUser.getId());
                lawentitytemp.setLawcontent(lawSubmitForm.getMaincontent());
                // 执行暂存/录入操作
                boolean flag = lawService.addLawRecordService(securitylaw, optype, currentUser, lawentitytemp);
                return flag ? WebJSONResult.ok() :
                        WebJSONResult.errorMsg("录入失败,该法律条文可能已被录入，请稍后重试!");
            } else {
                return WebJSONResult.errorMsg("您的账户已经过期，请重新登录!");
            }
        } else {
            return WebJSONResult.errorMsg("操作错误，法律条文和版本信息不能为空!");
        }
    }


    /**
     * 请求待校验的法律条文
     *
     * @return
     */
    @RequestMapping("/stucheck")
    public String stucheck() {
        return "/student/checklaw";
    }


    /**
     * 请求待验证的法律信息
     *
     * @param page
     * @param recPerPage
     * @param sortBy
     * @param order
     * @param search
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/valiLaw")
    public JsonResult valiLaw(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int recPerPage,
            @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(name = "order", defaultValue = "") String order,
            @RequestParam(name = "search", defaultValue = "") String search,
            HttpServletRequest request
    ) {
        // 获取当前账户
        Account account = WebJSONResult.getCurrentAccount(request);
        // 获取search参数
        ValidateLawView_S searchstr = null;
        if (StringUtils.isNotBlank(search)) {
            try {
                Gson gson = new Gson();
                searchstr = gson.fromJson(search, ValidateLawView_S.class);
            } catch (JsonSyntaxException e) {
                searchstr = new ValidateLawView_S("");
            }
        } else {
            searchstr = new ValidateLawView_S("");
        }
        // 查询分页的信息
        PageInfo<ValidateLawView> validateLawViewPageInfo =
                lawService.queryStuValiInfoView(
                        page,
                        recPerPage,
                        sortBy,
                        order,
                        String.valueOf(account.getId()),
                        searchstr.getLawname()
                );
        if (validateLawViewPageInfo != null && validateLawViewPageInfo.getList().size() > 0) {
            return PageDataConvertTool.convert(
                    validateLawViewPageInfo,
                    Constrants.SUCCESS,
                    Constrants.SUCCESS_MESSAGE);
        }
        return PageDataConvertTool.convert(
                validateLawViewPageInfo,
                Constrants.ERROR,
                Constrants.NOT_DATA);
    }

    /**
     * 开始验证法律
     *
     * @param lawid
     * @return
     */
    @ResponseBody
    @RequestMapping("/startValidate/{lawid}")
    public WebJSONResult startValidate(@PathVariable("lawid") int lawid
            , HttpServletRequest request) {
        Account current = WebJSONResult.getCurrentAccount(request);
        boolean flag = lawService.startValidate(lawid, current.getId());
        return flag ? WebJSONResult.ok() :
                WebJSONResult.errorMsg("对不起此条文正被其他人验证中!");
    }

    /**
     * 请求校验法律条文详细页面
     *
     * @return
     */
    @RequestMapping("/stusurecheck/{lawid}")
    public String stusurecheck(@PathVariable("lawid") int lawid, Model model) {
        // 查询所有部门
        model.addAttribute("releasedeps", lawService.queryAllReleaseDep());
        // 查询所有的法律属性
        model.addAttribute("lawattribute", lawService.getLawAttributeNodes());
        // 查询对应的法律条文信息
        if (lawid != 0) {
            LawSubmitForm lawSubmitForm = lawService.queryLawDetail(lawid);
            model.addAttribute("lawsadded", lawSubmitForm);
        }
        return "/student/checklaw_detail";
    }

    /**
     * 通过验证法律条文
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/validatedLaw")
    public WebJSONResult validatedLaw(
            @RequestBody LawSubmitForm lawSubmitForm,
            HttpServletRequest request) throws ParseException {
        boolean flag = false;
        SecuritylawWithBLOBs securitylaw = new SecuritylawWithBLOBs();
        // 获取当前账户信息
        Account currentUser = WebJSONResult.getCurrentAccount(request);
        if (currentUser != null) {
            // 设置法律信息
            securitylaw.setId(lawSubmitForm.getLawid());
            securitylaw.setLawname(lawSubmitForm.getLawname());
            securitylaw.setVersion(lawSubmitForm.getVersion());
            securitylaw.setKeyterms(lawSubmitForm.getKeyterms());
            // 属性去掉"[]只保留,
            String attri = lawSubmitForm.getLawattributes();
            if (attri != null) {
                attri = attri.replaceAll("[\\]\\[\\\"]", "");
            }
            securitylaw.setLawattributes(attri);
            securitylaw.setUpperregulations(lawSubmitForm.getUpperregulations());
            // 执行发布部门逻辑
            String firstdep = lawSubmitForm.getFirstreleasedep();
            String secondep = lawSubmitForm.getCopubdep();
            Releasedep firstDep = lawService.queryReleaseDep(lawSubmitForm.getFirstreleasedep());
            Releasedep secondDep = lawService.queryReleaseDep(lawSubmitForm.getCopubdep());
            if (firstDep != null) {
                securitylaw.setFirstreldepid(firstDep.getId());
            }
            if (secondDep != null) {
                securitylaw.setCopubdepid(secondDep.getId());
            }
            // 设置验证的编号时间
            securitylaw.setCheckerid(currentUser.getId());
            securitylaw.setCheckedtime(new Date(System.currentTimeMillis()));

            // 法律条文正文
            Lawentitytemp lawentitytemp = new Lawentitytemp();
            lawentitytemp.setId(lawSubmitForm.getLawcontentid());
            lawentitytemp.setLawid(lawSubmitForm.getLawid());
            lawentitytemp.setLawcontent(lawSubmitForm.getMaincontent());
            // 执行校验
            flag = lawService.validaeLaws(securitylaw, lawentitytemp);
        }
        return flag ? WebJSONResult.ok() :
                WebJSONResult.errorMsg("校验失败，请稍后重试!");
    }


    /**
     * 请求审核录入的法律条文页面
     *
     * @return
     */
    @RequestMapping("/passlaw")
    public String passlaw() {
        return "/teacher/passlaw";
    }


    /**
     * 请求查询录入法律条文页面
     *
     * @return
     */
    @RequestMapping("/searchlaw")
    public String searchlaw(Model model) {
        // 查询所有的法律属性
        model.addAttribute("lawattribute", lawService.getLawAttributeNodes());
        return "/teacher/searchlaw";
    }


    /**
     * 查询所有待审核的法律的条文信息
     *
     * @param page
     * @param recPerPage
     * @param sortBy
     * @param order
     * @param search
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/valiLawsEntrys")
    public JsonResult valiLawsEntry(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int recPerPage,
                                    @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
                                    @RequestParam(name = "order", defaultValue = "") String order,
                                    @RequestParam(name = "search", defaultValue = "") String search,
                                    HttpServletRequest request) {
        LawValidatingView_S searchObj = null;
        if (StringUtils.isNotBlank(search)) {
            try {
                Gson gson = new Gson();
                searchObj = gson.fromJson(search, LawValidatingView_S.class);
            } catch (JsonSyntaxException e) {
                searchObj = new LawValidatingView_S(
                        "",
                        "",
                        "",
                        String.valueOf(Constrants.LAW_CHECKED)
                );
            }
        } else {
            searchObj = new LawValidatingView_S(
                    "",
                    "",
                    "",
                    String.valueOf(Constrants.LAW_CHECKED)
            );
        }
        PageInfo<LawValidatingView> lawValidatingViewPageInfo =
                lawService.queryLawvalidating(
                        page,
                        recPerPage,
                        searchObj.getStatus(),
                        searchObj.getStarttime(), searchObj.getEndtime(),
                        sortBy,
                        order,
                        searchObj.getLawname()
                );
        if (lawValidatingViewPageInfo != null && lawValidatingViewPageInfo.getList().size() > 0) {
            return PageDataConvertTool.convert(
                    lawValidatingViewPageInfo,
                    Constrants.SUCCESS,
                    Constrants.SUCCESS_MESSAGE);
        }
        return PageDataConvertTool.convert(
                lawValidatingViewPageInfo,
                Constrants.ERROR,
                Constrants.NOT_DATA);

    }

    /**
     * 请求法律条文详情
     *
     * @param lawid
     * @param model
     * @return
     */
    @RequestMapping("/lawDetail/{lawid}")
    public String lawDetail(@PathVariable("lawid") int lawid,
                            Model model,
                            @RequestParam(name = "optype", defaultValue = "0") String optype) {
        CheckLawEntryView checkLawEntryView = lawService.queryCheckLawDetail(lawid);
        checkLawEntryView.setOptype(optype);
        model.addAttribute("obj", checkLawEntryView);
        return "/teacher/passlaw_detail";
    }

    /**
     * 查看法律条文详情
     *
     * @param lawid
     * @param model
     * @param optype
     * @return
     */
    @RequestMapping("/seeworkdetail/{lawid}")
    public String seeworkdetail(@PathVariable("lawid") int lawid,
                                Model model,
                                @RequestParam(name = "optype", defaultValue = "0") String optype) {
        CheckLawEntryView checkLawEntryView = lawService.queryCheckLawDetail(lawid);
        checkLawEntryView.setOptype(optype);
        model.addAttribute("obj", checkLawEntryView);
        return "/student/personalwork_detail";
    }

    /**
     * 审核法律条文
     *
     * @param checkLawSubForm
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkLaw/{optype}")
    public WebJSONResult checkLaw(@RequestBody CheckLawSubForm checkLawSubForm,
                                  @PathVariable(name = "optype") int optype,
                                  HttpServletRequest request,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return WebJSONResult.errorMsgData("验证字段有误", result.getFieldErrors());
        }
        Account account = WebJSONResult.getCurrentAccount(request);
        boolean flag = lawService.checkLaw(checkLawSubForm,
                account,
                optype == 1 ? Constrants.LAW_PASSED : Constrants.LAW_DELETED);
        return flag ? WebJSONResult.ok() : WebJSONResult.errorMsg("审核失败，请稍后重试!");
    }


    /**
     * 条件查询法律条文信息
     *
     * @param page
     * @param recPerPage
     * @param sortBy
     * @param order
     * @param search
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchlawentry")
    public JsonResult searchlawentry(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int recPerPage,
                                     @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
                                     @RequestParam(name = "order", defaultValue = "") String order,
                                     @RequestParam(name = "search", defaultValue = "") String search) {
        LawValidatingView_SS searchObj = null;
        if (StringUtils.isNotBlank(search)) {
            try {
                Gson gson = new Gson();
                searchObj = gson.fromJson(search, LawValidatingView_SS.class);
            } catch (JsonSyntaxException e) {
                searchObj = new LawValidatingView_SS(
                        null,
                        "",
                        "",
                        ""
                );
            }
        } else {
            searchObj = new LawValidatingView_SS(
                    null,
                    "",
                    "",
                    ""
            );
        }
        PageInfo<SearchLawView> lawValidatingViewPageInfo =
                lawService.queryValidatedLaw(
                        page,
                        recPerPage,
                        sortBy,
                        order,
                        searchObj.getInputer(),
                        searchObj.getChecker(),
                        searchObj.getLawname(),
                        searchObj.getAttribute()
                );
        if (lawValidatingViewPageInfo != null && lawValidatingViewPageInfo.getList().size() > 0) {
            return PageDataConvertTool.convert(
                    lawValidatingViewPageInfo,
                    Constrants.SUCCESS,
                    Constrants.SUCCESS_MESSAGE);
        }
        return PageDataConvertTool.convert(
                lawValidatingViewPageInfo,
                Constrants.ERROR,
                Constrants.NOT_DATA);

    }

}
