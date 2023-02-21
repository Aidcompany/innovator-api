package com.innovator.innovator.models.product;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.innovator.innovator.models.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String avatar;

    @ManyToOne
    @JsonIncludeProperties({
            "clientId", "email", "photoUrl", "fullName", "donate"
    })
    private User owner;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JsonIncludeProperties({
//            "clientId", "email", "photoUrl", "fullName", "donate"
//    })
//    private List<User> shared;

    @ManyToMany
    @JsonIncludeProperties({
            "clientId", "email", "photoUrl", "fullName", "donate"
    })
    private Set<User> shared;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Block> blocks;
}
