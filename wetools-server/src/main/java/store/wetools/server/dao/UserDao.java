package store.wetools.server.dao;

import store.wetools.api.been.User;

public interface UserDao {
    public User selectById(String id);

}
