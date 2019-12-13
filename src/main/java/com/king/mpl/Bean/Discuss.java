package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName Discuss
 * @Description TODO
 * @Author mai
 * @Date 2019/12/3 10:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discuss {
    private int id;
    private String avatar;
    private String sender;
    private String content;
    private int commentCount;
    private String time;
    private String openid;
    private List<DiscussImg> discussImgList;
}
