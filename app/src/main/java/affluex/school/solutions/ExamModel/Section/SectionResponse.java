package affluex.school.solutions.ExamModel.Section;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class SectionResponse {
    @SerializedName("PK_SectionId")
    public Object pK_SectionId;
    @SerializedName("SectionName")
    public Object sectionName;
    public ArrayList<Section> listSection;
    @SerializedName("Fk_ClassID")
    public String fk_ClassID;
    @SerializedName("ClassName")
    public Object className;
    @SerializedName("TeacherID")
    public String teacherID;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Status")
    public String status;

    public SectionResponse(Object pK_SectionId, Object sectionName, ArrayList<Section> listSection, String fk_ClassID, Object className, String teacherID, Object message, String status) {
        this.pK_SectionId = pK_SectionId;
        this.sectionName = sectionName;
        this.listSection = listSection;
        this.fk_ClassID = fk_ClassID;
        this.className = className;
        this.teacherID = teacherID;
        this.message = message;
        this.status = status;

    }

    public Object getpK_SectionId() {
        return pK_SectionId;
    }

    public void setpK_SectionId(Object pK_SectionId) {
        this.pK_SectionId = pK_SectionId;
    }

    public Object getSectionName() {
        return sectionName;
    }

    public void setSectionName(Object sectionName) {
        this.sectionName = sectionName;
    }

    public ArrayList<Section> getListSection() {
        return listSection;
    }

    public void setListSection(ArrayList<Section> listSection) {
        this.listSection = listSection;
    }

    public String getFk_ClassID() {
        return fk_ClassID;
    }

    public void setFk_ClassID(String fk_ClassID) {
        this.fk_ClassID = fk_ClassID;
    }

    public Object getClassName() {
        return className;
    }

    public void setClassName(Object className) {
        this.className = className;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
