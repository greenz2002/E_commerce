package com.example.demo.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PermissionReponse {
    private Integer id;
    private String name;
    private String descriptions;
}
