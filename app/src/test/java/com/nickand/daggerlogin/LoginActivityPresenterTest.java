package com.nickand.daggerlogin;

import com.nickand.daggerlogin.login.LoginActivityMVP;
import com.nickand.daggerlogin.login.model.User;
import com.nickand.daggerlogin.login.presenter.LoginActivityPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PresenterUnitTest {

    private LoginActivityPresenter presenter;
    private User user;

    @Mock
    private LoginActivityMVP.Model mockModel;
    @Mock
    private LoginActivityMVP.View mockView;

    @Before
    public void setUp() {
        initMocks(this);
        user = new User("T-Rex", "The Champion");

        presenter = new LoginActivityPresenter(mockModel);
        presenter.setView(mockView);
    }

    @Test
    public void testNoExistsInteractionWithView() {
        when(mockModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        verify(mockView, never()).showUserNotAvailable();
    }

    @Test
    public void testNoExistsMoraThanOneInteractionWithView() {
        when(mockModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        verify(mockView, times(1)).showUserNotAvailable();

        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void testShowUserNotAvailable() {
        when(mockModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        verify(mockModel, times(1)).getUser();
        verify(mockView).showUserNotAvailable();
    }

    @Test
    public void testLoadFromTheRepoWhenValidUserIsPresent() {
        when(mockModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        verify(mockModel, times(1)).getUser();
        verify(mockView, times(1)).setName("T-Rex");
        verify(mockView, times(1)).setLastName("The Champion");
        verify(mockView, never()).showUserNotAvailable();
    }

    @Test
    public void testCheckInputNameIsEmpty() {
        when(mockView.getName()).thenReturn("");
        when(mockView.getName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getName();
        verify(mockView, never()).getLastName();
        verify(mockView, times(1)).showInputError();
    }

    @Test
    public void testCheckInputLastNameIsEmpty() {
        when(mockView.getName()).thenReturn("T-Rex");
        when(mockView.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getName();
        verify(mockView, times(1)).getLastName();
        verify(mockView, times(1)).showInputError();
    }

    @Test
    public void testSaveValidUser() {
        when(mockView.getName()).thenReturn("T-Rex");
        when(mockView.getLastName()).thenReturn("The Champion");

        presenter.loginButtonClicked();

        verify(mockModel, times(1)).createUser("T-Rex", "The Champion");

        verify(mockView, times(2)).getName();
        verify(mockView, times(2)).getLastName();
        verify(mockView, times(1)).showUserSaved();
    }
}
