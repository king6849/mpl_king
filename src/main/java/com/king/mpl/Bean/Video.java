package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private int id;
    private String title;
    private String type;
    private String level;
    private int watch;
    private String imgUrl;
    private String videoUrl;
    private String openid;
    private int status;
}
