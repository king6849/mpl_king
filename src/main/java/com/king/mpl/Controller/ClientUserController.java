package com.king.mpl.Controller;

import com.king.mpl.Service.ClientUserService;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/user")
public class ClientUserController {
    @Autowired
    private ClientUserService clientUserService;

    /**
     * 微信小程序端登录
     * @param code 微信小程序端随机生成的code
     * @return authorization
     */
    @PostMapping("/login")
    public ResultVO login(String code) {
        return clientUserService.login(code);
    }

    /**
     * 更新微信小程序端的信息
     * @param avatar 头像链接
     * @param nickname 昵称
     * @param authorization token
     * @return 更新结果
     */
    @PostMapping("/info")
    public ResultVO info(@RequestParam("avatar") String avatar, @RequestParam("nickname") String nickname, @RequestHeader("authorization") String authorization) {
        return clientUserService.info(avatar, nickname, authorization);
    }


}
