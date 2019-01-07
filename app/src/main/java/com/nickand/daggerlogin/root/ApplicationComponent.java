package com.nickand.daggerlogin.root;

import com.nickand.daggerlogin.login.view.LoginActivity;
import com.nickand.daggerlogin.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;
import http.TwitchModule;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class, TwitchModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);
}
