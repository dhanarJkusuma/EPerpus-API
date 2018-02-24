package id.veintechnology.apps.library.id.veintechnology.apps.api.book;

import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CategoryDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CreateCategoryPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import id.veintechnology.apps.library.id.veintechnology.apps.security.User;
import id.veintechnology.apps.library.id.veintechnology.apps.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
            response.put("message", "Gagal menambahkan kategori, data sudah ada.");
            return ResponseEntity.badRequest().body(response);
        }
        Category category = CategoryMapper.mapToCategory(payload);
        CategoryDto categoryDto = CategoryMapper.mapToCategoryDto(categoryService.createNewCategory(category));
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Data berhasil ditambahkan.");
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
        Category deletedCategory = categoryService.destroyCategory(existCategory);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Data berhasil dihapus.");
        response.put("data", deletedCategory);
        return ResponseEntity.ok(response);
    }



}
