package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeWorkDetails {

    @SerializedName("SectionName")
    @Expose
    private String sectionName;
    @SerializedName("HomeworkFile")
    @Expose
    private String homeworkFile;
    @SerializedName("HomeworkText")
    @Expose
    private String homeworkText;
    @SerializedName("SubjectName")
    @Expose
    private String subjectName;
    @SerializedName("Pk_HomeworkID")
    @Expose
    private Object pkHomeworkID;
    @SerializedName("ClassName")
    @Expose
    private String className;
    @SerializedName("HomeworkDate")
    @Expose
    private String homeworkDate;
    @SerializedName("Pk_ClassID")
    @Expose
    private String pkClassID;
    @SerializedName("PK_SectionID")
    @Expose
    private Object pKSectionID;
    @SerializedName("Pk_StudentID")
    @Expose
    private String pkStudentID;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Pk_SectionID")
    @Expose
    private String pkSectionID;
    @SerializedName("Pk_SubjectID")
    @Expose
    private String pkSubjectID;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getHomeworkFile() {
        return homeworkFile;
    }

    public void setHomeworkFile(String homeworkFile) {
        this.homeworkFile = homeworkFile;
    }

    public String getHomeworkText() {
        return homeworkText;
    }

    public void setHomeworkText(String homeworkText) {
        this.homeworkText = homeworkText;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Object getPkHomeworkID() {
        return pkHomeworkID;
    }

    public void setPkHomeworkID(Object pkHomeworkID) {
        this.pkHomeworkID = pkHomeworkID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHomeworkDate() {
        return homeworkDate;
    }

    public void setHomeworkDate(String homeworkDate) {
        this.homeworkDate = homeworkDate;
    }

    public String getPkClassID() {
        return pkClassID;
    }

    public void setPkClassID(String pkClassID) {
        this.pkClassID = pkClassID;
    }

    public Object getPKSectionID() {
        return pKSectionID;
    }

    public void setPKSectionID(Object pKSectionID) {
        this.pKSectionID = pKSectionID;
    }

    public String getPkStudentID() {
        return pkStudentID;
    }

    public void setPkStudentID(String pkStudentID) {
        this.pkStudentID = pkStudentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPkSectionID() {
        return pkSectionID;
    }

    public void setPkSectionID(String pkSectionID) {
        this.pkSectionID = pkSectionID;
    }

    public String getPkSubjectID() {
        return pkSubjectID;
    }

    public void setPkSubjectID(String pkSubjectID) {
        this.pkSubjectID = pkSubjectID;
    }

}
