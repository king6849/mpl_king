package com.king.mpl.Controller;

import com.king.mpl.Service.ClientStudentService;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

/**
 * @ClassName StudentInfoController
 * @Description TODO
 * @Author mai
 * @Date 2019/12/4 14:49
 **/
@RestController
@RequestMapping("/client/student")
public class ClientStudentController {
    @Autowired
    private ClientStudentService clientStudentService;

    /**
     * 账户绑定
     * @param username 学号
     * @param password 密码
     * @return 绑定结果
     */
    @PostMapping("/bind")
    public ResultVO bindController(@RequestParam("username") String username, @RequestParam("password") String password){
        return clientStudentService.bindService(username, password);
    }

    /**
     * 请求课程信息
     * @param username 用户名
     * @param password 密码
     * @return 课程信息
     * @throws IOException
     */
    @PostMapping("/course")
    public ResultVO courseController(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        return clientStudentService.courseService(username, password);
    }

    //获得考试信息

    /**
     * 请求考试信息
     * @param username 用户名
     * @param password 密码
     * @return 考试信息
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/exam")
    public ResultVO examController(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException, ParseException {
        return clientStudentService.examService(username, password);
    }

    /**
     * 请求成绩信息
     * @param username 用户名
     * @param password 密码
     * @return 成绩信息
     * @throws IOException
     */
    @PostMapping("/score")
    public ResultVO scoreController(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        return clientStudentService.scoreService(username, password);
    }

    //获得考勤信息

    /**
     * 请求考勤信息
     * @param username 用户名
     * @param password 密码
     * @return 考勤信息
     * @throws IOException
     */
    @PostMapping("/attendance")
    public ResultVO attendanceController(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        return clientStudentService.attendanceService(username, password);
    }
}
