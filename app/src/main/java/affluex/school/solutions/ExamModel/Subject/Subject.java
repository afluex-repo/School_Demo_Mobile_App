package affluex.school.solutions.ExamModel.Subject;

import com.google.gson.annotations.SerializedName;

public class Subject {
    @SerializedName("Fk_SectionID")
    private String fkSectionId;

    @SerializedName("SubjectName")
    private String subjectName;

    @SerializedName("Fk_ClassID")
    private String fkClassId;

    @SerializedName("Fk_SubjectID")
    private String fkSubjectId;

    public Subject(String fkSectionId, String subjectName, String fkClassId, String fkSubjectId) {
        this.fkSectionId = fkSectionId;
        this.subjectName = subjectName;
        this.fkClassId = fkClassId;
        this.fkSubjectId = fkSubjectId;
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
}
