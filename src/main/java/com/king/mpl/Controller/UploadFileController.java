package com.king.mpl.Controller;

import com.king.mpl.Bean.Video;
import com.king.mpl.Service.UploadFileService;
import com.king.mpl.Utils.ResultVO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/client/learn")
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/uploadvideo")
    public ResultVO uploadVideo(@RequestParam("file") MultipartFile file[], @RequestParam("level") String level, @RequestParam("type") String type, @RequestHeader("authorization") String authorization) throws IOException {
        uploadFileService.uploadVideo(level, type, authorization, file);
        return ResultVO.getSuccess("正在上传");
    }

    @PostMapping("/index")
    public List<Video> videoList() {
        return uploadFileService.videoList();
    }

    @PostMapping("/watch")
    public int UpWatch(int id) {
        return uploadFileService.UpWatcher(id);
    }

}
