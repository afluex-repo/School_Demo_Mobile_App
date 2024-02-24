package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLeaveParents {
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("PK_StdntLeaveID")
    @Expose
    private String pKStdntLeaveID;
 @SerializedName("StudentName")
    @Expose
    private String StudentName;
@SerializedName("SectionName")
    @Expose
    private String SectionName;
@SerializedName("ClassName")
    @Expose
    private String ClassName;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getpKStdntLeaveID() {
        return pKStdntLeaveID;
    }

    public void setpKStdntLeaveID(String pKStdntLeaveID) {
        this.pKStdntLeaveID = pKStdntLeaveID;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }
}
