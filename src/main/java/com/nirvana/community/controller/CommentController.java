package com.nirvana.community.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nirvana.community.dto.ResultDTO;
import com.nirvana.community.dto.InsertComment;
import com.nirvana.community.dto.ShowComment;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.model.Comment;
import com.nirvana.community.model.User;
import com.nirvana.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--24--12:35
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @PostMapping("/comment")
    @ResponseBody          //requestbody中使用大小写会出现问题https://blog.csdn.net/qq_26075861/article/details/54016591
    public Object comment (@RequestBody InsertComment insertComment,
                           HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return ResultDTO.getResult(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setContent(insertComment.getContent());
        comment.setqId(insertComment.getQid());
        comment.setType(insertComment.getType());
        comment.setGmtCreate(System.currentTimeMillis());

        //如果插入失败，再service层会抛异常
        commentService.addComment(comment);

        //能执行到这里说明插入成功
        return ResultDTO.getResult(CustomizeErrorCode.OK);
    }

    /**
     * 展开二级评论
     */
    @GetMapping("/comment/showsec")
    @ResponseBody
    public Object showsec (Integer qid){

        List<ShowComment> showComments = commentService.queryCommentByQId(qid);
        System.out.println(JSON.toJSONString(showComments, SerializerFeature.DisableCircularReferenceDetect));

        return JSON.toJSONString(showComments, SerializerFeature.DisableCircularReferenceDetect);
    }

}
