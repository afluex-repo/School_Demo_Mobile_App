package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHomework {
    @SerializedName("listStudent")
    @Expose
    List<ModelAssignment> assignmentList;

    public List<ModelAssignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<ModelAssignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
