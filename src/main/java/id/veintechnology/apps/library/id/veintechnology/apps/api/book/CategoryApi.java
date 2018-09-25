package id.veintechnology.apps.library.id.veintechnology.apps.api.book;

import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CategoryDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CreateCategoryPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import id.veintechnology.apps.library.id.veintechnology.apps.security.User;
import id.veintechnology.apps.library.id.veintechnology.apps.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryApi {

    private final CategoryService categoryService;

    @Autowired
    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCategory(@RequestBody @Validated CreateCategoryPayload payload){
        Category existCategory = categoryService.findByCategoryName(payload.getCategory());
        if(existCategory != null){
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Duplicate Category, Category name is already exists.");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = CategoryMapper.mapToCategory(payload);
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(categoryService.createNewCategory(category));
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Category created successfully.");
        response.put("data", categoryDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCategory(@PathVariable("code") String code, @RequestBody @Validated CreateCategoryPayload payload){
        Category existCategoryByName = categoryService.findByCategoryName(payload.getCategory());
        if(existCategoryByName != null){
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Duplicate Category, Category name is already exists.");
            return ResponseEntity.badRequest().body(response);
        }

        Category existCategory = categoryService.findByCategoryCode(code);
        if(existCategory == null){
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Category doesn't exists.");
            return new ResponseEntity<>(response, NOT_FOUND);
        }

        existCategory.setName(payload.getCategory());
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(categoryService.updateCategory(existCategory));

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Category updated successfully.");
        response.put("data", categoryDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> retrieveCategory(){
        List<Category> categories = categoryService.retrieveAll();
        return categories.stream().map(CategoryMapper::mapToCategoryDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity detailCategory(@PathVariable("code") String code){
        Category existCategory = categoryService.findByCategoryCode(code);
        if(existCategory == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existCategory);
    }

    @DeleteMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity destroyCategoru(@PathVariable("code") String code, @AuthenticationPrincipal User user){
        Category existCategory = categoryService.findByCategoryCode(code);
        if(existCategory == null){
            return ResponseEntity.notFound().build();
        }
        categoryService.destroyCategory(existCategory);
        return ResponseEntity.noContent().build();
    }



}
