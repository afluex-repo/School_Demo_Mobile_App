package affluex.school.solutions.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSyllabus {
    @SerializedName("Syllabus")
    @Expose
    private String syllabus;


    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
}
