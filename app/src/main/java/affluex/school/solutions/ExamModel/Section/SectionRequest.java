package affluex.school.solutions.ExamModel.Section;
import com.google.gson.annotations.SerializedName;

public class SectionRequest {
    @SerializedName("TeacherID")
    public String teacherID;
    @SerializedName("Fk_ClassID")
    public String fk_ClassID;

    public SectionRequest(String teacherID, String fk_ClassID) {
        this.teacherID = teacherID;
        this.fk_ClassID = fk_ClassID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getFk_ClassID() {
        return fk_ClassID;
    }

    public void setFk_ClassID(String fk_ClassID) {
        this.fk_ClassID = fk_ClassID;
    }
}
