package affluex.school.solutions.ExamModel.Section;
import com.google.gson.annotations.SerializedName;


public class Section {
    @SerializedName("PK_SectionId")
    public String pK_SectionId;
    @SerializedName("SectionName")
    public String sectionName;
    public Object listSection;
    @SerializedName("Fk_ClassID")
    public String fk_ClassID;
    @SerializedName("ClassName")
    public String className;
    @SerializedName("TeacherID")
    public Object teacherID;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Status")
    public Object status;

    public Section(String pK_SectionId, String sectionName, Object listSection, String fk_ClassID, String className, Object teacherID, Object message, Object status) {
        this.pK_SectionId = pK_SectionId;
        this.sectionName = sectionName;
        this.listSection = listSection;
        this.fk_ClassID = fk_ClassID;
        this.className = className;
        this.teacherID = teacherID;
        this.message = message;
        this.status = status;
    }

    public String getpK_SectionId() {
        return pK_SectionId;
    }

    public void setpK_SectionId(String pK_SectionId) {
        this.pK_SectionId = pK_SectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Object getListSection() {
        return listSection;
    }

    public void setListSection(Object listSection) {
        this.listSection = listSection;
    }

    public String getFk_ClassID() {
        return fk_ClassID;
    }

    public void setFk_ClassID(String fk_ClassID) {
        this.fk_ClassID = fk_ClassID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Object teacherID) {
        this.teacherID = teacherID;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}
