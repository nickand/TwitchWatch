package com.nickand.daggerlogin.login.presenter;

import android.support.annotation.Nullable;

import com.nickand.daggerlogin.login.LoginActivityMVP;
import com.nickand.daggerlogin.login.model.User;

public class LoginActivityPresenter implements LoginActivityMVP.Presenter {

    @Nullable
    private LoginActivityMVP.View view;
    private LoginActivityMVP.Model model;

    public LoginActivityPresenter(LoginActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(@Nullable LoginActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loginButtonClicked() {
        if (view != null) {
            if (view.getName().trim().equals("") ||
                view.getLastName().trim().equals("")) {
                view.showInputError();
            } else {
                model.createUser(view.getName().trim(), view.getLastName().trim());
                view.showUserSaved();
            }
        }
    }

    @Override
    public void getCurrentUser() {
        User user = model.getUser();

        if (user == null) {
            if (view != null) {
                view.showUserNotAvailable();
            }
        } else {
            if (view != null) {
                view.setName(user.getName());
                view.setLastName(user.getLastName());
            }
        }
    }
}
