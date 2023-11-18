package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAssignmentDashboard {
    @SerializedName("lsthomeworkDetails")
    @Expose
    List<ModelParentsAssignment> modelParentsAssignments;


    public List<ModelParentsAssignment> getModelParentsAssignments() {
        return modelParentsAssignments;
    }

    public void setModelParentsAssignments(List<ModelParentsAssignment> modelParentsAssignments) {
        this.modelParentsAssignments = modelParentsAssignments;
    }
}
