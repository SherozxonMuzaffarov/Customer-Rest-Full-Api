package com.restfullapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull(message = "enter fullname")
    private String fullName;

    @NotNull(message = "enter phone number")
    private String phoneNumber;

    @NotNull(message = "enter address")
    private String address;
}
