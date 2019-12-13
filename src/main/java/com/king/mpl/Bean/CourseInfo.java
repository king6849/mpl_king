package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CourseInfo
 * @Description TODO
 * @Author mai
 * @Date 2019/12/5 14:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    private String name;
    private String classNo;
    private String teacher;
    private String duration;
    private String classroom;
}
