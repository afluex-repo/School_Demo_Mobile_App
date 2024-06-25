package affluex.school.solutions.ExamModel.Exam;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Lstexamdetail {
    @SerializedName("Title")
    public String title;
    @SerializedName("ExamDetails")
    public ArrayList<ExamDetail> examDetails;

    public Lstexamdetail(String title, ArrayList<ExamDetail> examDetails) {
        this.title = title;
        this.examDetails = examDetails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ExamDetail> getExamDetails() {
        return examDetails;
    }

    public void setExamDetails(ArrayList<ExamDetail> examDetails) {
        this.examDetails = examDetails;
    }
}
