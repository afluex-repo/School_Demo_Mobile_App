package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFeeReport {
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("BankDetails")
    @Expose
    private String bankDetails;
    @SerializedName("MonthName")
    @Expose
    private Object monthName;
    @SerializedName("PaymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("PaymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("ReceiptNo")
    @Expose
    private String receiptNo;
    @SerializedName("StudentName")
    @Expose
    private String studentName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public Object getMonthName() {
        return monthName;
    }

    public void setMonthName(Object monthName) {
        this.monthName = monthName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
