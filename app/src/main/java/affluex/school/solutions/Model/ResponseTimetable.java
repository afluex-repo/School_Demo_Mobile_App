package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseTimetable {
    @SerializedName("lsttimetabledetails")
    @Expose
    private ArrayList<ResponseTimeTableDetails> timeTableDetailsArrayList;


    public ArrayList<ResponseTimeTableDetails> getTimeTableDetailsArrayList() {
        return timeTableDetailsArrayList;
    }

    public void setTimeTableDetailsArrayList(ArrayList<ResponseTimeTableDetails> timeTableDetailsArrayList) {
        this.timeTableDetailsArrayList = timeTableDetailsArrayList;
    }
}
