package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelNotice {
    @SerializedName("lstNoticeList")
    @Expose
    List<lstNoticeList> lstNoticeListList;
}
