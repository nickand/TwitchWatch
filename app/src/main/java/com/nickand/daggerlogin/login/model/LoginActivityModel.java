package com.nickand.daggerlogin.login.model;

import com.nickand.daggerlogin.login.LoginActivityMVP;
import com.nickand.daggerlogin.login.repository.LoginRepository;

public class LoginActivityModel implements LoginActivityMVP.Model {

    private LoginRepository repository;

    public LoginActivityModel(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createUser(String name, String lastName) {
        //Lógica de negocio
        repository.saveUser(new User(name, lastName));
    }

    @Override
    public User getUser() {
        //Lógica de negocio
        return repository.getUser();
    }
}
