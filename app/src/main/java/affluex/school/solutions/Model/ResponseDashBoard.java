package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDashBoard {

    @SerializedName("StudentDetails")
    @Expose
    List<StudentDetails> StudentDetails;

    public List<affluex.school.solutions.Model.StudentDetails> getStudentDetails() {
        return StudentDetails;
    }

    public void setStudentDetails(List<affluex.school.solutions.Model.StudentDetails> studentDetails) {
        StudentDetails = studentDetails;
    }
}
