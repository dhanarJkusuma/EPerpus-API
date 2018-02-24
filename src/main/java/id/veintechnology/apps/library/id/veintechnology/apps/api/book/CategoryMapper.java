package id.veintechnology.apps.library.id.veintechnology.apps.api.book;

import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CategoryDto;
import id.veintechnology.apps.library.id.veintechnology.apps.api.book.dto.CreateCategoryPayload;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;

public class CategoryMapper {
    public static Category mapToCategory(CreateCategoryPayload payload){
        return new Category(payload.getCategory());
    }

    public static CategoryDto mapToCategoryDto(Category category){
        return new CategoryDto(category.getCode(), category.getName(), category.getCreatedOn());
    }
}
