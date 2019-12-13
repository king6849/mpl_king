package com.king.mpl.Utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;

/**
 * @ClassName HtmlUnitUtils
 * @Description TODO
 * @Author mai
 * @Date 2019/12/7 20:17
 **/
public class HtmlUnitUtils {
    public static WebClient initWebClient(String loginUrl,String username,String password){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
        try {
            HtmlPage page = webClient.getPage(loginUrl);
            // 学号 username    密码  password   登录  Submit
            HtmlInput usernameHtml = (HtmlInput) page.getElementsById("username").get(0);
            HtmlPasswordInput passwordHtml = (HtmlPasswordInput) page.getElementsById("password").get(0);
            HtmlButtonInput submit = (HtmlButtonInput) page.getElementsById("Submit").get(0);
            usernameHtml.setValueAttribute(username);
            passwordHtml.setValueAttribute(password);
            HtmlPage page1 = submit.click();
            HtmlTitle htmlTitle = (HtmlTitle) page1.getByXPath("/html/head/title ").get(0);
            String loginStatus = htmlTitle.asText();
            if (loginStatus.equals("系统错误提示页面")) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return webClient;
    }
}
