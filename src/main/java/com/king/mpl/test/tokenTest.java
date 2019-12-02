package com.king.mpl.test;

import com.king.mpl.Utils.MyToken;
import jdk.nashorn.internal.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class tokenTest {
    private MyToken token=new MyToken();

    @Test
    public void tokentest() {
        String session_id = "wvEE8azdkBqjTN1LeITvxw==";
        String openid = "oRSfn5eZOsohS3BzGcaDD9wNA3yc";
        String tokenstr = token.getCustomerToken(openid, session_id);
        System.out.println(tokenstr);
    }
}
