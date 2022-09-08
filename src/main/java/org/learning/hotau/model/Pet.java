package org.learning.hotau.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Entity @Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int age;

    private LocalDate birthday;

    @Column(nullable = false)
    private boolean isNeutered;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String sex;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pet_notes", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "note_id")
    @Builder.Default
    private List<String> notes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private Client owner;
}
