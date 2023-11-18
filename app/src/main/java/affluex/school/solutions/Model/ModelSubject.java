package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSubject {
    @SerializedName("Fk_SectionID")
    @Expose
    String Fk_SectionID;
    @SerializedName("SubjectName")
    @Expose
    String SubjectName;
    @SerializedName("Fk_SubjectID")
    @Expose
    String Fk_SubjectID;

    public ModelSubject(String subjectName) {
        SubjectName = subjectName;
    }

    public String getFk_SectionID() {
        return Fk_SectionID;
    }

    public void setFk_SectionID(String fk_SectionID) {
        Fk_SectionID = fk_SectionID;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getFk_SubjectID() {
        return Fk_SubjectID;
    }

    public void setFk_SubjectID(String fk_SubjectID) {
        Fk_SubjectID = fk_SubjectID;
    }
}
