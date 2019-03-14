package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "dependency")
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "ClassName", nullable = false)
    private String className;

    @Column(name = "Start", nullable = false)
    private String startTopic;

    @Column(name = "End", nullable = false)
    private String endTopic;


    public Dependency() {
    }

    public Dependency(String className, String startTopic, String endTopic) {
        this.className = className;
        this.startTopic = startTopic;
        this.endTopic = endTopic;
    }

    public Dependency(int id, String className, String startTopic, String endTopic) {
        this.id = id;
        this.className = className;
        this.startTopic = startTopic;
        this.endTopic = endTopic;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", startTopic='" + startTopic + '\'' +
                ", endTopic='" + endTopic + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartTopic() {
        return startTopic;
    }

    public void setStartTopic(String startTopic) {
        this.startTopic = startTopic;
    }

    public String getEndTopic() {
        return endTopic;
    }

    public void setEndTopic(String endTopic) {
        this.endTopic = endTopic;
    }
}
