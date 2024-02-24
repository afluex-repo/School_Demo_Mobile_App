package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelFeeReportDetails {

    @SerializedName("FeeReportDetails")
    @Expose
    private ArrayList<ModelFeeReport> feeReportArrayList;


    public ArrayList<ModelFeeReport> getFeeReportArrayList() {
        return feeReportArrayList;
    }

    public void setFeeReportArrayList(ArrayList<ModelFeeReport> feeReportArrayList) {
        this.feeReportArrayList = feeReportArrayList;
    }
}
