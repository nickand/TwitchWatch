package com.nickand.daggerlogin.login;

import com.nickand.daggerlogin.login.model.User;

public interface LoginActivityMVP {

    interface View {
        String getName();
        String getLastName();

        void showUserNotAvailable();
        void showInputError();
        void showUserSaved();

        void setName(String name);
        void setLastName(String lastName);
    }

    interface Presenter {
        void setView(LoginActivityMVP.View view);

        void loginButtonClicked();

        void getCurrentUser();
    }

    interface Model {
        void createUser(String name,  String lastName);

        User getUser();
    }
}
