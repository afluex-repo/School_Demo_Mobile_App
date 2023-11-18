package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSection {
    @SerializedName("listSection")
    @Expose
    List<ModelSection> sectionArrayList;

    public List<ModelSection> getSectionArrayList() {
        return sectionArrayList;
    }

    public void setSectionArrayList(List<ModelSection> sectionArrayList) {
        this.sectionArrayList = sectionArrayList;
    }
}
