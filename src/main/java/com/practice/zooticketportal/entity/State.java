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
@Getter
@Setter
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

    // Constructors, getters, setters, etc.


//    @Override
//    public String toString() {
//        return "State{" +
//                "stateId=" + stateId +
//                ", stateName='" + stateName + '\'' +
//                ", stateCode=" + stateCode +
//                ", district=" + district +
//                '}';
//    }
}
