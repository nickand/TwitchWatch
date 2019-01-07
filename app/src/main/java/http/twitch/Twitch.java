
package http.twitch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Twitch {

    @SerializedName("data")
    @Expose
    private List<Game> game = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Game> getGame() {
        return game;
    }

    public void setGame(List<Game> game) {
        this.game = game;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
