package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName VideoComment
 * @Description TODO
 * @Author mai
 * @Date 2019/12/2 19:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoComment {
    private int id;
    private String avatar;
    private String commentator;
    private String content;
    private String time;
    private int vid;
}
