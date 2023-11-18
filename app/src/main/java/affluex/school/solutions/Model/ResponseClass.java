package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseClass {
    @SerializedName("TeacherID")
    @Expose
    String TeacherID;

    @SerializedName("Status")
    @Expose
    String status;

    @SerializedName("listClass")
    @Expose
    List<ModelClass> listClass;

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ModelClass> getListClass() {
        return listClass;
    }

    public void setListClass(List<ModelClass> listClass) {
        this.listClass = listClass;
    }
}
