package com.ufcg.psoft.commerce.dto.Sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPatchRequestDTO {

    @JsonProperty("disponivel")
    @AssertTrue
    Boolean disponivel;

}
