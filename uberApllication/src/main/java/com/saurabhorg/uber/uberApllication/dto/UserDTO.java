package com.saurabhorg.uber.uberApllication.dto;

import com.saurabhorg.uber.uberApllication.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long userId;

    private String name;

    private String email;

    private String password;

    private Set<Role> roles;
}
