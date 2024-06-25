package affluex.school.solutions.ExamModel.Exam;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ExamResponse {
    public ArrayList<Lstexamdetail> lstexamdetails;
    @SerializedName("Status")
    public String status;

    public ExamResponse(ArrayList<Lstexamdetail> lstexamdetails, String status) {
        this.lstexamdetails = lstexamdetails;
        this.status = status;
    }

    public ArrayList<Lstexamdetail> getLstexamdetails() {
        return lstexamdetails;
    }

    public void setLstexamdetails(ArrayList<Lstexamdetail> lstexamdetails) {
        this.lstexamdetails = lstexamdetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
