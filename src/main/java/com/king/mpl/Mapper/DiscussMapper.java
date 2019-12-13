package com.king.mpl.Mapper;

import com.king.mpl.Bean.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName DiscussMapper
 * @Description TODO
 * @Author mai
 * @Date 2019/12/3 10:13
 **/

@Mapper
@Component
public interface DiscussMapper {
    /**
     * 查询所有讨论并按照id降序排序
     * @return List<Discuss>
     */
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "discussImgList",column = "id",many = @Many(select = "com.king.mpl.Mapper.DiscussMapper.findAllDiscussImg"))
    })
    @Select("select id,avatar,sender,content,comment_count as commentCount,time from discuss order by id desc")
    List<Discuss> findAllDiscuss();

    /**
     * 根据id查询发布的讨论
     * @param id 讨论id
     * @return Discuss
     */
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(property = "discussImgList",column = "id",many = @Many(select = "com.king.mpl.Mapper.DiscussMapper.findAllDiscussImg"))
    })
    @Select("select id,avatar,sender,content,comment_count as commentCount,time from discuss where id=#{id}")
    Discuss findOneDiscuss(@Param("id") int id);

    /**
     * 根据did查询图片
     * @param did 讨论id
     * @return List<DiscussImg>
     */
    @Select("select id,img_url as imgUrl,did from discuss_img where did=#{did}")
    List<DiscussImg> findAllDiscussImg(@Param("did") int did);

    /**
     * 插入评论信息并给bean中返回插入的id
     * @param discuss 讨论类
     */
    @Insert("insert into discuss(avatar,sender,content,comment_count,time,openid) values(#{avatar},#{sender},#{content},#{commentCount},#{time},#{openid})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void addDiscussInfo(Discuss discuss);

    /**
     * 插入图片
     * @param imgUrl 图片名字
     * @param did 讨论的id
     */
    @Insert("insert into discuss_img(img_url,did) values(#{img_url},#{did})")
    void addDiscussImg(@Param("img_url") String imgUrl,@Param("did") int did);

    /**
     * 根据did查询评论
     * @param did 讨论id
     * @return List<DiscussComment>
     */
    @Select("select * from discuss_comment where did=#{did}")
    List<DiscussComment> getComment(@Param("did") int did);

    /**
     * 根据did查询回复
     * @param did 讨论id
     * @return List<DiscussReply>
     */
    @Select("select * from discuss_reply where did=#{did}")
    List<DiscussReply> getReply(@Param("did") int did);

    /**
     * 增加评论
     * @param avatar 头像url
     * @param commentator 评论人
     * @param content 内容
     * @param time 时间
     * @param did 讨论id
     * @return boolean
     */
    @Insert("insert into discuss_comment(avatar,commentator,content,time,did) values(#{avatar},#{commentator},#{content},#{time},#{did})")
    boolean addComment(@Param("avatar") String avatar,@Param("commentator") String commentator,
                       @Param("content") String content,@Param("time") String time,@Param("did") int did);

    /**
     * 根据id增加评论数量
     * @param did 讨论id
     */
    @Update("update discuss set comment_count=comment_count+1 where id=#{id}")
    void updateCommentCount(@Param("id") int did);

    /**
     * 增加回复
     * @param reply 回复人
     * @param receiver 接收人
     * @param content 内容
     * @param cid 评论id
     * @param did 讨论od
     * @return boolean
     */
    @Insert("insert into discuss_reply(reply,receiver,content,cid,did) values(#{reply},#{receiver},#{content},#{cid},#{did})")
    boolean addReply(@Param("reply") String reply,@Param("receiver") String receiver,
                     @Param("content") String content,@Param("cid") int cid,@Param("did") int did);
}
