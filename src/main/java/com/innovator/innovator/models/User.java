package com.innovator.innovator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innovator.innovator.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    private String email;

    @Column(length = 1000000)
    private String photoUrl;

    private String fullName;

    private Double donate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReportError> reportErrorList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recommendation> recommendationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RecommendationNews> recommendationNewsList;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Product> products;

//    @ManyToOne
//    private Product product;

    @ManyToMany
    private Set<Product> productsShared;
}
