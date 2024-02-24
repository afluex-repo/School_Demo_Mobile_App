package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseParentsProfile {
    @SerializedName("lstParentProfiledetails")
    @Expose
    private ArrayList<ModelParentsProfile>  parentsProfileArrayList;


    public ArrayList<ModelParentsProfile> getParentsProfileArrayList() {
        return parentsProfileArrayList;
    }

    public void setParentsProfileArrayList(ArrayList<ModelParentsProfile> parentsProfileArrayList) {
        this.parentsProfileArrayList = parentsProfileArrayList;
    }
}
