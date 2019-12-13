package com.king.mpl.Service;

import com.auth0.jwt.JWT;
import com.king.mpl.Bean.Video;
import com.king.mpl.Bean.VideoComment;
import com.king.mpl.Bean.VideoReply;
import com.king.mpl.Mapper.VideoMapper;
import com.king.mpl.Utils.FileUtils;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ClientVideoService
 * @Description TODO
 * @Author mai
 * @Date 2019/12/2 16:21
 **/
@Service
public class ClientVideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Value("${file.videoPath}")
    private String videoPath;
    @Value("${file.imgPath}")
    private String imgPath;

    /**
     * 初始化视频模块
     *
     * @param type 视频类型
     * @return 视频列表
     */
    public List<Video> videoPassList(String type) {
        return videoMapper.videoPassList(type);
    }

    /**
     * 增加视频的观看数量
     *
     * @param id 视频id
     * @return 增加结果
     */
    @Transactional
    public ResultVO UpdateWatch(int id) {
        if (videoMapper.UpdateWatch(id)) {
            return ResultVO.getSuccess("修改成功");
        } else {
            return ResultVO.getFailed("修改失败");
        }
    }

    /**
     * 获得视频的观看数量
     *
     * @param id 视频id
     * @return 更新后的观看数量
     */
    public ResultVO getWatch(int id) {
        return ResultVO.getSuccessWithData("请求成功", videoMapper.getWatch(id));
    }

    /**
     * 获得视频的回复和评论
     *
     * @param vid 视频的id，别的表的外键
     * @return 评论和回复列表
     */
    public ResultVO getCommentAndReply(int vid) {
        List<VideoComment> commentList = videoMapper.getComment(vid);
        List<VideoReply> replyList = videoMapper.getReply(vid);
        Map<String, Object> map = new HashMap<>();
        map.put("commentList", commentList);
        map.put("replyList", replyList);
        return ResultVO.getSuccessWithData("请求成功", map);
    }

    /**
     * 添加评论
     *
     * @param avatar      评论者头像
     * @param commentator 评论者
     * @param content     评论内容
     * @param vid         视频id，别的表的外键
     * @return 评论结果
     */
    public ResultVO addComment(String avatar, String commentator, String content, int vid) {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        boolean flag = videoMapper.addComment(avatar, commentator, content, time, vid);
        if (flag) {
            return ResultVO.getSuccess("评论成功");
        } else {
            return ResultVO.getFailed("评论失败");
        }
    }

    /**
     * 添加回复
     *
     * @param reply    回复者
     * @param receiver 接收者
     * @param content  回复内容
     * @param cid      评论的id
     * @param vid      视频的id
     * @return 回复结果
     */
    public ResultVO addReply(String reply, String receiver, String content, int cid, int vid) {
        boolean flag = videoMapper.addReply(reply, receiver, content, cid, vid);
        if (flag) {
            return ResultVO.getSuccess("回复成功");
        } else {
            return ResultVO.getFailed("回复失败");
        }
    }

    /**
     * 上传视频的信息
     *
     * @param title         标题
     * @param type          类型
     * @param level         等级
     * @param file          图片文件
     * @param authorization token
     * @return 上传视频结果
     */
    @Transactional
    public ResultVO videoInfoUpload(String title, String type, String level, MultipartFile file, String authorization) {
        String openid = JWT.decode(authorization).getClaim("openid").asString();
        String imgName = FileUtils.getRandomName(32);
        String imgUrl = FileUtils.saveFile(imgName, imgPath, file);
        Video video = new Video(0, title, type, level, 0, imgUrl, "", openid, 0);
        boolean flag = videoMapper.videoInfoUpload(video);
        if (flag) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", video.getId());
            //将照片的名字同样作为视频的名字
            map.put("videoName", imgName);
            return ResultVO.getSuccessWithData("上传视频信息成功", map);
        } else {
            return ResultVO.getFailed("上传视频信息成功");
        }
    }

    /**
     * 上传视频
     *
     * @param videoName 视频名字
     * @param id        视频id
     * @param file      视频文件
     * @return 上传视频的结果
     */
    @Transactional
    public ResultVO videoUpload(String videoName, int id, MultipartFile file) {
        String videoUrl = FileUtils.saveFile(videoName, videoPath, file);
        //保存图片名字到数据库
        videoMapper.updateVideoUrl(videoUrl, id);
        return ResultVO.getSuccess("上传图片成功");
    }

    /**
     * 获得个人视频信息
     *
     * @param authorization token
     * @return 视频列表
     */
    public ResultVO getPersonCourseList(String authorization) {
        String openid = JWT.decode(authorization).getClaim("openid").asString();
        List<Video> videoPassList = videoMapper.findPersonPassCourseListByOpenid(openid);
        List<Video> videoCheckList = videoMapper.findPersonCheckCourseListByOpenid(openid);
        Map<String, Object> map = new HashMap<>();
        map.put("videoPassList", videoPassList);
        map.put("videoCheckList", videoCheckList);
        return ResultVO.getSuccessWithData("请求成功", map);
    }

    /**
     * 删除个人视频
     *
     * @param id 视频id
     * @return 删除结果
     */
    @Transactional
    public ResultVO removeVideo(int id) {
        videoMapper.removeVideoById(id);
        videoMapper.removeVideoCommentByVid(id);
        videoMapper.removeVideoReplyByVid(id);
        return ResultVO.getSuccess("删除成功");
    }

    /**
     * 视频审核不通过
     *
     * @param id 视频id
     * @return 审核结果
     */
    public ResultVO cancelCheck(int id) {
        videoMapper.removeVideoById(id);
        return ResultVO.getSuccess("取消视频审核成功");
    }
}
