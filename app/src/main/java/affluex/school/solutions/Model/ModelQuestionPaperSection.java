package affluex.school.solutions.Model;

public class ModelQuestionPaperSection {

    String sectionHeading,sectionMarks;

    public ModelQuestionPaperSection() {
    }

    public ModelQuestionPaperSection(String sectionHeading, String sectionMarks) {
        this.sectionHeading = sectionHeading;
        this.sectionMarks = sectionMarks;
    }

    public String getSectionHeading() {
        return sectionHeading;
    }

    public void setSectionHeading(String sectionHeading) {
        this.sectionHeading = sectionHeading;
    }

    public String getSectionMarks() {
        return sectionMarks;
    }

    public void setSectionMarks(String sectionMarks) {
        this.sectionMarks = sectionMarks;
    }
}
