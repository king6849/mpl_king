package com.king.mpl.Mepper;

import com.king.mpl.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ClientUserMepper {

    @Insert("insert into user(openid) value(#{openId})")
    int insertOpenId(String openId);

    @Select("select openid from user where openid=#{openid}")
    String isExitOpenId(@Param("openid") String openid);

    @Update("update user set avatar=#{avatar} ,nickname=#{nickname} where openid=#{openid}")
    int updateInfo(@Param("avatar") String avatar, @Param("nickname") String nickname, @Param("openid") String openid);



}
