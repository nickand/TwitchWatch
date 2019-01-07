package http;

import http.twitch.Twitch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TwitchAPI {

    @GET("games/top")
    Call<Twitch> getTopGames(@Header("Client-Id") String clientId);
    @GET("games")
    Call<Twitch> getGame(@Header("Client-Id") String clientId, @Query("name") String nameGame);
}
