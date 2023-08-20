package com.lhm.myapp.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String mname;
    private String email;
    private String nextPage;
}
