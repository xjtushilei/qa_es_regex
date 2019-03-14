package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "baiduzhidao_index", type = "question", shards = 5, replicas = 0)
public class QuestionAnswer {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String question;

    @Field(type = FieldType.Text)
    private String answer;


    public QuestionAnswer() {
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + (answer.length() > 50 ? answer.substring(0, 49) : answer) + '\'' +
                '}';
    }

    public QuestionAnswer(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
