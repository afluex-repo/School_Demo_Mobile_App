package affluex.school.solutions.Model;

public class ModelOptions {
    String optionId,optionName;

    public ModelOptions() {
    }

    public ModelOptions(String optionId, String optionName) {
        this.optionId = optionId;
        this.optionName = optionName;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
