package com.example.ngBACKEND.Respons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respons<T> {
    private boolean success;
    private String message;
    private T data;
}
