package com.king.mpl.Mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ClientUserMapper {
    /**
     * 插入openid
     * @param openid 小程序的标识
     */
    @Insert("insert into user(openid) value(#{openid})")
    void insertOpenid(String openid);

    /**
     * 根据openid查找小程序用户
     * @param openid 小程序的标识
     * @return openid
     */
    @Select("select openid from user where openid=#{openid}")
    String isExitOpenid(@Param("openid") String openid);

    /**
     * 根据openid更新小程序的信息
     * @param avatar 头像
     * @param nickname 昵称
     * @param openid 小程序的标识
     */
    @Update("update user set avatar=#{avatar} ,nickname=#{nickname} where openid=#{openid}")
    void updateInfo(@Param("avatar") String avatar, @Param("nickname") String nickname, @Param("openid") String openid);
}
