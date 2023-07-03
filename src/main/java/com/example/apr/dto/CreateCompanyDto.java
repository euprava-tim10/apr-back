package com.example.apr.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyDto {
    private String name;

    private String pio;

    private String pib;


}
