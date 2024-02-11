package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="report_labels")
public class ReportLabels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="report_id")
    private Long reportId;
    @Column(name="field_name")
    private String fieldName;
    @Column(name="field_Label")
    private String fieldLabel;

    @Column(name="entered_on")
    private LocalDateTime enteredOn;
    @Column(name="entered_by")
    private String enteredBy;
    //constructors

    public ReportLabels() {
    }

    //Constructors

    public ReportLabels(Long reportId, String fieldName, String fieldLabel, LocalDateTime eneteredOn, String enteredBy) {
        this.reportId = reportId;
        this.fieldName = fieldName;
        this.fieldLabel = fieldLabel;
        this.enteredOn = enteredOn;
        this.enteredBy = enteredBy;
    }


    //Getter and Setters


    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public LocalDateTime getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(LocalDateTime enteredOn) {
        this.enteredOn = enteredOn;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }
}
