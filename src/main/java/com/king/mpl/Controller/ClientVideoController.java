package com.king.mpl.Controller;

import com.king.mpl.Bean.Video;
import com.king.mpl.Service.ClientVideoService;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName ClientVideoController
 * @Description TODO
 * @Author mai
 * @Date 2019/12/2 16:17
 **/
@RestController
@RequestMapping("/client/video")
public class ClientVideoController {
    @Autowired
    private ClientVideoService clientVideoService;

    /**
     * 初始化视频模块
     *
     * @param type 视频类型
     * @return 视频列表
     */
    @PostMapping("/index")
    public List<Video> videoPassList(@RequestParam("type") String type) {
        return clientVideoService.videoPassList(type);
    }

    /**
     * 增加视频的观看数量
     *
     * @param id 视频id
     * @return 增加结果
     */
    @PostMapping("/updateWatch")
    public ResultVO UpdateWatch(@RequestParam("id") int id) {
        return clientVideoService.UpdateWatch(id);
    }

    /**
     * 获得视频的观看数量
     * @param id 视频id
     * @return 更新后的观看数量
     */
    @PostMapping("/getWatch")
    public ResultVO getWatch(@RequestParam("id") int id) {
        return clientVideoService.getWatch(id);
    }

    /**
     * 获得视频的回复和评论
     * @param vid 视频的id，别的表的外键
     * @return 评论和回复列表
     */
    @PostMapping("/getCommentAndReply")
    public ResultVO getCommentAndReply(@RequestParam("vid") int vid) {
        return clientVideoService.getCommentAndReply(vid);
    }

    /**
     * 添加评论
     * @param avatar 评论者头像
     * @param commentator 评论者
     * @param content 评论内容
     * @param vid 视频id，别的表的外键
     * @return 评论结果
     */
    @PostMapping("/addComment")
    public ResultVO addComment(@RequestParam("avatar") String avatar, @RequestParam("commentator") String commentator,
                               @RequestParam("content") String content, @RequestParam("vid") int vid) {
        return clientVideoService.addComment(avatar, commentator, content, vid);
    }

    /**
     * 添加回复
     * @param reply 回复者
     * @param receiver 接收者
     * @param content 回复内容
     * @param cid 评论的id
     * @param vid 视频的id
     * @return 回复结果
     */
    @PostMapping("/addReply")
    public ResultVO addReply(@RequestParam("reply") String reply, @RequestParam("receiver") String receiver,
                             @RequestParam("content") String content, @RequestParam("cid") int cid, @RequestParam("vid") int vid) {
        return clientVideoService.addReply(reply, receiver, content, cid, vid);
    }

    /**
     * 上传视频的信息
     * @param title 标题
     * @param type 类型
     * @param level 等级
     * @param file 图片文件
     * @param authorization token
     * @return 上传视频结果
     */
    @PostMapping("/videoInfoUpload")
    public ResultVO videoInfoUpload(@RequestParam("title") String title, @RequestParam("type") String type, @RequestParam("level") String level,
                                    @RequestParam("file") MultipartFile file, @RequestHeader("authorization") String authorization) {
        return clientVideoService.videoInfoUpload(title, type, level, file, authorization);
    }

    /**
     * 上传视频
     * @param videoName 视频名字
     * @param id 视频id
     * @param file 视频文件
     * @return 上传视频的结果
     */
    @PostMapping("/videoUpload")
    public ResultVO videoUpload(@RequestParam("videoName") String videoName, @RequestParam("id") int id, @RequestParam("file") MultipartFile file) {
        return clientVideoService.videoUpload(videoName, id, file);
    }

    /**
     * 获得个人视频信息
     * @param authorization token
     * @return 视频列表
     */
    @PostMapping("/person")
    public ResultVO getPersonCourseList(@RequestHeader("authorization") String authorization) {
        return clientVideoService.getPersonCourseList(authorization);
    }

    /**
     * 删除个人视频
     * @param id 视频id
     * @return 删除结果
     */
    @PostMapping("/remove")
    public ResultVO removeVideo(@RequestParam("id") int id) {
        return clientVideoService.removeVideo(id);
    }

    /**
     * 视频审核不通过
     * @param id 视频id
     * @return 审核结果
     */
    @PostMapping("/cancel")
    public ResultVO cancelCheck(@RequestParam("id") int id) {
        return clientVideoService.cancelCheck(id);
    }
}
