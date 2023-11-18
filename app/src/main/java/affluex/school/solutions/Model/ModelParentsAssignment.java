package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelParentsAssignment {

    @SerializedName("HomeWorkDetails")
    @Expose
    List<HomeWorkDetails> homeWorkDetailsList;

    public List<HomeWorkDetails> getHomeWorkDetailsList() {
        return homeWorkDetailsList;
    }

    public void setHomeWorkDetailsList(List<HomeWorkDetails> homeWorkDetailsList) {
        this.homeWorkDetailsList = homeWorkDetailsList;
    }
}
