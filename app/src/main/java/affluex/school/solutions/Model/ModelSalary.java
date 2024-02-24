package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSalary {
    @SerializedName("Pk_PaidSalId")
    @Expose
    private String pkPaidSalId;
    @SerializedName("EmployeeID")
    @Expose
    private String employeeID;
    @SerializedName("EmployeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("Basic")
    @Expose
    private String basic;
    @SerializedName("HRA")
    @Expose
    private String hra;
    @SerializedName("MA")
    @Expose
    private String ma;
    @SerializedName("PA")
    @Expose
    private String pa;
    @SerializedName("CA")
    @Expose
    private String ca;
    @SerializedName("PF")
    @Expose
    private String pf;
    @SerializedName("ExtraWork")
    @Expose
    private String extraWork;
    @SerializedName("Incentive")
    @Expose
    private String incentive;
    @SerializedName("OtherPay")
    @Expose
    private String otherPay;
    @SerializedName("TotalIncome")
    @Expose
    private String totalIncome;
    @SerializedName("ContributionTosociety")
    @Expose
    private String contributionTosociety;
    @SerializedName("Advance")
    @Expose
    private String advance;
    @SerializedName("TDS")
    @Expose
    private String tds;
    @SerializedName("Insurance")
    @Expose
    private String insurance;
    @SerializedName("Other")
    @Expose
    private String other;
    @SerializedName("TotalDeduction")
    @Expose
    private String totalDeduction;
    @SerializedName("NetSalary")
    @Expose
    private String netSalary;
    @SerializedName("MonthName")
    @Expose
    private String monthName;
    @SerializedName("Year")
    @Expose
    private String year;

    public String getPkPaidSalId() {
        return pkPaidSalId;
    }

    public void setPkPaidSalId(String pkPaidSalId) {
        this.pkPaidSalId = pkPaidSalId;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getHra() {
        return hra;
    }

    public void setHra(String hra) {
        this.hra = hra;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getExtraWork() {
        return extraWork;
    }

    public void setExtraWork(String extraWork) {
        this.extraWork = extraWork;
    }

    public String getIncentive() {
        return incentive;
    }

    public void setIncentive(String incentive) {
        this.incentive = incentive;
    }

    public String getOtherPay() {
        return otherPay;
    }

    public void setOtherPay(String otherPay) {
        this.otherPay = otherPay;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getContributionTosociety() {
        return contributionTosociety;
    }

    public void setContributionTosociety(String contributionTosociety) {
        this.contributionTosociety = contributionTosociety;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(String totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public String getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(String netSalary) {
        this.netSalary = netSalary;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
