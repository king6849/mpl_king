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

    @PostMapping("/login")
    public ResultVO login(String code) {
        return clientUserService.login(code);
    }

    @PostMapping("/info")
    public ResultVO info(@RequestParam("avatar") String avatar, @RequestParam("nickname") String nickname, @RequestHeader("authorization") String authorization) {
        return clientUserService.info(avatar, nickname, authorization);

    }


}
