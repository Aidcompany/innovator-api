package com.innovator.innovator.models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 512)
    private String description;

    private Integer maxLength;
    private Boolean isDefault;
    private String nameText;

    @Column(length = 512)
    private String descriptionText;

    @ManyToOne
    @JsonIgnore
    private Product product;
}
