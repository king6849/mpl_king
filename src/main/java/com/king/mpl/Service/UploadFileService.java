package com.king.mpl.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.king.mpl.Bean.Video;
import com.king.mpl.Entity.User;
import com.king.mpl.Mepper.VideoMepper;
import com.king.mpl.Utils.ConvertVideo;
import com.king.mpl.Utils.MyToken;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileService {
    @Autowired
    private MyToken authorization = null;

    @Autowired
    private VideoMepper videoMepper = null;

    //    @Async("taskExecutor")
    @Transactional
    public ResultVO uploadVideo(String level, String type, String token, MultipartFile[] file) throws IOException {
        DecodedJWT jwt = authorization.parseToken(token);
        String openid = jwt.getClaim("openid").asString();
        String oldName = null;
        for (MultipartFile mul : file) {
            oldName = mul.getOriginalFilename();
            System.out.println("oldName" + oldName);
            File saveFilPath = new File("D:\\idea\\mp4\\tmp\\" + oldName);
            if (saveFilPath.exists()) {
                return ResultVO.getFailed("视频已存在");
            }
            System.out.println(saveFilPath.getPath());
            mul.transferTo(saveFilPath);
            videoMepper.insertVideo2(level, type, openid);
        }
        return ResultVO.getSuccess("上传" + oldName + "成功");
    }

    //视频列表
    public List<Video> videoList() {
        return videoMepper.VideoList();
    }

    //刷新观看人数
    @Transactional
    public int UpWatcher(int id) {
        videoMepper.UpWatcher(id);
        return videoMepper.watchers(id);
    }

}
