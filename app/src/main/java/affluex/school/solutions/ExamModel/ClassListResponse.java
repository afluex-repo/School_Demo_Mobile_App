package affluex.school.solutions.ExamModel;

import java.util.List;

public class ClassListResponse {
    private String Fk_ClassID;
    private String TeacherID;
    private String Message;
    private String Status;
    private String ClassName;
    private List<ClassItem> listClass;

    public ClassListResponse(String fk_ClassID, String teacherID, String message, String status, String className, List<ClassItem> listClass) {
        Fk_ClassID = fk_ClassID;
        TeacherID = teacherID;
        Message = message;
        Status = status;
        ClassName = className;
        this.listClass = listClass;
    }

    public String getFk_ClassID() {
        return Fk_ClassID;
    }

    public void setFk_ClassID(String fk_ClassID) {
        Fk_ClassID = fk_ClassID;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public List<ClassItem> getListClass() {
        return listClass;
    }

    public void setListClass(List<ClassItem> listClass) {
        this.listClass = listClass;
    }
}
