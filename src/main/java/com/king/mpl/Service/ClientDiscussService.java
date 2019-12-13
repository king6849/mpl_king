package com.king.mpl.Service;

import com.auth0.jwt.JWT;
import com.king.mpl.Bean.Discuss;
import com.king.mpl.Bean.DiscussComment;
import com.king.mpl.Bean.DiscussReply;
import com.king.mpl.Mapper.DiscussMapper;
import com.king.mpl.Utils.FileUtils;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ClientDiscussService
 * @Description TODO
 * @Author mai
 * @Date 2019/12/3 10:22
 **/
@Service
public class ClientDiscussService {

    @Autowired
    private DiscussMapper discussMapper;
    @Value("${file.imgPath}")
    private String imgPath;

    /**
     * 初始化讨论模块的内容
     * @return 讨论列表
     */
    public ResultVO DiscussIndex(){
        List<Discuss> discussList=discussMapper.findAllDiscuss();
        return ResultVO.getSuccessWithData("请求成功",discussList);
    }

    /**
     * 获得某条讨论的所有信息包括图片
     * @param id 讨论id
     * @return 讨论信息
     */
    public ResultVO getOneDiscuss(int id){
        Discuss discuss=discussMapper.findOneDiscuss(id);
        return ResultVO.getSuccessWithData("请求成功",discuss);
    }

    /**
     * 发布讨论
     * @param avatar 发布者头像
     * @param sender 发布者
     * @param content 内容
     * @param authorization token
     * @return 讨论的id
     */
    public ResultVO addDiscussInfo(String avatar,String sender,String content,String authorization){
        int commentCount=0;
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String openid= JWT.decode(authorization).getClaim("openid").asString();
        Discuss discuss=new Discuss(0,avatar,sender,content,commentCount,time,openid,null);
        discussMapper.addDiscussInfo(discuss);
        return ResultVO.getSuccessWithData("插入信息成功",discuss.getId());
    }

    /**
     * 给讨论添加图片
     * @param did 讨论的id
     * @param file 图片文件
     * @return 添加结果
     */
    public ResultVO addDiscussImg(int did, MultipartFile file){
        String imgUrl= FileUtils.saveFile(FileUtils.getRandomName(32),imgPath,file);
        discussMapper.addDiscussImg(imgUrl,did);
        return ResultVO.getSuccess("上传图片成功");
    }

    /**
     * 获得视频的回复和评论
     * @param did 讨论id
     * @return 讨论的评论和回复
     */
    public ResultVO getCommentAndReply(int did){
        List<DiscussComment> commentList=discussMapper.getComment(did);
        List<DiscussReply> replyList=discussMapper.getReply(did);
        Map<String,Object> map=new HashMap<>();
        map.put("commentList",commentList);
        map.put("replyList",replyList);
        return ResultVO.getSuccessWithData("请求成功",map);
    }

    /**
     * 添加评论
     * @param avatar 头像url
     * @param commentator 评论人
     * @param content 内容
     * @param did 讨论id
     * @return 添加结果
     */
    @Transactional
    public ResultVO addComment(String avatar,String commentator,String content,int did){
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        boolean flag=discussMapper.addComment(avatar,commentator,content,time,did);
        if (flag){
            discussMapper.updateCommentCount(did);
            return ResultVO.getSuccess("评论成功");
        }else{
            return ResultVO.getFailed("评论失败");
        }
    }

    /**
     * 添加回复
     * @param reply 回复人
     * @param receiver 接收人
     * @param content 内容
     * @param cid 评论id
     * @param did 讨论id
     * @return 添加结果
     */
    public ResultVO addReply(String reply,String receiver,String content,int cid,int did){
        boolean flag=discussMapper.addReply(reply,receiver,content,cid,did);
        if (flag){
            return ResultVO.getSuccess("回复成功");
        }else{
            return ResultVO.getFailed("回复失败");
        }
    }
}
