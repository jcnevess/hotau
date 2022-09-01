package org.learning.hotau.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @PositiveOrZero
    private int age;

    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    @Column(nullable = false)
    private boolean isNeutered;

    @NotBlank
    @Column(nullable = false)
    private String breed;

    @NotBlank
    @Column(nullable = false)
    private String sex;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pet_note", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "note_id")
    private List<String> notes;


}
