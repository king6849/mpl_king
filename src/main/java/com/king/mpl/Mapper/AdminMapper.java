package com.king.mpl.Mapper;

import com.king.mpl.Bean.Discuss;
import com.king.mpl.Bean.DiscussImg;
import com.king.mpl.Bean.Video;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AdminMapper {
    /**
     * 查找某个用户名密码是否正确
     * @param username 用户名
     * @param password 密码
     * @return username
     */
    @Select("select username from admin where username=#{username} and password=#{password}")
    String login(@Param("username") String username,@Param("password") String password);

    /**
     * 根据status查询所有的video信息
     * @param status 视频状态
     * @return List<Video>
     */
    @Select("select id,title,type,level,watch,img_url,video_url,status from video where status=#{status}")
    List<Video> findVideoByStatus(@Param("status") int status);

    /**
     * 根据id修改视频状态
     * @param status 视频状态
     * @param id 视频id
     */
    @Update("update video set status=#{status} where id=#{id}")
    void updateVideoStatusById(@Param("status") int status,@Param("id") int id);

    /**
     * 根据id删除视频
     * @param id 视频id
     */
    @Delete("delete from video where id=#{id}")
    void deleteVideoById(@Param("id") int id);

    /**
     * 根据vid删除视频评论
     * @param vid 视频评论的vid
     */
    @Delete("delete from video_comment where vid=#{vid}")
    void removeVideoCommentByVid(@Param("vid") int vid);

    /**
     * 根据vid删除视频回复
     * @param vid 视频回复的vid
     */
    @Delete("delete from video_reply where vid=#{vid}")
    void removeVideoReplyByVid(@Param("vid") int vid);

    /**
     * 一对多查询，查询讨论信息并且更具id查询图片
     * @return
     */
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "discussImgList",column = "id",many = @Many(select = "com.king.mpl.Mapper.AdminMapper.findAllDiscussImg"))
    })
    @Select("select id,avatar,sender,content,comment_count,time from discuss order by id desc")
    List<Discuss> findAllDiscuss();

    /**
     * 根据did查询图片
     * @param did 外键
     * @return 图片列表
     */
    @Select("select id,img_url,did from discuss_img where did=#{did}")
    List<DiscussImg> findAllDiscussImg(@Param("did") int did);

    /**
     * 根据id删除讨论
     * @param id 讨论的id
     */
    @Delete("delete from discuss where id=#{id}")
    void deleteDiscussById(@Param("id") int id);

    /**
     * 根据did删除讨论的评论
     * @param did 外键
     */
    @Delete("delete from discuss_comment where did=#{did}")
    void deleteDiscussCommentByDid(@Param("did") int did);

    /**
     * 根据did删除讨论的回复
     * @param did 外键
     */
    @Delete("delete from discuss_reply where did=#{did}")
    void deleteDiscussReplyByDid(@Param("did") int did);

    /**
     * 根据did删除讨论的图片
     * @param did 外键
     */
    @Delete("delete from discuss_img where did=#{did}")
    void deleteDiscussImgByDid(@Param("did") int did);
}
