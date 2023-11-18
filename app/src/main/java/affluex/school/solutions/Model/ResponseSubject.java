package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSubject {
    @SerializedName("listSection")
    @Expose
    List<ModelSubject> listSection;

    public List<ModelSubject> getListSection() {
        return listSection;
    }

    public void setListSection(List<ModelSubject> listSection) {
        this.listSection = listSection;
    }
}
