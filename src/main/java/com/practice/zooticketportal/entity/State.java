package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Table(name="State")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stateId;

    @Column(unique = true)
    private String stateName;

    @Column(unique = true)
    private Long stateCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state", fetch = FetchType.LAZY)
    @JsonIgnore // Add this annotation to prevent serialization of this field
    private List<District> district;

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getStateCode() {
        return stateCode;
    }

    public void setStateCode(Long stateCode) {
        this.stateCode = stateCode;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }
}
