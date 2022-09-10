package org.learning.hotau.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ProductForm {

    @NotBlank
    private String name;

    private String description;

    @Positive
    private BigDecimal price;
}
