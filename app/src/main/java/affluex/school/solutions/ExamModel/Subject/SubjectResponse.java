package affluex.school.solutions.ExamModel.Subject;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class SubjectResponse {
    @SerializedName("Fk_SectionID")
    private String fkSectionId;

    @SerializedName("SubjectName")
    private String subjectName;

    @SerializedName("listSection")
    private List<Subject> listSection;

    @SerializedName("Fk_ClassID")
    private String fkClassId;

    @SerializedName("Fk_SubjectID")
    private String fkSubjectId;

    @SerializedName("TeacherID")
    private String teacherId;

    @SerializedName("Message")
    private String message;

    @SerializedName("SessionName")
    private String sessionName;

    @SerializedName("Status")
    private String status;

    public SubjectResponse(String fkSectionId, String subjectName, List<Subject> listSection, String fkClassId, String fkSubjectId, String teacherId, String message, String sessionName, String status) {
        this.fkSectionId = fkSectionId;
        this.subjectName = subjectName;
        this.listSection = listSection;
        this.fkClassId = fkClassId;
        this.fkSubjectId = fkSubjectId;
        this.teacherId = teacherId;
        this.message = message;
        this.sessionName = sessionName;
        this.status = status;

    }

    public String getFkSectionId() {
        return fkSectionId;
    }

    public void setFkSectionId(String fkSectionId) {
        this.fkSectionId = fkSectionId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Subject> getListSection() {
        return listSection;
    }

    public void setListSection(List<Subject> listSection) {
        this.listSection = listSection;
    }

    public String getFkClassId() {
        return fkClassId;
    }

    public void setFkClassId(String fkClassId) {
        this.fkClassId = fkClassId;
    }

    public String getFkSubjectId() {
        return fkSubjectId;
    }

    public void setFkSubjectId(String fkSubjectId) {
        this.fkSubjectId = fkSubjectId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
