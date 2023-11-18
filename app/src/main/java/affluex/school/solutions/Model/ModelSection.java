package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSection {
    @SerializedName("PK_SectionId")
    @Expose
    String PK_SectionId;
    @SerializedName("SectionName")
    @Expose
    String SectionName;

    public ModelSection(String sectionName) {
        SectionName = sectionName;
    }

    public String getPK_SectionId() {
        return PK_SectionId;
    }

    public void setPK_SectionId(String PK_SectionId) {
        this.PK_SectionId = PK_SectionId;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }
}
