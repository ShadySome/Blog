package com.lst.blog.service;

import com.lst.blog.dao.CommentRepository;
import com.lst.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {

        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId)); //如果parentCommentID有值，说明该对象是一个回复，给其设置parentComment
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment :comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }

        //合并评论的各层的子代到第一级的子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 合并顶级评论中每一个评论节点的 所有子代到自己的 ReplyComments list中
     */
    private void combineChildren(List<Comment> comments){

        for(Comment comment : comments){
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) //对子代评论的每一个评论节点，迭代出它的子代，放在tempReplys中
            {
                recursively(reply1);
            }
            comment.setReplyComments(tempReplys);

            //清理临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //用来存放所有顶级节点的子代的集合
    private List<Comment> tempReplys  = new ArrayList<>();

    /**
     * 递归迭代，DFS，把顶级节点下的所有的评论节点都放到tempReplys中
     */

    private void recursively(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0)
        {
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply :replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
