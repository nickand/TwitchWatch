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

import java.util.List;

import javax.inject.Inject;

import http.TwitchAPI;
import http.twitch.Game;
import http.twitch.Twitch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    private static final String CLIENT_ID = "2rxz79lan41l7apg2zrmx8oj1050i4";

    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    TwitchAPI twitchAPI;

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

        Call<Twitch> call = twitchAPI.getTopGames(CLIENT_ID);
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Game> topGames = response.body().getGame();
                for(Game game: topGames) {
                    System.out.println(game.getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<Twitch> callGame = twitchAPI.getGame(CLIENT_ID, "Pokemon platinum");
        callGame.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Game> games = response.body().getGame();
                for(Game game: games) {
                    System.out.println("GAME: "+game.getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
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
