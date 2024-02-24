package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelSyllabusDetails {
    @SerializedName("SylabusDetails")
    @Expose
    private ArrayList<ModelSyllabus> syllabusArrayList;


    public ArrayList<ModelSyllabus> getSyllabusArrayList() {
        return syllabusArrayList;
    }

    public void setSyllabusArrayList(ArrayList<ModelSyllabus> syllabusArrayList) {
        this.syllabusArrayList = syllabusArrayList;
    }
}
