package com.Wisdom_Training.controller;

import com.Wisdom_Training.dto.RequestResponse;
import com.Wisdom_Training.dto.request.ProductDTO;
import com.Wisdom_Training.dto.respone.ProductResponse;
import com.Wisdom_Training.entity.Product;
import com.Wisdom_Training.exception.ExceptionResponse;
import com.Wisdom_Training.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RequestResponse("Tạo product thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("Lỗi khi tạo product: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
        try {
            Product product = productService.getProductById(id);
            ProductResponse productResponse = productService.todo(product);
            return ResponseEntity.ok(new RequestResponse(productResponse, "Lấy product thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse("Lỗi khi lấy product: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody ProductDTO productDTO) {
        try {
            Product response = productService.getProductById(id);
            productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(new RequestResponse("Cập nhật product thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("Lỗi khi cập nhật product: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new RequestResponse("Xóa product thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("Lỗi khi xóa product: " + e.getMessage()));
        }
    }

    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> updateProductByCategory(@PathVariable("productId") int productId, @PathVariable("categoryId") int categoryId) {
        try {
            productService.updateProductCategory(productId, categoryId);
            return ResponseEntity.ok(new RequestResponse("Cập nhật product thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("Lỗi khi cập nhật product: " + e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> listProduct = productService.getPaginatedProducts(pageable);
            Page<ProductResponse> response = listProduct.map(productService::todo);
            return ResponseEntity.ok(new RequestResponse(response,"Lấy danh sách product thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse("Lỗi khi lấy danh sách product: " + e.getMessage()));
        }
    }
}
