package affluex.school.solutions.ExamModel.Exam;
import com.google.gson.annotations.SerializedName;

public class ExamDetail {

    @SerializedName("ExamTypeName")
    public String examTypeName;
    @SerializedName("Pk_ExamTypeId")
    public String pk_ExamTypeId;

    public ExamDetail(String examTypeName, String pk_ExamTypeId) {
        this.examTypeName = examTypeName;
        this.pk_ExamTypeId = pk_ExamTypeId;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public String getPk_ExamTypeId() {
        return pk_ExamTypeId;
    }

    public void setPk_ExamTypeId(String pk_ExamTypeId) {
        this.pk_ExamTypeId = pk_ExamTypeId;
    }
}
