package com.king.mpl.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.king.mpl.Config.RestTemplateConfig;
import com.king.mpl.Mapper.ClientUserMapper;
import com.king.mpl.Utils.JwtUtils;
import com.king.mpl.Utils.ResultVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientUserService {
    @Autowired
    private RestTemplateConfig restTemplateConfig;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ClientUserMapper clientUserMapper;

    /**
     * 小程序端登录
     * @param code 微信小程序端随机生成的code
     * @return authorization
     * @throws RuntimeException 运行时错误
     */
    @Transactional
    public ResultVO login(String code) throws RuntimeException {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String appid = "wx069b7dc66afc066d";
        String secret = "4b80b43c0ea1e3ede324f7f57df22dd6";
        String js_code = code;
        String grant_type = "authorization_code";
        String codereslut = restTemplateConfig.getForObject(url + "?appid=" + appid + "&secret=" + secret + "&js_code=" + js_code + "&grant_type=" + grant_type, String.class);
        JSONObject jsonObject = JSONObject.fromObject(codereslut);
        //解析出session_key，openid
        String session_key = jsonObject.getString("session_key");
        System.out.println("session_key" + session_key);
        String openid = jsonObject.getString("openid");
        //生成authorization
        String authorization = jwtUtils.getClientAuthorization(openid, session_key);
        String tmp = clientUserMapper.isExitOpenid(openid);
        if (tmp == null) {
            clientUserMapper.insertOpenid(openid);
        }
        //返回结果
        return ResultVO.getSuccessWithAuthorization("登陆成功", authorization);
    }

    /**
     * 更新微信小程序端的信息
     * @param avatar 头像链接
     * @param nickname 昵称
     * @param authorization token
     * @return 更新结果
     */
    @Transactional
    public ResultVO info(String avatar, String nickname, String authorization) {
        DecodedJWT jwt = JWT.decode(authorization);
        String openid = jwt.getClaim("openid").asString();
        if (clientUserMapper.isExitOpenid(openid) != null) {
            clientUserMapper.updateInfo(avatar, nickname, openid);
            return ResultVO.getSuccess("更新成功");
        }
        return ResultVO.getFailed("信息有误");
    }
}
