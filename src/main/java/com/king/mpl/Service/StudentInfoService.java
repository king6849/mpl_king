package com.king.mpl.Service;

import java.util.*;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(value = "protoType")
public class StudentInfoService {
    //  个人信息登录界面
    private final String loginUrlPath = "http://class.sise.com.cn:7001/sise/";
    //个人信息界面
    private final String InfoIndexPath = "http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp";
    //个人信息主页
    private final String personalIndexPath = "http://class.sise.com.cn:7001/SISEWeb/pub/course/courseViewAction.do?method=doMain&studentid=";
    //    课程表
    private final String SourceUrlPath = "http://class.sise.com.cn:7001/sise/module/student_schedular/student_schedular.jsp";
    //    考试安排
    private final String ExamUrlPath = "http://class.sise.com.cn:7001/SISEWeb/pub/exam/studentexamAction.do?method=doMain&studentid=";

    private WebClient webClient;
    private HtmlPage personIndex;
    private String username;
    private String password;
    private Boolean success = false;
    @Value(value = "")
    public String status;

    public void setLoginInfo(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StudentInfoService() {
        initWebClient();
    }

    private void initWebClient() {
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
    }

    private void managerCookie() {
        Set<Cookie> cookies = new HashSet<Cookie>();
        Iterator<Cookie> i = cookies.iterator();
        while (i.hasNext()) {
            webClient.getCookieManager().addCookie(i.next());
        }
    }

    //个人信息登录界面
    private void loginPage() {
        if (this.username == null || this.username.equals("") || password == null || this.password.equals("")) {
            status = "用户名密码不能为空";
            System.out.println(status);
            return;
        }
        try {
            HtmlPage page = webClient.getPage(loginUrlPath);
            // 学号 username    密码  password   登录  Submit
            HtmlInput username = (HtmlInput) page.getElementsById("username").get(0);
            HtmlPasswordInput password = (HtmlPasswordInput) page.getElementsById("password").get(0);
            HtmlButtonInput submit = (HtmlButtonInput) page.getElementsById("Submit").get(0);
            username.setValueAttribute(this.username);
            password.setValueAttribute(this.password);
            HtmlPage page1 = submit.click();
            // /html/head/title  是否登录成功
            HtmlTitle htmlTitle = (HtmlTitle) page1.getByXPath("/html/head/title ").get(0);
            String loginStatus = htmlTitle.asText();
            if (loginStatus.equals("系统错误提示页面")) {
                status = "用户名密码错误";
                System.out.println(status);
                return;
            }
            managerCookie();
            //正式进入主面板
            success = true;
            personIndex = webClient.getPage(InfoIndexPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //个人信息主页
    // /html/body/table/tbody/tr[1]/td[1]/table/tbody/tr/td
    //<td align="center" width="200" height="20" onclick="showRunningAll();window.location.href='../../../../../SISEWeb/pub/course/courseViewAction.do?method=doMain&amp;studentid=jHsjPBBPQxM='" onmouseover="this.style.cursor='hand'" style="cursor: pointer;">
    //						<img src="/sise/images/2222.gif" width="180" height="100" title="个人信息查询"><br>
    //						<strong>个人信息</strong></td>
    private void personal() {
        if (!success) {
            status = "请先登录";
            return;
        }
        try {
            HtmlTableCell td = (HtmlTableCell) personIndex.getByXPath("/html/body/table/tbody/tr[1]/td[1]/table/tbody/tr/td").get(0);
            String onclick = td.getAttribute("onclick");
            int onclickLen = onclick.length();
            String studentid = onclick.substring(onclickLen - 13, onclickLen - 1);
            String personalUrlPath = personalIndexPath + studentid;
            webClient.getOptions().setJavaScriptEnabled(false);
            // 正式进入个人信息主面板
            HtmlPage page = webClient.getPage(personalUrlPath);
            webClient.getOptions().setJavaScriptEnabled(true);
            // //*[@id="form1"]/table[3]/tbody/tr/td/table/tbody 基本信息
            HtmlTableBody body = (HtmlTableBody) page.getByXPath("//*[@id=\"form1\"]/table[3]/tbody/tr/td/table/tbody").get(0);
//            System.out.println("!!!"+body.asXml());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 课程表
    ///html/body/form/table[5]/tbody
    private void Source() {
        if (!success) {
            status = "请先登录";
            return;
        }
        try {
            HtmlPage page = webClient.getPage(SourceUrlPath);
            //课表表格体
            HtmlTableBody body = (HtmlTableBody) page.getByXPath("/html/body/form/table[5]/tbody").get(0);
            List<HtmlTableRow> tr = body.getRows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    考试安排
    ///html/body/table/tbody/tr[1]/td[3]/table/tbody/tr/td  考试安排唯一标识
    private void Exam() throws IOException {
        if (!success) {
            status = "请先登录";
            return;
        }
        HtmlTableCell td = (HtmlTableCell) personIndex.getByXPath("/html/body/table/tbody/tr[1]/td[3]/table/tbody/tr/td").get(0);
        String onclick = td.getAttribute("onclick");
        int eqIndex = onclick.lastIndexOf("=");
        int onclickLen = onclick.length();
        String studentid = onclick.substring(eqIndex + 1, onclickLen - 1);
        String ExamPath = ExamUrlPath + studentid;
        HtmlPage page = webClient.getPage(ExamPath);
        // //*[@id="form1"]/div/p[2]/table/thead  考试安排表格头
        HtmlTableHeader header = (HtmlTableHeader) page.getByXPath("//*[@id=\"form1\"]/div/p[2]/table/thead").get(0);
        // //*[@id="form1"]/div/p[2]/table/tbody   考试安排表格体
        HtmlTableBody body = (HtmlTableBody) page.getByXPath("//*[@id=\"form1\"]/div/p[2]/table/tbody").get(0);
    }

    public static void main(String[] args) {
        StudentInfoService studentInfoService = new StudentInfoService();
        studentInfoService.setLoginInfo("1740129222", "17440981109034");
        studentInfoService.loginPage();
        studentInfoService.personal();
//        studentInfoService.Source();
    }
}
