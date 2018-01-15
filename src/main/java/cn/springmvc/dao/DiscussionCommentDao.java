package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by YLT on 2017/12/23.
 */
public interface DiscussionCommentDao {
    List<Integer> getCommentersByDiscussionId(@Param("discussionId")int discussionId);
}
