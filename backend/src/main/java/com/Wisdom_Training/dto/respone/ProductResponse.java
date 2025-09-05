package com.Wisdom_Training.dto.respone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private int idProduct;
    private String name;
    private int price;
    private String description;
    private int categoryId;
//    private CategoryResponse categoryResponse;
}

