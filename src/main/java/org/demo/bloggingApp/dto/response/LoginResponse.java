package org.demo.bloggingApp.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private String welcomeNote;
}
