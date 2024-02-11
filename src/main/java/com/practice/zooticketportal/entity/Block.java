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
@Getter
@Setter
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
//    @JsonIgnore
    @JsonManagedReference // Add this annotation to indicate the "owning" side of the relationship
    private District district;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "block", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Village> village;

//    @Override
//    public String toString() {
//        return "Block{" +
//                "blockId=" + blockId +
//                ", blockName='" + blockName + '\'' +
//                ", blockCode=" + blockCode +
//                ", district=" + district +
//                ", village=" + village +
//                '}';
//    }
}
