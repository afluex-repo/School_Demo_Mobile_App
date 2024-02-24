package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseTimeTableDetails {
    @SerializedName("TimeTableDetails")
    @Expose
    private ArrayList<ModelTimeTable> timeTableArrayList;

    public ArrayList<ModelTimeTable> getTimeTableArrayList() {
        return timeTableArrayList;
    }

    public void setTimeTableArrayList(ArrayList<ModelTimeTable> timeTableArrayList) {
        this.timeTableArrayList = timeTableArrayList;
    }
}
