package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseComplain {


    @SerializedName("lstcomplain")
    @Expose
    private ArrayList<ModelComplain> complainArrayList;

    public ArrayList<ModelComplain> getComplainArrayList() {
        return complainArrayList;
    }

    public void setComplainArrayList(ArrayList<ModelComplain> complainArrayList) {
        this.complainArrayList = complainArrayList;
    }
}
