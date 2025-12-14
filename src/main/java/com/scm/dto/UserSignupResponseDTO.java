package com.scm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "User signup response")
public class UserSignupResponseDTO {

    @Schema(example = "SCM07/2025/XXXXXX")
    private String userId;

    @Schema(example = "user@example.com")
    private String email;

    @Schema(example = "9876543210")
    private String contactNumber;
}


