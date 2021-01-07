package com.github.yuque.service;

import com.github.yuque.api.UserService;
import com.github.yuque.api.model.UserModel;
import com.github.yuque.dao.dataobject.UserDO;
import com.github.yuque.dao.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public String getUserName(Long id) {
        UserDO userDO = userMapper.getById(id);
        return userDO != null ? userDO.getName() : null;
    }

    @Override
    public UserModel addUser(UserModel user) {
        UserDO userDO = new UserDO();

        Long id = userMapper.insert(userDO);
        user.setId(id);
        return user;
    }
}
