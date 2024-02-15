package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Table(name = "District")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long districtId;

    @Column(unique = true)
    private String districtName;

    @Column(unique = true)
    private Long districtCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    @JsonManagedReference // Add this annotation to indicate the "owning" side of the relationship
    private State state;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district", fetch = FetchType.EAGER)
    @JsonIgnore // Add this annotation to prevent serialization of this field
    private List<Block> block;

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Block> getBlock() {
        return block;
    }

    public void setBlock(List<Block> block) {
        this.block = block;
    }
}