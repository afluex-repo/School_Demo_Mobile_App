package affluex.school.solutions.ExamModel;

public class Question {
    private String sectionHeading;
    private String sectionMarks;
    private String questionType;

    public Question(String sectionHeading, String sectionMarks, String questionType) {
        this.sectionHeading = sectionHeading;
        this.sectionMarks = sectionMarks;
        this.questionType = questionType;
    }

    public String getSectionHeading() {
        return sectionHeading;
    }

    public String getSectionMarks() {
        return sectionMarks;
    }

    public String getQuestionType() {
        return questionType;
    }

}
