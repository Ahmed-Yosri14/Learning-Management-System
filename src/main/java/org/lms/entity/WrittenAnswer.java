package org.lms.entity;

public class WrittenAnswer extends AnsweredQuestion{
    String answer;

    public WrittenAnswer() {
        super();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
