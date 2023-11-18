package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLeave {
    @SerializedName("listStudent")
    @Expose
    List<ModelLeave> leaveList;

    public List<ModelLeave> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<ModelLeave> leaveList) {
        this.leaveList = leaveList;
    }
}
