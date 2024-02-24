package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLeaveListDetails {

    @SerializedName("LeaveListDetails")
    @Expose

    private ArrayList<ModelLeaveParents> leaveParentsArrayList;


    public ArrayList<ModelLeaveParents> getLeaveParentsArrayList() {
        return leaveParentsArrayList;
    }

    public void setLeaveParentsArrayList(ArrayList<ModelLeaveParents> leaveParentsArrayList) {
        this.leaveParentsArrayList = leaveParentsArrayList;
    }
}
