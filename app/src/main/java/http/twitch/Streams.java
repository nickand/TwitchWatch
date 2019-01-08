
package http.twitch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Streams {

    @SerializedName("data")
    @Expose
    private List<StreamInfo> data = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<StreamInfo> getData() {
        return data;
    }

    public void setData(List<StreamInfo> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
