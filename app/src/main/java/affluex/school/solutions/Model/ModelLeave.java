package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLeave {

    @SerializedName("AddedBy")
    @Expose
    private Object addedBy;
    @SerializedName("Pk_StudentID")
    @Expose
    private String pkStudentID;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("PK_ClassID")
    @Expose
    private Object pKClassID;
    @SerializedName("Fk_SectionID")
    @Expose
    private Object fkSectionID;
    @SerializedName("PK_TeacherID")
    @Expose
    private Object pKTeacherID;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("SectionName")
    @Expose
    private String sectionName;
    @SerializedName("PK_StdntLeaveID")
    @Expose
    private String pKStdntLeaveID;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("listStudent")
    @Expose
    private Object listStudent;
    @SerializedName("Fk_ClassID")
    @Expose
    private Object fkClassID;
    @SerializedName("TeacherID")
    @Expose
    private Object teacherID;

    public ModelLeave(ModelLeave modelLeave) {
    }

    public Object getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Object addedBy) {
        this.addedBy = addedBy;
    }

    public String getPkStudentID() {
        return pkStudentID;
    }

    public void setPkStudentID(String pkStudentID) {
        this.pkStudentID = pkStudentID;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Object getPKClassID() {
        return pKClassID;
    }

    public void setPKClassID(Object pKClassID) {
        this.pKClassID = pKClassID;
    }

    public Object getFkSectionID() {
        return fkSectionID;
    }

    public void setFkSectionID(Object fkSectionID) {
        this.fkSectionID = fkSectionID;
    }

    public Object getPKTeacherID() {
        return pKTeacherID;
    }

    public void setPKTeacherID(Object pKTeacherID) {
        this.pKTeacherID = pKTeacherID;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getPKStdntLeaveID() {
        return pKStdntLeaveID;
    }

    public void setPKStdntLeaveID(String pKStdntLeaveID) {
        this.pKStdntLeaveID = pKStdntLeaveID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getListStudent() {
        return listStudent;
    }

    public void setListStudent(Object listStudent) {
        this.listStudent = listStudent;
    }

    public Object getFkClassID() {
        return fkClassID;
    }

    public void setFkClassID(Object fkClassID) {
        this.fkClassID = fkClassID;
    }

    public Object getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Object teacherID) {
        this.teacherID = teacherID;
    }

}

