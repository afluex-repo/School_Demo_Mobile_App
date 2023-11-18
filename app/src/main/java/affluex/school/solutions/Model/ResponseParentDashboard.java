package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseParentDashboard {
    @SerializedName("lstdashboarddata")
    @Expose
    List<ResponseDashBoard> dashBoardList;

    public List<ResponseDashBoard> getDashBoardList() {
        return dashBoardList;
    }

    public void setDashBoardList(List<ResponseDashBoard> dashBoardList) {
        this.dashBoardList = dashBoardList;
    }
}
