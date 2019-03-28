package com.justcs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.justcs.dao.*;
import com.justcs.entity.*;
import com.justcs.form.CheckLawSubForm;
import com.justcs.form.LawSubmitForm;
import com.justcs.service.ILawService;
import com.justcs.utils.Constrants;
import com.justcs.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("lawService")
public class LawService implements ILawService {

    @Resource
    private ReleasedepMapper releasedepMapper;

    @Resource
    private SecuritylawMapper securitylawMapper;

    @Resource
    private LawentitytempMapper lawentitytempMapper;

    @Resource
    private BodyofentryMapper bodyofentryMapper;

    @Resource
    private WorkloadrecordMapper workloadrecordMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private LawattrlevelMapper lawattrlevelMapper;


    /**
     * 查询所有发布部门
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Releasedep> queryAllReleaseDep() {
        return releasedepMapper.selectAllDep();
    }


    /**
     * 查询发布部门
     *
     * @param depname
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Releasedep queryReleaseDep(String depname) {
        if (StringUtils.isNotBlank(depname)) {
            Releasedep releasedep = releasedepMapper.selectDepByName(depname);
            if (releasedep == null) {
                // 录入新的部门
                Releasedep newrecord = new Releasedep();
                newrecord.setDepname(depname);
                releasedepMapper.insertSelective(newrecord);
                return newrecord;
            }
            return releasedep;
        }
        return null;
    }


    /**
     * 录入法律条文服务（保存/暂存）
     *
     * @param securitylaw
     * @param savetype
     * @param account
     * @param saveTemp
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addLawRecordService(SecuritylawWithBLOBs securitylaw,
                                       Short savetype,
                                       Account account,
                                       Lawentitytemp saveTemp) {
        if (securitylaw != null) {
            try {
                // 查询法律条文服务（查看是否具有相同的法律名称和版本）
                Securitylaw slawrecord = securitylawMapper.selectContainsLaw(securitylaw);
                // 如果没有录入
                if (slawrecord == null) {

                    // 判断操作的类型
                    if (savetype == Constrants.FINAL_SAVE) { // CASE1:如果是录入(并且没有记录的情况下)

                        // 设置保存状态(录入完成)
                        securitylaw.setLawstatus(Constrants.LAW_COMPLETE);

                    } else if (savetype == Constrants.TEMP_SAVE) { // CASE2:如果是暂时保存(没有记录的情况下)

                        // 设置保存状态(文档正在输入)
                        securitylaw.setLawstatus(Constrants.LAW_INPUTING);
                    }

                    // 录入到法律条文库中
                    securitylawMapper.insertSelective(securitylaw);
                    // 录入正文暂存表中
                    saveTemp.setLawid(securitylaw.getId()); // 法律编号
                    lawentitytempMapper.insertSelective(saveTemp);

                    if (savetype == Constrants.FINAL_SAVE) {

                        ////////////////////录入工作量////////////////////
                        Workloadrecord workloadrecord = new Workloadrecord();
                        workloadrecord.setAccountid(account.getId());
                        workloadrecord.setLawid(securitylaw.getId());
                        workloadrecord.setOptime(new Date(System.currentTimeMillis()));
                        workloadrecord.setType(Constrants.WORK_LURU);
                        workloadrecordMapper.insertSelective(workloadrecord);
                    }
                    return true;
                } else {
                    // 正在输入,当前法律条文为本人操作
                    if ((slawrecord.getLawstatus().equals(Constrants.LAW_INPUTING))
                            && (slawrecord.getEnterid().equals(account.getId()))) {

                        // 设置法律的编号
                        securitylaw.setId(slawrecord.getId());
                        // 设置法律条文的状态
                        securitylaw.setLawstatus(slawrecord.getLawstatus());

                        // 设置法律正文内容的编号
                        Lawentitytemp _lacontenttemp = lawentitytempMapper.selectByLawId(String.valueOf(slawrecord.getId()));
                        saveTemp.setId(_lacontenttemp.getId());
                        saveTemp.setLawid(slawrecord.getId());
                        // CASE3:如果数据库已经有暂存，进行录入操作
                        if (savetype == Constrants.FINAL_SAVE) {
                            // 标记已经完成
                            securitylaw.setLawstatus(Constrants.LAW_COMPLETE);

                            // 修改法律项目表，正文临时表
                            securitylawMapper.updateByPrimaryKeyWithBLOBs(securitylaw);
                            lawentitytempMapper.updateByPrimaryKeyWithBLOBs(saveTemp);

                            ////////////////////录入工作量////////////////////
                            Workloadrecord workloadrecord = new Workloadrecord();
                            workloadrecord.setAccountid(account.getId());
                            workloadrecord.setLawid(securitylaw.getId());
                            workloadrecord.setOptime(new Date(System.currentTimeMillis()));
                            workloadrecord.setType(Constrants.WORK_LURU);
                            workloadrecordMapper.insertSelective(workloadrecord);
                            return true;
                        } else if (savetype == Constrants.TEMP_SAVE) { // CASE4:如果数据库已经暂存，进行暂存【修改操作】
                            // 修改法律项目表，正文临时表
                            securitylawMapper.updateByPrimaryKeyWithBLOBs(securitylaw);
                            lawentitytempMapper.updateByPrimaryKeyWithBLOBs(saveTemp);
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

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
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageInfo<InputLawView> queryPagedInpting(int page,
                                                    int recPerPage,
                                                    String enterid,
                                                    Short lawstatus,
                                                    String sortby,
                                                    String order,
                                                    String search) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<InputLawView> inputLawViewPageInfo = new PageInfo<>(
                lawentitytempMapper.selectInputingLaw(
                        enterid,
                        String.valueOf(lawstatus),
                        sortby,
                        order,
                        search
                )
        );
        return inputLawViewPageInfo;
    }

    /**
     * 删除某个法律条文
     *
     * @param lawid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delInputLaw(int lawid) {
        int laws[] = {lawid};
        return batchDelInputLaw(laws);
    }

    /**
     * 批量删除某个法律条文
     *
     * @param laws
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean batchDelInputLaw(int[] laws) {
        if (laws != null && laws.length > 0) {
            int rows = lawentitytempMapper.batchDelLaws(laws);
            return rows >= laws.length;
        }
        return false;
    }

    /**
     * 通过法律的编号查询法律的具体的信息
     *
     * @param lawid
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public LawSubmitForm queryLawDetail(int lawid) {
        LawSubmitForm lawSubmitForm = null;
        // 查询法律详情
        SecuritylawWithBLOBs securitylaw = securitylawMapper.selectByPrimaryKey(lawid);
        // 通过法律的编号查询法律正文的内容
        Lawentitytemp lawentitytemp = lawentitytempMapper.selectByLawId(String.valueOf(lawid));
        if (securitylaw != null && lawentitytemp != null) {
            lawSubmitForm = new LawSubmitForm();
            // 法律项目部分
            lawSubmitForm.setLawid(lawid);
            lawSubmitForm.setLawname(securitylaw.getLawname());
            lawSubmitForm.setVersion(securitylaw.getVersion());
            lawSubmitForm.setFirstreleasedep(String.valueOf(securitylaw.getFirstreldepid()));
            lawSubmitForm.setCopubdep(String.valueOf(securitylaw.getCopubdepid()));
            lawSubmitForm.setKeyterms(securitylaw.getKeyterms());
            lawSubmitForm.setLawattributes(securitylaw.getLawattributes());
            lawSubmitForm.setUpperregulations(securitylaw.getUpperregulations());
            // 录入人部分
            lawSubmitForm.setEnterid(String.valueOf(securitylaw.getEnterid()));
            lawSubmitForm.setEntertime(String.valueOf(securitylaw.getEntertime()));
            // 校验人部分
            lawSubmitForm.setCheckid(String.valueOf(securitylaw.getCheckerid()));
            // 法律条文的状态
            lawSubmitForm.setLawstatus(String.valueOf(securitylaw.getLawstatus()));
            // 法律正文部分
            lawSubmitForm.setLawcontentid(lawentitytemp.getId());
            lawSubmitForm.setMaincontent(lawentitytemp.getLawcontent());
        }
        return lawSubmitForm;
    }

    /**
     * 查询待校验的记录
     *
     * @param page
     * @param recPerPage
     * @param sortby
     * @param order
     * @param search
     * @return
     * @Param accountid
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageInfo<ValidateLawView> queryStuValiInfoView(
            int page,
            int recPerPage,
            String sortby,
            String order,
            String accountid,
            String search) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<ValidateLawView> validateLawViewPageInfo = new PageInfo<ValidateLawView>(
                lawentitytempMapper.selectValidatingLaw(
                        accountid,
                        sortby,
                        order,
                        search
                )
        );
        return validateLawViewPageInfo;
    }


    /**
     * 开始验证
     *
     * @param accountid
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean startValidate(int lawid, int accountid) {
        SecuritylawWithBLOBs securitylaw = securitylawMapper.selectByPrimaryKey(lawid);
        if (securitylaw != null) {
            // 判断是否已经有人开始验证
            if (securitylaw.getLawstatus() == Constrants.LAW_LOCKED
                    && securitylaw.getCheckerid() != accountid) {
                return false;
            }
            securitylaw.setLawstatus(Constrants.LAW_LOCKED);
            securitylaw.setCheckerid(accountid);
            return securitylawMapper.updateByPrimaryKeySelective(securitylaw) > 0;
        }
        return false;
    }


    /**
     * 执行校验通过操作
     *
     * @param securitylaw
     * @param lawentitytemp
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean validaeLaws(SecuritylawWithBLOBs securitylaw, Lawentitytemp lawentitytemp) {
        if (securitylaw != null && lawentitytemp != null) {
            securitylaw.setLawstatus(Constrants.LAW_CHECKED);
            int affectedrows = securitylawMapper.updateByPrimaryKeySelective(securitylaw);
            if (affectedrows > 0) {
                // 分段存储法律正文
                String content = lawentitytemp.getLawcontent();
                String pattern = "第[零一二三四五六七八九十百千]+条\\s{1,}";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                // 现在创建 matcher 对象
                Matcher m = r.matcher(content);
                // 创建条目
                List<String> tiaomu = new ArrayList<>();
                while (m.find()) {
                    tiaomu.add(m.group());
                }
                // 切割字符串
                String[] results = content.split(pattern);
                // 分条录入系统中
                if (results != null) {
                    int arrayLen = results.length;
                    int lawid = securitylaw.getId();
                    int count = 0;
                    List<Bodyofentry> bodyofentries = new ArrayList<>();
                    for (int i = 1; i < arrayLen; i++) {
                        count++;
                        Bodyofentry bodyofentry = new Bodyofentry();
                        bodyofentry.setNumber(count);
                        bodyofentry.setLawid(lawid);
                        bodyofentry.setLawcontent(tiaomu.get(i - 1) + results[i]);
                        bodyofentries.add(bodyofentry);
                    }
                    // 批量录入数据库中
                    int affected = bodyofentryMapper.batchInsertLawEntity(bodyofentries);
                    /////////////////// 这里录入工作量 //////////////////
                    Workloadrecord workloadrecord = new Workloadrecord();
                    workloadrecord.setAccountid(securitylaw.getCheckerid());
                    workloadrecord.setLawid(securitylaw.getId());
                    workloadrecord.setOptime(new Date(System.currentTimeMillis()));
                    workloadrecord.setType(Constrants.WORK_CHECK);
                    workloadrecordMapper.insertSelective(workloadrecord);
                    return affected >= count;
                }

            }
        }
        return false;
    }


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
    @Override
    public PageInfo<WorkedLawView> workloadRecordsPageInfo(
            int page,
            int recPerPage,
            String accountid,
            String search,
            String sortby,
            String order,
            String starttime,
            String endtime) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<WorkedLawView> workedLawViewPageInfo =
                new PageInfo<>(workloadrecordMapper.selectWorkedLaws(
                        accountid,
                        search,
                        sortby,
                        order,
                        starttime,
                        endtime
                ));
        return workedLawViewPageInfo;
    }

    /**
     * 分页查询待审核的法律的信息
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
    @Override
    public PageInfo<LawValidatingView> queryLawvalidating(int page,
                                                          int recPerPage,
                                                          String lawstatus,
                                                          String starttime,
                                                          String endtime,
                                                          String sortby,
                                                          String order,
                                                          String search) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<LawValidatingView> lawValidatingViewPageInfo =
                new PageInfo<>(
                        securitylawMapper.selectValidating(
                                lawstatus,
                                starttime,
                                endtime,
                                sortby,
                                order,
                                search
                        )
                );
        return lawValidatingViewPageInfo;
    }


    /**
     * 检索数据
     *
     * @param page
     * @param recPerPage
     * @param sortby
     * @param order
     * @param inputer
     * @param checker
     * @param lawname
     * @param attribute
     * @return
     */
    @Override
    public PageInfo<LawValidatingView> queryValidatedLaw(
            int page,
            int recPerPage,
            String sortby,
            String order,
            String inputer,
            String checker,
            String lawname,
            String attribute) {
        PageHelper.startPage(page, recPerPage);
        PageInfo<LawValidatingView> lawValidatingViewPageInfo =
                new PageInfo<>(
                        securitylawMapper.selectValidated(
                                sortby,
                                order,
                                inputer,
                                checker,
                                lawname,
                                attribute
                        )
                );
        return lawValidatingViewPageInfo;
    }


    /**
     * 查询待校验的信息实体
     *
     * @param lawid
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CheckLawEntryView queryCheckLawDetail(int lawid) {
        CheckLawEntryView checkLawEntryView = new CheckLawEntryView();
        try {
            // 查询法律主体信息
            SecuritylawWithBLOBs securitylaw = securitylawMapper.selectByPrimaryKey(lawid);
            checkLawEntryView.setSecuritylaw(securitylaw);
            // 获取第一发布部门和第二发布部门
            Releasedep releasedep = releasedepMapper.selectByPrimaryKey(securitylaw.getFirstreldepid());
            Releasedep coreleasedep = releasedepMapper.selectByPrimaryKey(securitylaw.getCopubdepid());
            checkLawEntryView.setFirstdep(releasedep.getDepname());
            checkLawEntryView.setCodep(releasedep.getDepname());

            // 录入人信息
            Student enter = studentMapper.selectByAccountId(securitylaw.getEnterid());
            checkLawEntryView.setEntername(enter.getStuname());
            // 获取暂存的法律主体内容信息
            Lawentitytemp lawentitytemp = lawentitytempMapper.selectByLawId(String.valueOf(lawid));
            checkLawEntryView.setLawentitytemp(lawentitytemp);

            if (securitylaw.getLawstatus() != null) {
                int status = securitylaw.getLawstatus();
                if (status == Constrants.LAW_CHECKED || status == Constrants.LAW_PASSED) {
                    // 校验人信息
                    Student checker = studentMapper.selectByAccountId(securitylaw.getCheckerid());
                    checkLawEntryView.setCheckername(checker.getStuname());
                    // 获取校验过的法律条文列表
                    List<Bodyofentry> bodyofentries = bodyofentryMapper.selectLawItems(String.valueOf(lawid));
                    checkLawEntryView.setBodyofentryList(bodyofentries);
                } else {
                    checkLawEntryView.setCheckername("等待校验人校验");
                    checkLawEntryView.setBodyofentryList(new ArrayList<Bodyofentry>());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkLawEntryView;
    }


    /**
     * 审核法律
     *
     * @param checkLawSubForm
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean checkLaw(CheckLawSubForm checkLawSubForm, Account account, Short result) {
        try {
            // 查询法律主体信息
            SecuritylawWithBLOBs securitylaw = securitylawMapper.selectByPrimaryKey(Integer.valueOf(checkLawSubForm.getLawid()));
            // 设置录入人员，时间，条文的状态,并保存
            securitylaw.setReviewerid(account.getId());
            securitylaw.setReviewtime(new Date(System.currentTimeMillis()));
            securitylaw.setLawstatus(result);
            securitylawMapper.updateByPrimaryKeySelective(securitylaw);
            // 查询工作量信息
            Workloadrecord enterwork = workloadrecordMapper.selectByLawidAndAcId(
                    checkLawSubForm.getEnterid(),
                    checkLawSubForm.getLawid()
            );
            Workloadrecord checkerwork = workloadrecordMapper.selectByLawidAndAcId(
                    checkLawSubForm.getCheckerid(),
                    checkLawSubForm.getLawid()
            );
            // 修改分数，打分时间，保存
            enterwork.setGrade(Float.valueOf(checkLawSubForm.getEnterscore()));
            enterwork.setGradetime(new Date(System.currentTimeMillis()));
            workloadrecordMapper.updateByPrimaryKeySelective(enterwork);
            checkerwork.setGrade(Float.valueOf(checkLawSubForm.getCheckerscore()));
            checkerwork.setGradetime(new Date(System.currentTimeMillis()));
            workloadrecordMapper.updateByPrimaryKeySelective(checkerwork);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取所有的法律属性(两层)
     *
     * @return
     */
    @Override
    public List<LawAttributeNode> getLawAttributeNodes() {
        List<LawAttributeNode> results = null;
        List<Lawattrlevel> records = lawattrlevelMapper.selectAllAttributes();
        if (records != null) {
            results = new ArrayList<>();
            // 遍历第一层
            for (Lawattrlevel level : records) {
                if (level.getParentNode().equals(-1)) {
                    LawAttributeNode _new = new LawAttributeNode();
                    _new.setId(level.getId());
                    _new.setCurrentName(level.getLevelName());
                    results.add(_new);
                }
            }
            // 遍历第二层
            for (Lawattrlevel level : records) {
                if (level.getLevel().equals(1)) {
                    int parentid = level.getParentNode();
                    // 遍历顶层然后添加到父节点中
                    for (LawAttributeNode e : results) {
                        if (parentid == e.getId()) {
                            if(e.getChildren()==null){
                                e.setChildren(new ArrayList<LawAttributeNode>());
                            }
                            LawAttributeNode _second = new LawAttributeNode();
                            _second.setId(level.getId());
                            _second.setCurrentName(level.getLevelName());
                            e.getChildren().add(_second);
                        }
                    }
                }
            }
        }
        return results;
    }

}
