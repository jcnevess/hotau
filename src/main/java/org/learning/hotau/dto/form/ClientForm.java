package org.learning.hotau.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientForm {
    @Email
    private String email;

    @NotBlank
    private String fullName;

    @NotBlank
    private String addressStreet;

    @NotBlank
    private String addressStreetNumber;

    @NotBlank
    private String addressNeighborhood;

    @NotBlank
    private String addressZipcode;

    @NotBlank
    private String addressCity;

    @NotBlank
    private String addressState;

    @NotBlank
    private String addressCountry;

    @NotBlank
    private String mainPhoneNumber;

    private String secondaryPhoneNumber;

    @CPF
    @NotBlank
    private String cpfCode;

    private String nationalIdCode;

    @Past
    private LocalDate birthday;
}
