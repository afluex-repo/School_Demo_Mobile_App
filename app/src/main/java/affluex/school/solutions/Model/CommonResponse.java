package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonResponse {
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("Status")
    @Expose
    private String newStatus;
    @SerializedName("Message")
    @Expose
    private String Message;
  @SerializedName("PunchInDate")
    @Expose
    private String PunchInDate;
 @SerializedName("PunchInTime")
    @Expose
    private String PunchInTime;
 @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;
@SerializedName("PunchOutDate")
    @Expose
    private String PunchOutDate;
@SerializedName("PunchOutTime")
    @Expose
    private String PunchOutTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPunchInDate() {
        return PunchInDate;
    }

    public void setPunchInDate(String punchInDate) {
        PunchInDate = punchInDate;
    }


    public String getPunchInTime() {
        return PunchInTime;
    }

    public void setPunchInTime(String punchInTime) {
        PunchInTime = punchInTime;
    }

    public String getPunchOutDate() {
        return PunchOutDate;
    }

    public void setPunchOutDate(String punchOutDate) {
        PunchOutDate = punchOutDate;
    }

    public String getPunchOutTime() {
        return PunchOutTime;
    }

    public void setPunchOutTime(String punchOutTime) {
        PunchOutTime = punchOutTime;
    }


    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
