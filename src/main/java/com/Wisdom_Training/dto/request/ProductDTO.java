package com.Wisdom_Training.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private int price;
    private String description;
    private int categoryId;
}
