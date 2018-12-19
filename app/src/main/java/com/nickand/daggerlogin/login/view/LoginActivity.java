package com.nickand.daggerlogin.login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nickand.daggerlogin.R;
import com.nickand.daggerlogin.login.LoginActivityMVP;
import com.nickand.daggerlogin.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    @Inject
    LoginActivityMVP.Presenter presenter;

    private EditText nameEditText, lastNameEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplicationContext()).getComponent().inject(this);

        nameEditText = findViewById(R.id.editTextName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        loginButton = findViewById(R.id.buttonLogin);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginButtonClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getName() {
        return this.nameEditText.getText().toString();
    }

    @Override
    public String getLastName() {
        return this.lastNameEditText.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "Error, user not available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Error, name or last name can't be null", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSaved() {
        Toast.makeText(this, "User saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setName(String name) {
        this.nameEditText.setText(name);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastNameEditText.setText(lastName);
    }
}
