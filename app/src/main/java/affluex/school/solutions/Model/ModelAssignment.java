package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAssignment {
 @SerializedName("SubjectID")
    @Expose
    String subjectId;
 @SerializedName("HomeWorkID")
    @Expose
    String HomeWorkID;
 @SerializedName("HomeWorkHTML")
    @Expose
    String HomeWorkHTML;
 @SerializedName("HomeworkDate")
    @Expose
    String HomeworkDate;
 @SerializedName("ClassName")
    @Expose
    String ClassName;
@SerializedName("SectionName")
    @Expose
    String SectionName;
@SerializedName("SubjectName")
    @Expose
    String SubjectName;
@SerializedName("HomeworkFile")
    @Expose
    String HomeworkFile;
@SerializedName("StudentPhoto")
    @Expose
    String StudentPhoto;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getHomeWorkID() {
        return HomeWorkID;
    }

    public void setHomeWorkID(String homeWorkID) {
        HomeWorkID = homeWorkID;
    }

    public String getHomeWorkHTML() {
        return HomeWorkHTML;
    }

    public void setHomeWorkHTML(String homeWorkHTML) {
        HomeWorkHTML = homeWorkHTML;
    }

    public String getHomeworkDate() {
        return HomeworkDate;
    }

    public void setHomeworkDate(String homeworkDate) {
        HomeworkDate = homeworkDate;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getHomeworkFile() {
        return HomeworkFile;
    }

    public void setHomeworkFile(String homeworkFile) {
        HomeworkFile = homeworkFile;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getStudentPhoto() {
        return StudentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        StudentPhoto = studentPhoto;
    }
}
