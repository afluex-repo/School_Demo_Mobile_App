package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSyllabus {
    @SerializedName("lstSylabusDetails")
    @Expose
    private ArrayList<ModelSyllabusDetails> modelSyllabusDetailsArrayList;


    public ArrayList<ModelSyllabusDetails> getModelSyllabusDetailsArrayList() {
        return modelSyllabusDetailsArrayList;
    }

    public void setModelSyllabusDetailsArrayList(ArrayList<ModelSyllabusDetails> modelSyllabusDetailsArrayList) {
        this.modelSyllabusDetailsArrayList = modelSyllabusDetailsArrayList;
    }
}
