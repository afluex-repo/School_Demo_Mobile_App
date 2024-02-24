package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseStudentAttendance {
    @SerializedName("ListStudent")
    @Expose
    private ArrayList<ResponseStudentList> responseStudentListArrayList;


    public ArrayList<ResponseStudentList> getResponseStudentListArrayList() {
        return responseStudentListArrayList;
    }

    public void setResponseStudentListArrayList(ArrayList<ResponseStudentList> responseStudentListArrayList) {
        this.responseStudentListArrayList = responseStudentListArrayList;
    }
}
