package com.nickand.daggerlogin.login.repository;

import com.nickand.daggerlogin.login.model.User;

public class MemoryRepository implements LoginRepository {

    private User user;

    @Override
    public void saveUser(User user) {
        if (user == null) {
            user = getUser();
        }
        this.user = user;
    }

    @Override
    public User getUser() {
        if (user == null) {
            user = new User("Pato", "Lucas");
            user.setId(0);
            return user;
        } else {
            return user;
        }
    }
}
