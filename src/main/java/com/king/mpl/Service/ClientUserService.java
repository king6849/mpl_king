package com.king.mpl.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.king.mpl.Config.RestTemplateConfig;
import com.king.mpl.Entity.User;
import com.king.mpl.Mepper.ClientUserMepper;
import com.king.mpl.Utils.MyToken;
import com.king.mpl.Utils.ResultVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClientUserService {
    @Autowired
    private RestTemplateConfig restTemplateConfig;
    @Autowired
    private MyToken authorization;
    @Autowired
    private ClientUserMepper clientUserMepper;

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
        //生成token
        String authorization1 = authorization.getCustomerToken(openid, session_key);
        String tmp = clientUserMepper.isExitOpenId(openid);
        if (tmp == null) {
            clientUserMepper.insertOpenId(openid);
        }
        //返回结果
        return ResultVO.getSuccessWithAuthorization("登陆成功", authorization1);
    }

    public ResultVO uploadFile(MultipartFile file[], String savePath) {
        int len = file.length;
        for (int i = 0; i < len; i++) {
            MultipartFile f = file[i];
            String fileName = f.getOriginalFilename();
            System.out.println(fileName);
        }
        return ResultVO.getSuccess("上传成功");
    }

    @Transactional
    public ResultVO info(String avatar, String nickname, String token) {
        DecodedJWT jwt = authorization.parseToken(token);
        String openid = jwt.getClaim("openid").asString();
        if (clientUserMepper.isExitOpenId(openid) != null) {
            clientUserMepper.updateInfo(avatar, nickname, openid);
            return ResultVO.getSuccess("更新成功");
        }
        return ResultVO.getFailed("信息有误");
    }
}
