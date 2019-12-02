package com.king.mpl.Mepper;

import com.king.mpl.Bean.Video;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface VideoMepper {

    //初始添加视频
    @Insert("insert into video(level,type,watch,openid) values(#{level},#{type},0,#{openid})")
    void insertVideo2(@Param("level") String level, @Param("type") String type, @Param("openid") String openid);

    //视频列表
    @Select("select title,level,type,img_url,video_url from video")
    List<Video> VideoList();

    //增加观看人数
    @Update("update video set watch=watch+1 where id=#{id}")
    int UpWatcher(int id);

    //观看次数
    @Select("select watch watch from video where id=#{id}")
    int watchers(int id);
}
