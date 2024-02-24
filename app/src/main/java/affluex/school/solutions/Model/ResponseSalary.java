package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseSalary {

    @SerializedName("listEmployeeSalarySlip")
    @Expose
    ArrayList<ModelSalary> salaryArrayList;


    public ArrayList<ModelSalary> getSalaryArrayList() {
        return salaryArrayList;
    }

    public void setSalaryArrayList(ArrayList<ModelSalary> salaryArrayList) {
        this.salaryArrayList = salaryArrayList;
    }
}
