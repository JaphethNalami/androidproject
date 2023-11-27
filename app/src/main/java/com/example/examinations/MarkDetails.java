package com.example.examinations;

public class MarkDetails {
    private String assignment1;
    private String assignment2;
    private String cat1;
    private String cat2;
    private String exam;

    // Empty constructor needed for Firebase
    public MarkDetails() {
    }

    public MarkDetails(String assignment1, String assignment2, String cat1, String cat2, String exam) {
        this.assignment1 = assignment1;
        this.assignment2 = assignment2;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.exam = exam;
    }

    public String getAssignment1() {
        return assignment1;
    }

    public String getAssignment2() {
        return assignment2;
    }

    public String getCat1() {
        return cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public String getExam() {
        return exam;
    }
}
