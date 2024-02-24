package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelStudentAttendance {
    @SerializedName("SectionName")
    @Expose
    private String sectionName;
    @SerializedName("AttendanceDate")
    @Expose
    private String attendanceDate;
    @SerializedName("SessionName")
    @Expose
    private String sessionName;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("PK_StdntLeaveID")
    @Expose
    private String pKStdntLeaveID;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("StudentName")
    @Expose
    private String studentName;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Object getSessionName() {
        return sessionName;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPKStdntLeaveID() {
        return pKStdntLeaveID;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getpKStdntLeaveID() {
        return pKStdntLeaveID;
    }

    public void setpKStdntLeaveID(String pKStdntLeaveID) {
        this.pKStdntLeaveID = pKStdntLeaveID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
