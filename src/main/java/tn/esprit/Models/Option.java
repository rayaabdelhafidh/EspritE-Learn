package tn.esprit.Models;

import java.util.List;

public class Option {
    public int option_id;
    private String option_content;
    private boolean is_correct;
    private Question  question;
    public Option(){

    }

    public Option(int option_id,String option_content, boolean correctOption, Question question) {
        this.option_id=option_id;
        this.option_content = option_content;
        this.is_correct = correctOption;
        this.question = question;

    }


    public Option(int option_id) {
        this.option_id = option_id;
    }

    public int getOption_id() {
        return option_id;
    }

    public void setOption_id(int option_id) {
        this.option_id = option_id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }

    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }

    public int getOptionId() {
        return option_id;
    }

    public void setOptionId(int option_id) {
        this.option_id = option_id;
    }
}
