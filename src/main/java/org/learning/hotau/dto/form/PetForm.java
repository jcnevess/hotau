package org.learning.hotau.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
public class PetForm {

    @NotBlank
    private String name;

    @PositiveOrZero
    private int age;

    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    private boolean isNeutered;

    @NotBlank
    private String breed;

    @NotBlank
    private String sex;
}
