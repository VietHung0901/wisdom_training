package com.Wisdom_Training.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private String username;
    private String password;
    private String role;
}
