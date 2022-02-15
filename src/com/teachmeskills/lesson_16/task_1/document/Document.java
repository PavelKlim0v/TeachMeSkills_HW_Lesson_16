package com.teachmeskills.lesson_16.task_1.document;

import java.util.ArrayList;
import java.util.List;

public class Document {

    private List<String> docPattern = new ArrayList<>();
    private List<String> phonePattern = new ArrayList<>();
    private List<String> emailPattern = new ArrayList<>();

    public Document() { }

    public Document(List<String> docPattern, List<String> phonePattern, List<String> emailPattern) {
        this.docPattern = docPattern;
        this.phonePattern = phonePattern;
        this.emailPattern = emailPattern;
    }

    public void addDocPattern(String str){
        docPattern.add(str);
    }

    public void addPhonePattern(String str){
        phonePattern.add(str);
    }

    public void addEmailPattern(String str){
        emailPattern.add(str);
    }

    @Override
    public String toString() {
        return " Document: " +
                "\n     docPattern = " + docPattern +
                ",\n     phonePattern = " + phonePattern +
                ",\n     emailPattern = " + emailPattern;
    }

}

