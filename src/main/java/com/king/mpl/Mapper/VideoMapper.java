package com.king.mpl.Mapper;

import com.king.mpl.Bean.Video;
import com.king.mpl.Bean.VideoComment;
import com.king.mpl.Bean.VideoReply;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface VideoMapper {
    /**
     * 根据type查询视频列表
     * @param type 视频类型
     * @return List<Video>
     */
    @Select("select id,title,type,level,watch,img_url as imgUrl,video_url as videoUrl from video where type=#{type} and status=1")
    List<Video> videoPassList(@Param("type") String type);

    /**
     * 上传视频信息
     * @param video 视频类
     * @return boolean
     */
    @Insert("insert into video(title,type,level,watch,img_url,video_url,openid,status) values(#{title},#{type},#{level},#{watch},#{imgUrl},#{videoUrl},#{openid},#{status})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    boolean videoInfoUpload(Video video);

    /**
     * 更新视频名字
     * @param videoUrl 视频名字
     * @param id 视频id
     */
    @Update("update video set video_url=#{video_url} where id=#{id}")
    void updateVideoUrl(@Param("video_url") String videoUrl,@Param("id") int id);

    /**
     * 根据id删除视频
     * @param id 视频id
     */
    @Delete("delete from video where id=#{id}")
    void removeVideoById(@Param("id") int id);

    /**
     * 根据id更新视频观看数
     * @param id 视频id
     * @return boolean
     */
    @Update("update video set watch=watch+1 where id=#{id}")
    boolean UpdateWatch(int id);

    /**
     * 根据openid查询个人审核通过的视频
     * @param openid 小程序用户唯一标识
     * @return List<Video>
     */
    @Select("select id,title,type,level,watch,img_url,video_url,status from video where openid=#{openid} and status=1")
    List<Video> findPersonPassCourseListByOpenid(@Param("openid") String openid);

    /**
     * 根据openid查询个人正在审核视频
     * @param openid 小程序唯一标识
     * @return List<Video>
     */
    @Select("select id,title,type,level,watch,img_url,video_url,status from video where openid=#{openid} and status=0")
    List<Video> findPersonCheckCourseListByOpenid(@Param("openid") String openid);

    /**
     * 根据id查询观看次数
     * @param id 视频id
     * @return int
     */
    @Select("select watch from video where id=#{id}")
    int getWatch(int id);

    /**
     * 根据vid查询视频的评论
     * @param vid 视频id
     * @return List<VideoComment>
     */
    @Select("select * from video_comment where vid=#{vid}")
    List<VideoComment> getComment(@Param("vid") int vid);

    /**
     * 根据vid删除视频评论
     * @param vid 视频id
     */
    @Delete("delete from video_comment where vid=#{vid}")
    void removeVideoCommentByVid(@Param("vid") int vid);

    /**
     * 根据vid获得视频的回复
     * @param vid 视频id
     * @return List<VideoReply>
     */
    @Select("select * from video_reply where vid=#{vid}")
    List<VideoReply> getReply(@Param("vid") int vid);

    /**
     * 根据vid删除视频回复
     * @param vid 视频id
     */
    @Delete("delete from video_reply where vid=#{vid}")
    void removeVideoReplyByVid(@Param("vid") int vid);

    /**
     * 增加评论
     * @param avatar 头像url
     * @param commentator 评论人
     * @param content 评论内容
     * @param time 评论时间
     * @param vid 视频id
     * @return boolean
     */
    @Insert("insert into video_comment(avatar,commentator,content,time,vid) values(#{avatar},#{commentator},#{content},#{time},#{vid})")
    boolean addComment(@Param("avatar") String avatar,@Param("commentator") String commentator,
               @Param("content") String content,@Param("time") String time,@Param("vid") int vid);

    /**
     * 增加回复
     * @param reply 回复人
     * @param receiver 接收人
     * @param content 内容
     * @param cid 评论id
     * @param vid 视频id
     * @return boolean
     */
    @Insert("insert into video_reply(reply,receiver,content,cid,vid) values(#{reply},#{receiver},#{content},#{cid},#{vid})")
    boolean addReply(@Param("reply") String reply,@Param("receiver") String receiver,
                     @Param("content") String content,@Param("cid") int cid,@Param("vid") int vid);
}
