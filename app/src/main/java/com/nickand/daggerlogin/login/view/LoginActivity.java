package com.nickand.daggerlogin.login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nickand.daggerlogin.R;
import com.nickand.daggerlogin.login.LoginActivityMVP;
import com.nickand.daggerlogin.root.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import http.TwitchAPI;
import http.twitch.Game;
import http.twitch.StreamInfo;
import http.twitch.Streams;
import http.twitch.Twitch;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    private static final String CLIENT_ID = "2rxz79lan41l7apg2zrmx8oj1050i4";

    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    TwitchAPI twitchAPI;

    @BindView(R.id.editTextName)
    private EditText nameEditText;
    @BindView(R.id.editTextLastName)
    private EditText  lastNameEditText;
    @BindView(R.id.buttonLogin)
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplicationContext()).getComponent().inject(this);

        loginButton.setOnClickListener(v -> presenter.loginButtonClicked());

        /*Call<Twitch> call = twitchAPI.getTopGames(CLIENT_ID);
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
        });*/

        twitchAPI.getTopGamesObservable(CLIENT_ID)
            .flatMap((Function<Twitch, Observable<Game>>)
                twitch -> Observable.fromIterable(twitch.getGame()))
            .filter(game -> game.getName().contains("C") || game.getName().contains("c"))
            .flatMap((Function<Game, Observable<String>>)
                game -> Observable.just(game.getName())).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String gameName) {
                    System.out.println(gameName);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

                }
            });

        //-----------Get steams------------------------------//
        twitchAPI.getStreams(CLIENT_ID, "en")
            .flatMap((Function<Streams, Observable<StreamInfo>>)
                twitch -> Observable.fromIterable(twitch.getData()))
            .filter(streamInfo -> !streamInfo.getGameId().isEmpty() &&
                streamInfo.getViewerCount() >= 10000 &&
                streamInfo.getLanguage().equals("en"))
            .flatMap((Function<StreamInfo, Observable<String>>)
                streamInfo -> twitchAPI.getGameObservable(CLIENT_ID, streamInfo.getGameId())
                    .flatMap((Function<Twitch, Observable<Game>>)
                        twitch -> Observable.fromIterable(twitch.getGame()))
                    .flatMap((Function<Game, Observable<String>>) game -> {
                        String streamTitle = streamInfo.getTitle();
                        String gameName = game.getName();

                        return Observable.just("Stream Title: " + streamTitle + " Game Name: " + gameName);
                    }))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String streamGame) {
                    System.out.println(streamGame);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onComplete() {

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
