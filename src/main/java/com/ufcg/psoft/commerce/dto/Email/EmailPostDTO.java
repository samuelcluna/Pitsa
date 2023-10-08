package com.ufcg.psoft.commerce.dto.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailPostDTO {

    @NotBlank
    @JsonProperty("receptor")
    @Email
    private String receptor;

    @NotBlank
    @JsonProperty("assunto")
    private String assunto;

    @NotBlank
    @JsonProperty("text")
    private String text;
}
