package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelComplain {
    @SerializedName("Pk_MessageId")
    @Expose
    private String pkMessageId;
    @SerializedName("Fk_UserId")
    @Expose
    private String fkUserId;
    @SerializedName("MemberName")
    @Expose
    private String memberName;
    @SerializedName("MessageTitle")
    @Expose
    private String messageTitle;
    @SerializedName("AddedOn")
    @Expose
    private String addedOn;
    @SerializedName("Messagess")
    @Expose
    private String messagess;
    @SerializedName("cssclass")
    @Expose
    private String cssclass;

    public String getPkMessageId() {
        return pkMessageId;
    }

    public void setPkMessageId(String pkMessageId) {
        this.pkMessageId = pkMessageId;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getMessagess() {
        return messagess;
    }

    public void setMessagess(String messagess) {
        this.messagess = messagess;
    }

    public String getCssclass() {
        return cssclass;
    }

    public void setCssclass(String cssclass) {
        this.cssclass = cssclass;
    }
}
