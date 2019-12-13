package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName VideoReply
 * @Description TODO
 * @Author mai
 * @Date 2019/12/2 20:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoReply {
    private int id;
    private String reply;
    private String receiver;
    private String content;
    private int cid;
    private int vid;
}
