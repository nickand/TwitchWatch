package http;

import http.twitch.Twitch;
import retrofit2.Call;
import retrofit2.http.GET;

interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames();
}
