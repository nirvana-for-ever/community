package com.nirvana.community.controller;

import com.nirvana.community.dto.ResultDTO;
import com.nirvana.community.dto.ShowComment;
import com.nirvana.community.enums.CustomizeErrorCode;
import com.nirvana.community.model.Comment;
import com.nirvana.community.model.User;
import com.nirvana.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/comment")
    @ResponseBody          //requestbody中使用大小写会出现问题https://blog.csdn.net/qq_26075861/article/details/54016591
    public Object comment (@RequestBody ShowComment showComment,
                           HttpServletRequest request){
        System.out.println(showComment);
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return ResultDTO.getResult(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setContent(showComment.getContent());
        comment.setqId(showComment.getQid());
        comment.setType(showComment.getType());
        comment.setGmtCreate(System.currentTimeMillis());

        //如果插入失败，再service层会抛异常
        commentService.addComment(comment);

        //能执行到这里说明插入成功
        return ResultDTO.getResult(CustomizeErrorCode.OK);
    }

}
