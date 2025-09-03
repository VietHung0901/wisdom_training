package com.Wisdom_Training.service;

import com.Wisdom_Training.dto.request.ProductDTO;
import com.Wisdom_Training.dto.respone.ProductResponse;
import com.Wisdom_Training.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);

    ProductResponse todo(Product product);

    List<Product> getAll();

    Product getProductById(int productId);

    Product updateProduct (int productId, ProductDTO productDTO);

    void deleteProduct(int productId);

    Page<Product> getAllByCategory(int category_id, Pageable pageable); // (with pagination)

    Product updateProductCategory(int productId, int newCategoryId);

    Page<Product> getPaginatedProducts(Pageable pageable);
}
