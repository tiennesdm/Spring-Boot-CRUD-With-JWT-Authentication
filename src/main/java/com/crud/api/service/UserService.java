package com.crud.api.service;

import com.crud.api.entity.User;
import com.crud.api.entity.UserModel;

public interface UserService {
    User createUser(UserModel user);

    User getUser();

    User updatUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();

    Boolean checkUserExist(UserModel user);

    
}
