package org.learning.hotau.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Entity @Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(nullable = false)
    private String fullName;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(nullable = false)
    private String mainPhoneNumber;

    private String secondaryPhoneNumber;

    @Column(nullable = false, unique = true)
    private String cpfCode;

    private String nationalIdCode;

    private LocalDate birthday;

    private LocalDateTime clientSince = LocalDateTime.now();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

}
