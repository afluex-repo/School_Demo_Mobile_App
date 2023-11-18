package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class lstNoticeList {
    @SerializedName("lstNoticeList")
    @Expose
    List<ResponseNoticeList> noticeDetailsList;

    @SerializedName("Status")
    @Expose

    String status;

    public List<ResponseNoticeList> getNoticeDetailsList() {
        return noticeDetailsList;
    }

    public void setNoticeDetailsList(List<ResponseNoticeList> noticeDetailsList) {
        this.noticeDetailsList = noticeDetailsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
