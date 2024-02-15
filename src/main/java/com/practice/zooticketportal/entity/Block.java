package com.practice.zooticketportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Table(name="Block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long blockId;
    private String blockName;
    @Column(unique=true)
    private Long blockCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "districtCode")
    @JsonManagedReference // Add this annotation to indicate the "owning" side of the relationship
    private District district;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "block", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Village> village;

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Long getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(Long blockCode) {
        this.blockCode = blockCode;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Village> getVillage() {
        return village;
    }

    public void setVillage(List<Village> village) {
        this.village = village;
    }
}
