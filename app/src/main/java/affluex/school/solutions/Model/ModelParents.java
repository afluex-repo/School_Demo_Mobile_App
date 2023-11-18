package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelParents {
    @SerializedName("ParentName")
    @Expose
    String ParentName;
    @SerializedName("LoginId")
    @Expose
    String LoginId;
    @SerializedName("Status")
    @Expose
    String Status;
    @SerializedName("Pk_ParentID")
    @Expose
    String Pk_ParentID;

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPk_ParentID() {
        return Pk_ParentID;
    }

    public void setPk_ParentID(String pk_ParentID) {
        Pk_ParentID = pk_ParentID;
    }
}
