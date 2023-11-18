package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTeachers {
    String name,gender,mobile,email,designation;


    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;
    @SerializedName("LoginId")
    @Expose
    private String LoginId;
    @SerializedName("PK_TeacherID")
    @Expose
    private String PK_TeacherID;
    @SerializedName("ImagePath")
    @Expose
    private String ImagePath;
     @SerializedName("DeviceId")
    @Expose
    private String DeviceId;
     @SerializedName("FireBaseId")
    @Expose
    private String FireBaseId;
     @SerializedName("IsClassTeacher")
    @Expose
    private String IsClassTeacher;
     @SerializedName("Fk_ClassId")
    @Expose
    private String Fk_ClassId;
     @SerializedName("Fk_SectionId")
    @Expose
    private String Fk_SectionId;

    @SerializedName("TeacherName")
    @Expose
    private String TeacherName;

    public ModelTeachers(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public ModelTeachers(String name, String mobile, String designation) {
        this.name = name;
        this.mobile = mobile;
        this.designation = designation;
    }

    public ModelTeachers(String password,
                         String status,
                         String errorMessage,
                         String loginId,
                         String PK_TeacherID,
                         String imagePath,
                         String deviceId,
                         String fireBaseId,
                         String isClassTeacher,
                         String fk_ClassId,
                         String fk_SectionId) {
        Password = password;
        Status = status;
        ErrorMessage = errorMessage;
        LoginId = loginId;
        this.PK_TeacherID = PK_TeacherID;
        ImagePath = imagePath;
        DeviceId = deviceId;
        FireBaseId = fireBaseId;
        IsClassTeacher = isClassTeacher;
        Fk_ClassId = fk_ClassId;
        Fk_SectionId = fk_SectionId;
    }

    public ModelTeachers() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getPK_TeacherID() {
        return PK_TeacherID;
    }

    public void setPK_TeacherID(String PK_TeacherID) {
        this.PK_TeacherID = PK_TeacherID;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getFireBaseId() {
        return FireBaseId;
    }

    public void setFireBaseId(String fireBaseId) {
        FireBaseId = fireBaseId;
    }

    public String getIsClassTeacher() {
        return IsClassTeacher;
    }

    public void setIsClassTeacher(String isClassTeacher) {
        IsClassTeacher = isClassTeacher;
    }

    public String getFk_ClassId() {
        return Fk_ClassId;
    }

    public void setFk_ClassId(String fk_ClassId) {
        Fk_ClassId = fk_ClassId;
    }

    public String getFk_SectionId() {
        return Fk_SectionId;
    }

    public void setFk_SectionId(String fk_SectionId) {
        Fk_SectionId = fk_SectionId;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
}
