package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class REsponseFeeReport {

    @SerializedName("lstfeedetails")
    @Expose
    private ArrayList<ModelFeeReportDetails> feeReportDetailsArrayList;

    public ArrayList<ModelFeeReportDetails> getFeeReportDetailsArrayList() {
        return feeReportDetailsArrayList;
    }

    public void setFeeReportDetailsArrayList(ArrayList<ModelFeeReportDetails> feeReportDetailsArrayList) {
        this.feeReportDetailsArrayList = feeReportDetailsArrayList;
    }
}
