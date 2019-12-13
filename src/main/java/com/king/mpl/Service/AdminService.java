package com.king.mpl.Service;

import com.king.mpl.Bean.Discuss;
import com.king.mpl.Mapper.AdminMapper;
import com.king.mpl.Utils.JwtUtils;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author mai
 * @Date 2019/12/8 20:58
 **/
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    public ResultVO adminLogin(String username,String password){
        if (adminMapper.login(username, password)==null){
            return ResultVO.getFailed("账号或密码错误");
        }
        String authorization=jwtUtils.getAdminAuthorization(username, password);
        return ResultVO.getSuccessWithAuthorization("登录成功",authorization);
    }

    /**
     * 请求视频列表
     * @param status 视频状态
     * @return 视频列表
     */
    public ResultVO getVideoList(int status){
        return ResultVO.getSuccessWithData("请求成功",adminMapper.findVideoByStatus(status));
    }

    /**
     * 一次审核一个视频
     * @param status 视频状态
     * @param id 视频id
     * @return  审核结果
     */
    @Transactional
    public ResultVO videoCheckOne(int status,int id){
        if (status==1){
            adminMapper.updateVideoStatusById(status,id);
        }else{
            adminMapper.deleteVideoById(id);
            adminMapper.removeVideoCommentByVid(id);
            adminMapper.removeVideoReplyByVid(id);
        }
        return ResultVO.getSuccess("视频审核操作成功");
    }

    /**
     * 一次审核一个视频
     * @param status 视频状态
     * @param idStr 视频id字符串
     * @return 审核结果
     */
    @Transactional
    public ResultVO videoCheckMany(int status,String idStr){
        //"." 、"\"、“|”是特殊字符，需要转义，"\\." 、"\\\"、“\\|”
        String[] idArray=idStr.split("\\|");
        if (status==1){
            for (String s : idArray) {
                adminMapper.updateVideoStatusById(status, Integer.parseInt(s));
            }
        }else{
            for (String s : idArray) {
                adminMapper.deleteVideoById(Integer.parseInt(s));
                adminMapper.removeVideoCommentByVid(Integer.parseInt(s));
                adminMapper.removeVideoReplyByVid(Integer.parseInt(s));
            }
        }

        return ResultVO.getSuccess("视频批量审核操作成功");
    }

    /**
     * 一次删除一个视频
     * @param id 视频id
     * @return 删除结果
     */
    @Transactional
    public ResultVO videoDeleteOne(int id){
        adminMapper.deleteVideoById(id);
        adminMapper.removeVideoCommentByVid(id);
        adminMapper.removeVideoReplyByVid(id);

        return ResultVO.getSuccess("视频删除操作成功");
    }

    /**
     * 一次删除多个视频
     * @param idStr id字符串
     * @return 删除结果
     */
    @Transactional
    public ResultVO videoDeleteMany(String idStr){
        //"." 、"\"、“|”是特殊字符，需要转义，"\\." 、"\\\"、“\\|”
        String[] idArray=idStr.split("\\|");
        for (String s : idArray) {
            adminMapper.deleteVideoById(Integer.parseInt(s));
            adminMapper.removeVideoCommentByVid(Integer.parseInt(s));
            adminMapper.removeVideoReplyByVid(Integer.parseInt(s));
        }
        return ResultVO.getSuccess("视频批量删除操作成功");
    }

    /**
     * 请求讨论列表
     * @return 讨论列表
     */
    public ResultVO getDiscussList(){
        List<Discuss> discussList=adminMapper.findAllDiscuss();
        return ResultVO.getSuccessWithData("请求成功",discussList);
    }

    /**
     * 一次删除一个讨论
     * @param id discuss的id
     * @return 删除结果
     */
    @Transactional
    public ResultVO discussDeleteOne(int id){
        adminMapper.deleteDiscussById(id);
        adminMapper.deleteDiscussCommentByDid(id);
        adminMapper.deleteDiscussReplyByDid(id);
        adminMapper.deleteDiscussImgByDid(id);
        return ResultVO.getSuccess("删除发布内容成功");
    }

    /**
     * 一次删除多个讨论
     * @param idStr id字符串
     * @return 删除结果
     */
    @Transactional
    public ResultVO discussDeleteMany(String idStr){
        String[] idArray=idStr.split("\\|");
        for (String s : idArray) {
            adminMapper.deleteDiscussById(Integer.parseInt(s));
            adminMapper.deleteDiscussCommentByDid(Integer.parseInt(s));
            adminMapper.deleteDiscussReplyByDid(Integer.parseInt(s));
            adminMapper.deleteDiscussImgByDid(Integer.parseInt(s));
        }
        return ResultVO.getSuccess("批量删除发布内容成功");
    }
}