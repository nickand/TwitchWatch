package com.nickand.daggerlogin.login;

import com.nickand.daggerlogin.login.model.LoginActivityModel;
import com.nickand.daggerlogin.login.presenter.LoginActivityPresenter;
import com.nickand.daggerlogin.login.repository.LoginRepository;
import com.nickand.daggerlogin.login.repository.MemoryRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    public LoginActivityMVP.Presenter provideLoginActivityPresenter(LoginActivityMVP.Model model) {
        return new LoginActivityPresenter(model);
    }

    @Provides
    public LoginActivityMVP.Model provideLoginActivityModel(LoginRepository repository) {
        return new LoginActivityModel(repository);
    }

    @Provides
    public LoginRepository provideLoginRepository () {
        return new MemoryRepository();
    }
}
