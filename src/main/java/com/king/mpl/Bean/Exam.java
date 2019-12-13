package com.king.mpl.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Exam
 * @Description TODO
 * @Author mai
 * @Date 2019/12/4 17:48
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    private String no;
    private String name;
    private String date;
    private String time;
    private String examRoomNo;
    private String examRoomName;
    private String seatNumber;
    private String status;
    private int dateGap;
}
