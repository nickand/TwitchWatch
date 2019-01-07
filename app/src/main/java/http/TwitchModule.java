package http;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class TwitchModule {

    public final String BASE_URL = "https://api.twitch.tv/helix/";

    @Provides
    public OkHttpClient provideHttpClient() {
        return null;
    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client) {
        return null;
    }

    @Provides
    public TwitchAPI provideTwitchService() {
        return null;
    }
}
