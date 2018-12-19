package com.nickand.daggerlogin.login.repository;

import com.nickand.daggerlogin.login.model.User;

public interface LoginRepository {

    void saveUser(User user);

    User getUser();
}
