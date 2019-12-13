package com.king.mpl.Controller;

import com.king.mpl.Service.ClientDiscussService;
import com.king.mpl.Utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ClientDiscussController
 * @Description TODO
 * @Author mai
 * @Date 2019/12/3 10:23
 **/
@RestController
@RequestMapping("/client/discuss")
public class ClientDiscussController {
    @Autowired
    private ClientDiscussService clientDiscussService;

    /**
     * 初始化讨论模块的内容
     * @return 讨论列表
     */
    @PostMapping("/index")
    public ResultVO DiscussIndex(){
        return clientDiscussService.DiscussIndex();
    }

    /**
     * 获得某条讨论的所有信息包括图片
     * @param id 讨论id
     * @return 讨论信息
     */
    @PostMapping("/one")
    public ResultVO getOneDiscuss(@RequestParam("id") int id){
        return clientDiscussService.getOneDiscuss(id);
    }

    /**
     * 发布讨论
     * @param avatar 发布者头像
     * @param sender 发布者
     * @param content 内容
     * @param authorization token
     * @return 讨论的id
     */
    @PostMapping("/addInfo")
    public ResultVO addDiscussInfo(@RequestParam("avatar") String avatar,@RequestParam("sender") String sender,
                                   @RequestParam("content") String content,@RequestHeader("authorization") String authorization){
        return clientDiscussService.addDiscussInfo(avatar,sender,content,authorization);
    }

    /**
     * 给讨论添加图片
     * @param did 讨论的id
     * @param file 图片文件
     * @return 添加结果
     */
    @PostMapping("/addImg")
    public ResultVO addDiscussImg(@RequestParam("did") int did, @RequestParam("file") MultipartFile file){
        return clientDiscussService.addDiscussImg(did,file);
    }

    /**
     * 获得视频的回复和评论
     * @param did 讨论id
     * @return 讨论的评论和回复
     */
    @PostMapping("/getCommentAndReply")
    public ResultVO getCommentAndReply(@RequestParam("did") int did){
        return clientDiscussService.getCommentAndReply(did);
    }

    /**
     * 添加评论
     * @param avatar 头像url
     * @param commentator 评论人
     * @param content 内容
     * @param did 讨论id
     * @return 添加结果
     */
    @PostMapping("/addComment")
    public ResultVO addComment(@RequestParam("avatar") String avatar,@RequestParam("commentator") String commentator,
                               @RequestParam("content") String content,@RequestParam("did") int did){
        return clientDiscussService.addComment(avatar,commentator,content,did);
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
    @PostMapping("/addReply")
    public ResultVO addReply(@RequestParam("reply") String reply,@RequestParam("receiver") String receiver,
                             @RequestParam("content") String content,@RequestParam("cid") int cid,@RequestParam("did") int did){
        return clientDiscussService.addReply(reply,receiver,content,cid,did);
    }
}
