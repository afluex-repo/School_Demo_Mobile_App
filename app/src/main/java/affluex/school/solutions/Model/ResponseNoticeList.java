package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNoticeList {
    @SerializedName("Title")
    @Expose
    String title;

    @SerializedName("NoticeDetails")
    @Expose
    List<NoticeDetails>noticeDetails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NoticeDetails> getNoticeDetails() {
        return noticeDetails;
    }

    public void setNoticeDetails(List<NoticeDetails> noticeDetails) {
        this.noticeDetails = noticeDetails;
    }
}
