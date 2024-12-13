package org.lms.entity;

public class TFAnswer extends AnsweredQuestion {
    boolean answer;
    public TFAnswer(){
        super();
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
