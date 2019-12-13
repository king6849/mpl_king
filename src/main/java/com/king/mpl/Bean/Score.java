package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Score
 * @Description TODO
 * @Author mai
 * @Date 2019/12/5 23:18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String semester;
    private String no;
    private String name;
    private String credit;
    private String grade;
}
