package affluex.school.solutions.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelClass {

    @SerializedName("Fk_ClassID")
            @Expose
            String Fk_ClassID;

    @SerializedName("ClassName")
            @Expose
            String ClassName;
    String title;
    int periods;
    String ct_name;

    public ModelClass(String title, int periods, String ct_name) {
        this.title = title;
        this.periods = periods;
        this.ct_name = ct_name;
    }

    public ModelClass(String className) {
        ClassName = className;
    }

    public ModelClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getCt_name() {
        return ct_name;
    }

    public void setCt_name(String ct_name) {
        this.ct_name = ct_name;
    }

    public String getFk_ClassID() {
        return Fk_ClassID;
    }

    public void setFk_ClassID(String fk_ClassID) {
        Fk_ClassID = fk_ClassID;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }


}
