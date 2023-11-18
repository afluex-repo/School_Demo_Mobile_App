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
}
