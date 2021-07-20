package com.company.AppRest.entity.request;

import com.company.AppRest.entity.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Set<Role> roles;
}
