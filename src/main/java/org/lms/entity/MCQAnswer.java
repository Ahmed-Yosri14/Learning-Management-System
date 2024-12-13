package org.lms.entity;

public class MCQAnswer extends AnsweredQuestion{
    int answer;
    public MCQAnswer(){
        super();
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}