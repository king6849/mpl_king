package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Course
 * @Description TODO
 * @Author mai
 * @Date 2019/12/5 13:47
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private CourseTime time;
    private CourseInfo info1;
    private CourseInfo info2;
    private CourseInfo info3;
    private CourseInfo info4;
    private CourseInfo info5;
}
