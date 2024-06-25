package affluex.school.solutions.ExamModel.Subject;

public class SubjectRequest {
    private String TeacherID;
    private String Fk_ClassID;
    private String Fk_SectionID;

    public SubjectRequest(String teacherID, String fk_ClassID, String fk_SectionID) {
        TeacherID = teacherID;
        Fk_ClassID = fk_ClassID;
        Fk_SectionID = fk_SectionID;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    public String getFk_ClassID() {
        return Fk_ClassID;
    }

    public void setFk_ClassID(String fk_ClassID) {
        Fk_ClassID = fk_ClassID;
    }

    public String getFk_SectionID() {
        return Fk_SectionID;
    }

    public void setFk_SectionID(String fk_SectionID) {
        Fk_SectionID = fk_SectionID;
    }
}
