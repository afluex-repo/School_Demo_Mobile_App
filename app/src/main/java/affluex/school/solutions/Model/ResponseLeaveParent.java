package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseLeaveParent {

    @SerializedName("lstleavelist")
    @Expose

    private ArrayList<ResponseLeaveListDetails> leaveListDetailsArrayList;

    public ArrayList<ResponseLeaveListDetails> getLeaveListDetailsArrayList() {
        return leaveListDetailsArrayList;
    }

    public void setLeaveListDetailsArrayList(ArrayList<ResponseLeaveListDetails> leaveListDetailsArrayList) {
        this.leaveListDetailsArrayList = leaveListDetailsArrayList;
    }
}
