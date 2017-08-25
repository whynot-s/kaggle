package cn.springmvc.service;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by YLT on 2017/8/23.
 */
@Component
public interface UserService {
    public int insert(User user);

    public int update(User user);

    public int delete(String userName);

    public List<User> selectAll();

    public int countAll();

    public User findByUserName(String userName);
}
