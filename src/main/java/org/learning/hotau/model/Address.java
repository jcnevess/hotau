package org.learning.hotau.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "Addresses")
public class Address {

    // TODO Remove validation annotations
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String street;

    @NotBlank
    @Column(nullable = false)
    private String streetNumber;

    @NotBlank
    @Column(nullable = false)
    private String neighborhood;

    @NotBlank
    @Column(nullable = false)
    private String zipcode;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Column(nullable = false)
    private String state;

    @NotBlank
    @Column(nullable = false)
    private String country;
}