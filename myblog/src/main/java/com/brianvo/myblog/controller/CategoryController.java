package com.brianvo.myblog.controller;

import com.brianvo.myblog.domain.dto.request.CreateCategoryRequest;
import com.brianvo.myblog.domain.dto.response.CategoryResponse;
import com.brianvo.myblog.domain.entity.Category;
import com.brianvo.myblog.mapper.CategoryMapper;
import com.brianvo.myblog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listCategories() {
        List<CategoryResponse> categories = categoryService.listCategories()
                .stream().map(categoryMapper::toResponse)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category categoryCreate = categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory = categoryService.createCategory(categoryCreate);

        return new ResponseEntity<>(categoryMapper.toResponse(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
