package com.Wisdom_Training.service.impl;

import com.Wisdom_Training.dto.request.ProductDTO;
import com.Wisdom_Training.dto.respone.ProductResponse;
import com.Wisdom_Training.entity.Category;
import com.Wisdom_Training.entity.Product;
import com.Wisdom_Training.repository.CategoryRepository;
import com.Wisdom_Training.repository.ProductRepository;
import com.Wisdom_Training.service.CategoryService;
import com.Wisdom_Training.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @Override
    public Product createProduct (ProductDTO productDTO) {
        Product product = new Product();
        return saveOrUpdateProduct(product, productDTO);
    }

    @Override
    public ProductResponse todo(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setIdProduct(product.getIdProduct());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategoryId(product.getCategory().getIdCategory());
//        CategoryResponse categoryResponse = categoryService.todo(product.getCategory());
//        productResponse.setCategoryResponse(categoryResponse);
        return productResponse;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Product với id = " + productId));
    }

    @Override
    public Product updateProduct (int productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Product với id = " + productId));
        return saveOrUpdateProduct(product, productDTO);
    }

    @Override
    public void deleteProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Product với id = " + productId));
        productRepository.delete(product);
    }

    private Product saveOrUpdateProduct(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy category với id = " + productDTO.getCategoryId()));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllByCategory(int category_id, Pageable pageable) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Category với id = " + category_id));
        return productRepository.findAllByCategory(category, pageable);
    }

    @Override
    public Product updateProductCategory(int productId, int newCategoryId){
        Product product = getProductById(productId);
        Category category = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy category với id = " + newCategoryId));
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
