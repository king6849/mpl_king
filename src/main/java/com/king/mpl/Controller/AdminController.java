package com.king.mpl.Controller;

import com.king.mpl.Service.AdminService;
import com.king.mpl.Utils.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author mai
 * @Date 2019/12/8 20:56
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResultVO AdminLogin(@RequestParam("username") String username,@RequestParam("password") String password){
        return adminService.adminLogin(username, password);
    }

    /**
     * 请求视频列表
     * @param status 视频状态
     * @return 视频列表
     */
    @PostMapping("/video")
    public ResultVO getVideoList(@Param("status") int status){
        return adminService.getVideoList(status);
    }

    /**
     * 一次审核一个视频
     * @param status 视频状态
     * @param id 视频id
     * @return  审核结果
     */
    @PostMapping("/videoCheckOne")
    public ResultVO videoCheckOne(@RequestParam("status") int status,@RequestParam("id") int id){
        return adminService.videoCheckOne(status, id);
    }

    /**
     * 一次审核一个视频
     * @param status 视频状态
     * @param idStr 视频id字符串
     * @return 审核结果
     */
    @PostMapping("/videoCheckMany")
    public ResultVO videoCheckOne(@RequestParam("status") int status,@RequestParam("idStr") String idStr){
        return adminService.videoCheckMany(status, idStr);
    }

    /**
     * 一次删除一个视频
     * @param id 视频id
     * @return 删除结果
     */
    @PostMapping("/videoDeleteOne")
    public ResultVO videoDeleteOne(@RequestParam("id") int id){
        return adminService.videoDeleteOne(id);
    }

    /**
     * 一次删除多个视频
     * @param idStr id字符串
     * @return 删除结果
     */
    @PostMapping("/videoDeleteMany")
    public ResultVO videoDeleteMany(@RequestParam("idStr") String idStr){
        return adminService.videoDeleteMany(idStr);
    }

    /**
     * 请求讨论列表
     * @return 讨论列表
     */
    @PostMapping("/discuss")
    public ResultVO getDiscussList(){
        return adminService.getDiscussList();
    }

    /**
     * 一次删除一个讨论
     * @param id discuss的id
     * @return 删除结果
     */
    @PostMapping("/discussDeleteOne")
    public ResultVO discussDeleteOne(@RequestParam("id") int id){
        return adminService.discussDeleteOne(id);
    }

    /**
     * 一次删除多个讨论
     * @param idStr id字符串
     * @return 删除结果
     */
    @PostMapping("/discussDeleteMany")
    public ResultVO discussDeleteMany(@RequestParam("idStr") String idStr){
        return adminService.discussDeleteMany(idStr);
    }
}
