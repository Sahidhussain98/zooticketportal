package com.practice.zooticketportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "OtherFeesType")
public class OtherFeesType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long otherFeesTypeId;
    @Column(name="otherFeesType")
    private String otherFeesType;
    @OneToMany(mappedBy = "otherFeesType", cascade = CascadeType.ALL)
    private List<OtherFees> otherFees;

    public OtherFeesType() {
    }

    public OtherFeesType(Long otherFeesTypeId, String otherFeesType, List<OtherFees> otherFees) {
        this.otherFeesTypeId = otherFeesTypeId;
        this.otherFeesType = otherFeesType;
        this.otherFees = otherFees;
    }

    public Long getOtherFeesTypeId() {
        return otherFeesTypeId;
    }

    public void setOtherFeesTypeId(Long otherFeesTypeId) {
        this.otherFeesTypeId = otherFeesTypeId;
    }

    public String getOtherFeesType() {
        return otherFeesType;
    }

    public void setOtherFeesType(String otherFeesType) {
        this.otherFeesType = otherFeesType;
    }

    public List<OtherFees> getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(List<OtherFees> otherFees) {
        this.otherFees = otherFees;
    }
    public boolean hasFees() {
        return otherFees != null && !otherFees.isEmpty();
    }

    @Override
    public String toString() {
        return "OtherFeesType{" +
                "otherFeesTypeId=" + otherFeesTypeId +
                ", otherFeesType='" + otherFeesType + '\'' +
                ", otherFees=" + otherFees +
                '}';
    }
}
