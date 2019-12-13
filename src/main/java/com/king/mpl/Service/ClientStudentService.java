package com.king.mpl.Service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.king.mpl.Bean.*;
import com.king.mpl.Utils.HtmlUnitUtils;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ClientStudentService
 * @Description TODO
 * @Author mai
 * @Date 2019/12/4 14:55
 **/
@Service
public class ClientStudentService {
    @Value("${url.loginUrl}")
    private String loginUrl;//登录路径
    @Value("${url.mainUrl}")
    private String mainUrl;//主页iframe路径
    @Value("${url.homeUrl}")
    private String homeUrl;//大多数网页路径相同的一部分
    @Value("${url.courseUrl}")
    private String courseUrl;//课程信息路径

    /**
     * 检验账号密码是否正确
     *
     * @param username 用户名
     * @param password 密码
     * @return 检验结果
     */
    public ResultVO bindService(String username, String password) {
        //  个人信息登录界面
        WebClient webClient = HtmlUnitUtils.initWebClient(loginUrl, username, password);
        if (webClient == null) {
            return ResultVO.getFailed("账号或密码错误");
        }
        return ResultVO.getSuccess("登录成功");
    }

    /**
     * 获得课程信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 课程信息
     * @throws IOException
     */
    public ResultVO courseService(String username, String password) throws IOException {
        List<Course> courseList = new ArrayList<>();
        //判断是否登录成功
        WebClient webClient = HtmlUnitUtils.initWebClient(loginUrl, username, password);
        if (webClient == null) {
            return ResultVO.getFailed("账号或密码错误");
        }
        webClient.getPage(mainUrl);
        HtmlPage coursePage = webClient.getPage(courseUrl);
        //课表表格体
        HtmlTableBody body = (HtmlTableBody) coursePage.getByXPath("/html/body/form/table[5]/tbody").get(0);
        for (int i = 1; i < body.getRows().size(); i++) {
            HtmlTableRow row = body.getRows().get(i);
            CourseTime courseTime = new CourseTime();
            CourseInfo courseInfo1 = new CourseInfo();
            CourseInfo courseInfo2 = new CourseInfo();
            CourseInfo courseInfo3 = new CourseInfo();
            CourseInfo courseInfo4 = new CourseInfo();
            CourseInfo courseInfo5 = new CourseInfo();
            String time = row.getCells().get(0).asText();
            String info1 = row.getCells().get(1).asText();
            String info2 = row.getCells().get(2).asText();
            String info3 = row.getCells().get(3).asText();
            String info4 = row.getCells().get(4).asText();
            String info5 = row.getCells().get(5).asText();
            Course course = new Course(arrangeCourseTime(courseTime, time), arrangeCourseInfo(courseInfo1, info1), arrangeCourseInfo(courseInfo2, info2),
                    arrangeCourseInfo(courseInfo3, info3), arrangeCourseInfo(courseInfo4, info4), arrangeCourseInfo(courseInfo5, info5));
            courseList.add(course);
        }
        return ResultVO.getSuccessWithData("请求成功", courseList);
    }

    /**
     * 整理课程信息的格式
     *
     * @param courseInfo 课程信息类
     * @param info       课程信息字符串
     * @return courseInfo
     */
    private CourseInfo arrangeCourseInfo(CourseInfo courseInfo, String info) {
        if (!info.equals(" ")) {
            int to1 = info.indexOf("(");
            courseInfo.setName(info.substring(0, to1));
            int to2 = info.indexOf(" ", to1 + 1);
            courseInfo.setClassNo("[" + info.substring(to1 + 1, to2) + "]");
            int to3 = info.indexOf(" ", to2 + 1);
            courseInfo.setTeacher(info.substring(to2 + 1, to3));
            int to4 = info.indexOf("周", to3 + 1);
            courseInfo.setDuration(info.substring(to3 + 1, to4 + 1));
            int to5 = info.indexOf("[", to4 + 1);
            int to6 = info.indexOf("]", to5);
            courseInfo.setClassroom(info.substring(to5 + 1, to6));
        }
        return courseInfo;
    }

    /**
     * 整理时间信息的格式
     *
     * @param courseTime 时间信息类
     * @param time       时间字符串
     * @return courseTime
     */
    private CourseTime arrangeCourseTime(CourseTime courseTime, String time) {
        int to1 = time.indexOf("\r");
        int to2 = time.indexOf("-", to1 + 1);
        courseTime.setStartTime(time.substring(to1 + 2, to2 - 1));
        courseTime.setEndTime(time.substring(to2 + 2));
        return courseTime;
    }

    /**
     * 获得考试信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 考试信息
     * @throws IOException
     * @throws ParseException
     */
    public ResultVO examService(String username, String password) throws IOException, ParseException {
        List<Exam> examList = new ArrayList<>();
        //判断是否登录成功
        WebClient webClient = HtmlUnitUtils.initWebClient(loginUrl, username, password);
        if (webClient == null) {
            return ResultVO.getFailed("账号或密码错误");
        }
        HtmlPage personIndex = webClient.getPage(mainUrl);
        HtmlTableCell td = (HtmlTableCell) personIndex.getByXPath("/html/body/table/tbody/tr[1]/td[3]/table/tbody/tr/td").get(0);
        String onclick = td.getAttribute("onclick");
        int onclickLen = onclick.length();
        int index = lookIndex(onclick, "/", 5);
        String url = onclick.substring(index, onclickLen - 1);
        String examPath = homeUrl + url;
        webClient.getOptions().setJavaScriptEnabled(false); //很重要，启用JS
        //跳转到考试页面
        HtmlPage examPage = webClient.getPage(examPath);
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        HtmlTableBody body = (HtmlTableBody) examPage.getByXPath("//body/form/div/p[2]/table/tbody").get(0);
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");//如2016-08-10 20:40
        String fromDate = simpleFormat.format(new Date());
        long from = simpleFormat.parse(fromDate).getTime();
        for (int i = 0; i < body.getRows().size(); i++) {
            HtmlTableRow row = body.getRows().get(i);
            String toDate = simpleFormat.format(simpleFormat.parse(row.getCells().get(2).asText()));
            long to = simpleFormat.parse(toDate).getTime();
            int dateGap = (int) ((to - from) / (1000 * 60 * 60 * 24));
            Exam exam = new Exam(row.getCells().get(0).asText(), row.getCells().get(1).asText(), row.getCells().get(2).asText(),
                    row.getCells().get(3).asText(), row.getCells().get(4).asText(), row.getCells().get(5).asText(), row.getCells().get(6).asText(),
                    row.getCells().get(7).asText(), dateGap);
            examList.add(exam);
        }
        return ResultVO.getSuccessWithData("请求成功", examList);
    }

    /**
     * 获得成绩信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 成绩信息
     * @throws IOException
     */
    public ResultVO scoreService(String username, String password) throws IOException {
        List<Score> scoreList = new ArrayList<>();
        CreditBasics creditBasics = null;
        WebClient webClient = HtmlUnitUtils.initWebClient(loginUrl, username, password);
        if (webClient == null) {
            return ResultVO.getFailed("账号或密码错误");
        }
        //导航页面
        HtmlPage personIndex = webClient.getPage(mainUrl);
        HtmlTableCell td = (HtmlTableCell) personIndex.getByXPath("/html/body/table/tbody/tr[1]/td[1]/table/tbody/tr/td").get(0);
        String onclick = td.getAttribute("onclick");
        int onclickLen = onclick.length();
        int index = lookIndex(onclick, "/", 5);
        String url = onclick.substring(index, onclickLen - 1);
        String scorePath = homeUrl + url;
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage scorePage = webClient.getPage(scorePath);
        webClient.getOptions().setJavaScriptEnabled(true);
        //必修表格体
        HtmlTableBody body = (HtmlTableBody) scorePage.getByXPath("//body/form/table[6]/tbody").get(0);
        String semesters = "第一学期";
        String semester = "";
        for (int i = 0; i < body.getRows().size(); i++) {
            HtmlTableRow row = body.getRows().get(i);
            String value = row.getCells().get(0).asText();
            if (value.equals("") || value.equals(" ")) {
                semester = semesters;
            } else {
                semesters = value;
                semester = semesters;
            }
            Score score = new Score(semester, row.getCells().get(1).asText(), row.getCells().get(2).asText(), row.getCells().get(3).asText(),
                    row.getCells().get(8).asText());
            scoreList.add(score);
        }
        //选修表格体
        HtmlTableBody body1 = (HtmlTableBody) scorePage.getByXPath("//body/form/table[9]/tbody").get(0);
        for (int i = 0; i < body1.getRows().size(); i++) {
            HtmlTableRow row1 = body1.getRows().get(i);
            Score score = new Score("", row1.getCells().get(0).asText(), row1.getCells().get(1).asText(), row1.getCells().get(2).asText(),
                    row1.getCells().get(7).asText());
            scoreList.add(score);
        }
        //基本信息
        String c1 = ((HtmlFont) scorePage.getByXPath("//body/form/table[10]/tbody/tr[3]/td[2]/div/font").get(0)).asText();
        String c2 = ((HtmlFont) scorePage.getByXPath("//body/form/table[10]/tbody/tr[3]/td[4]/div/font").get(0)).asText();
        String c3 = ((HtmlFont) scorePage.getByXPath("//body/form/table[10]/tbody/tr[4]/td[4]/div/font").get(0)).asText();
        String c4 = ((HtmlFont) scorePage.getByXPath("//body/form/table[10]/tbody/tr[5]/td[4]/div/font").get(0)).asText();
        String c5 = ((HtmlFont) scorePage.getByXPath("//body/form/table[10]/tbody/tr[6]/td[4]/div/font").get(0)).asText();
        String c6 = ((HtmlFont) scorePage.getByXPath("//body/form/table[10]/tbody/tr[7]/td[4]/div/font").get(0)).asText();
        String c7 = ((HtmlDivision) scorePage.getByXPath("//body/form/table[10]/tbody/tr[8]/td[2]/div").get(0)).asText();
        creditBasics = new CreditBasics(c1, c2, c3, c4, c5, c6, c7);
        Map<String, Object> map = new HashMap<>();
        map.put("scoreList", scoreList);
        map.put("creditBasics", creditBasics);
        return ResultVO.getSuccessWithData("请求成功", map);
    }

    /**
     * 获得考勤信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 考勤信息
     * @throws IOException
     */
    public ResultVO attendanceService(String username, String password) throws IOException {
        List<Attendance> attendanceList = new ArrayList<>();
        WebClient webClient = HtmlUnitUtils.initWebClient(loginUrl, username, password);
        if (webClient == null) {
            return ResultVO.getFailed("账号或密码错误");
        }
        HtmlPage personIndex = webClient.getPage(mainUrl);
        HtmlTableCell td = (HtmlTableCell) personIndex.getByXPath("/html/body/table/tbody/tr[1]/td[4]/table/tbody/tr/td").get(0);
        String onclick = td.getAttribute("onclick");
        int onclickLen = onclick.length();
        int index = lookIndex(onclick, "/", 5);
        String url = onclick.substring(index, onclickLen - 1);

        String AttendancePath = "http://class.sise.com.cn:7001" + url;
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage AttendancePage = webClient.getPage(AttendancePath);
        webClient.getOptions().setJavaScriptEnabled(false);
        //必修表格体
        HtmlTableBody body = (HtmlTableBody) AttendancePage.getByXPath("//body/form/div/table[4]/tbody").get(0);
        for (int i = 0; i < body.getRows().size(); i++) {
            HtmlTableRow row = body.getRows().get(i);
            String status = "";
            if (row.getCell(2).asText().equals("") || row.getCell(2).asText().equals(" ")) {
                status = "正常";
            }
            Attendance attendance = new Attendance(row.getCell(1).asText(), status);
            attendanceList.add(attendance);
        }
        return ResultVO.getSuccessWithData("请求成功", attendanceList);
    }

    /**
     * 在一个字符串中查询某个字符第几次出现的位置
     *
     * @param str  被检索的字符串
     * @param mark 检索标识
     * @param num  出现次数
     * @return index 位置
     */
    int lookIndex(String str, String mark, int num) {
        int index = 0;
        for (int i = 0; i < num; i++) {
            index = str.indexOf(mark, index);
        }
        return index;
    }
}

