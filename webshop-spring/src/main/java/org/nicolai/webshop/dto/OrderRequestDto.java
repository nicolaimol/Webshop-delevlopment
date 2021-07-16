package org.nicolai.webshop.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderRequestDto {

    private User userId;
    private Map<Integer, Integer> products;
}
