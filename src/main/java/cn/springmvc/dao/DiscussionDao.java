package cn.springmvc.dao;

import cn.springmvc.model.Discussion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by YLT on 2017/12/23.
 */
public interface DiscussionDao {
    List<Map<String,Object>> getDiscussionName();
    Discussion getDiscussionById(@Param("discussionId") int discussionId);
    void insertToDiscussionForTeam(Discussion discussion);
    List<Discussion> getDiscussionForTeam();
}
