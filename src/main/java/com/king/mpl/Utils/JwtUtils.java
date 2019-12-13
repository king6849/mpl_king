package com.king.mpl.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mai
 */
@Component
public class JwtUtils {

    /**
     * 定义一串密钥用于token加密解密
     */
    private static final String secret = "13124995836mxy";
    /**
     * 签发人
     */
    private static final String issuer = "mai";
    /**
     * 接收人
     */
    private static final String Audience="mai";
    /**
     * token有效时长
     */
    private static final long expTime = 60 * 60 * 2 * 1000;

    /**
     * 设置Authorization  JWT.decode(token).getClaim("sessionKey").asString())
     * @param openid openid
     * @param sessionKey session_key
     * @return authorization
     */
    public String getClientAuthorization(String openid, String sessionKey) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return JWT.create()
                //创建头部
                .withHeader(header)
                //签发人
                .withIssuer(issuer)
                //接收人
                .withAudience(Audience)
                //附带信息
                .withClaim("sessionKey", sessionKey)
                .withClaim("openid",openid)
                //签发时间
                .withIssuedAt(new Date())
                //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
                //构建密钥
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 设置管理后台的Authorization
     * @param username 用户名
     * @param password 密码
     * @return authorization
     */
    public String getAdminAuthorization(String username, String password) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return JWT.create()
                //创建头部
                .withHeader(header)
                //签发人
                .withIssuer(issuer)
                //接收人
                .withAudience(Audience)
                //附带信息
                .withClaim("username", username)
                .withClaim("password",password)
                //签发时间
                .withIssuedAt(new Date())
                //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
                //构建密钥
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 构造验证器
     * @param token 会话密钥
     */
//    public void verityToken(String token) {
//        JWTVerifier jwtVerify = JWT.require(Algorithm.HMAC256(secret)).build();
//        try {
//            //验证
//            jwtVerify.verify(token);
//        } catch (Exception e) {
//            //如果token不正确则抛出异常
//            throw new MyException(405, "token异常");
//        }
//    }

}
