package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeDetails {
    @SerializedName("NoticeDate")
    @Expose
    String NoticeDate;

    @SerializedName("NoticeName")
    @Expose
    String NoticeName;

    @SerializedName("PK_NoticeId")
    @Expose
    String PK_NoticeId;
     @SerializedName("SectionName")
    @Expose
    String SectionName;
     @SerializedName("FK_SectionId")
    @Expose
    String FK_SectionId;

    @SerializedName("ClassName")
    @Expose
    String ClassName;

    @SerializedName("FK_ClassId")
    @Expose
    String FK_ClassId;





    public String getNoticeDate() {
        return NoticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        NoticeDate = noticeDate;
    }

    public String getNoticeName() {
        return NoticeName;
    }

    public void setNoticeName(String noticeName) {
        NoticeName = noticeName;
    }

    public String getPK_NoticeId() {
        return PK_NoticeId;
    }

    public void setPK_NoticeId(String PK_NoticeId) {
        this.PK_NoticeId = PK_NoticeId;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getFK_SectionId() {
        return FK_SectionId;
    }

    public void setFK_SectionId(String FK_SectionId) {
        this.FK_SectionId = FK_SectionId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getFK_ClassId() {
        return FK_ClassId;
    }

    public void setFK_ClassId(String FK_ClassId) {
        this.FK_ClassId = FK_ClassId;
    }
}
