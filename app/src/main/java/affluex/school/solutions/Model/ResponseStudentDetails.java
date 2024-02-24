package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseStudentDetails {

    @SerializedName("lstStudentdetails")
    @Expose

    private ArrayList<ModelStudentDetails> studentDetails;


    public ArrayList<ModelStudentDetails> getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(ArrayList<ModelStudentDetails> studentDetails) {
        this.studentDetails = studentDetails;
    }
}
