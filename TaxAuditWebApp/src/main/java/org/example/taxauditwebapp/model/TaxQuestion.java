package org.example.taxauditwebapp.model;

public class TaxQuestion {
    private String question;
    private String answer;

    // Default constructor (required for serialization/deserialization)
    public TaxQuestion() {
    }

    // Constructor with both question and answer
    public TaxQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
