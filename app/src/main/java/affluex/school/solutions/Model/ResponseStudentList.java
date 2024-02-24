package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseStudentList {


    @SerializedName("lstStudents")
    @Expose
    private ArrayList<ModelStudentAttendance> studentAttendanceArrayList;


    public ArrayList<ModelStudentAttendance> getStudentAttendanceArrayList() {
        return studentAttendanceArrayList;
    }

    public void setStudentAttendanceArrayList(ArrayList<ModelStudentAttendance> studentAttendanceArrayList) {
        this.studentAttendanceArrayList = studentAttendanceArrayList;
    }
}
