package com.Wisdom_Training.controller;

import com.Wisdom_Training.dto.request.CategoryDTO;
import com.Wisdom_Training.dto.request.ProductDTO;
import com.Wisdom_Training.dto.respone.CategoryResponse;
import com.Wisdom_Training.dto.respone.ProductResponse;
import com.Wisdom_Training.entity.Category;
import com.Wisdom_Training.entity.Product;
import com.Wisdom_Training.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO){
        try{
            categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo category thành công!");
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi tạo category: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        try{
            List<Category> listCategory = categoryService.getAll();
            List<CategoryResponse> listDTO = listCategory.stream().map(categoryService::todo).toList();;
            return ResponseEntity.ok(Map.of("message", "Lấy danh sách category thành công", "response", listDTO));
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lỗi khi lấy danh sách category: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) {
        try{
            Category category = categoryService.getCategoryById(id);
            CategoryResponse dto = categoryService.todo(category);
            return ResponseEntity.ok(Map.of("message", "Lấy category thành công", "response", dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody CategoryDTO categoryDTO) {
        try{
            Category response = categoryService.getCategoryById(id);
            categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(Map.of("message", "Cập nhật category thành công"));
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi cập nhật category: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(Map.of("message", "Xóa category thành công"));
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi xóa category: " + e.getMessage());
        }
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<?> getAllByCategory(@PathVariable int categoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> products = productService.getAllByCategory(categoryId, pageable);
            Page<ProductResponse> pageProducts = products.map(productService::todo);
            return ResponseEntity.ok(Map.of(
                    "message", "Lấy danh sách sản phẩm thành công",
                    "page", products.getNumber(),
                    "totalPages", products.getTotalPages(),
                    "response", pageProducts
            ));
        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi xóa category: " + e.getMessage());
        }
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<?> createProduct(@PathVariable int  categoryId, @RequestBody ProductDTO productDTO) {
        try {
            productDTO.setCategoryId(categoryId);
            productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo product thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi khi tạo product: " + e.getMessage());
        }
    }
}
