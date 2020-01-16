package store.wetools.server.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import store.wetools.api.been.User;
import store.wetools.api.service.UserService;
import store.wetools.server.dao.UserDao;

@Service
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String id) {
        User user = userDao.selectById(id);
        return user;
    }
}

