package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseParentNotice {
    @SerializedName("lstnotice")
    @Expose
    private ArrayList<ModelNoticeParent> noticeParentArrayList;


    public ArrayList<ModelNoticeParent> getNoticeParentArrayList() {
        return noticeParentArrayList;
    }

    public void setNoticeParentArrayList(ArrayList<ModelNoticeParent> noticeParentArrayList) {
        this.noticeParentArrayList = noticeParentArrayList;
    }
}
