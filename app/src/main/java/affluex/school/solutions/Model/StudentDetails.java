package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDetails {
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Pk_StudentId")
    @Expose
    private String pkStudentId;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("StudentPhoto")
    @Expose
    private String studentPhoto;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("SectionName")
    @Expose
    private String sectionName;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("FatherOcc")
    @Expose
    private String fatherOcc;
    @SerializedName("MotherOcc")
    @Expose
    private String motherOcc;
    @SerializedName("PermanentAddress")
    @Expose
    private String permanentAddress;
    @SerializedName("CorrespondenceAddress")
    @Expose
    private String correspondenceAddress;
    @SerializedName("RollNo")
    @Expose
    private String rollNo;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPkStudentId() {
        return pkStudentId;
    }

    public void setPkStudentId(String pkStudentId) {
        this.pkStudentId = pkStudentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFatherOcc() {
        return fatherOcc;
    }

    public void setFatherOcc(String fatherOcc) {
        this.fatherOcc = fatherOcc;
    }

    public String getMotherOcc() {
        return motherOcc;
    }

    public void setMotherOcc(String motherOcc) {
        this.motherOcc = motherOcc;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getCorrespondenceAddress() {
        return correspondenceAddress;
    }

    public void setCorrespondenceAddress(String correspondenceAddress) {
        this.correspondenceAddress = correspondenceAddress;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}
