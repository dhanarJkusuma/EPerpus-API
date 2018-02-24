package id.veintechnology.apps.library.id.veintechnology.apps.service.category;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import id.veintechnology.apps.library.id.veintechnology.apps.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Service
public class DbCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final GeneratorCodeCategoryService codeCategoryService;

    @Autowired
    public DbCategoryService(CategoryRepository categoryRepository, GeneratorCodeCategoryService codeCategoryService) {
        this.categoryRepository = categoryRepository;
        this.codeCategoryService = codeCategoryService;
    }

    @Override
    public Category createNewCategory(Category category) {
        category.setName(category.getName().trim().toLowerCase());
        category.setCode(codeCategoryService.nextIdSequence());
        return categoryRepository.save(category);
    }

    @Override
    @Nullable
    public Category findByCategoryName(String categoryName) {
        String lowCaseName = categoryName.trim().toLowerCase();
        return categoryRepository.findFirstByName(lowCaseName);
    }

    @Nullable
    @Override
    public Category findByCategoryCode(String code) {
        return categoryRepository.findOneByCode(code);
    }

    @Override
    public List<Category> retrieveAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Set<Category> findByCategoryCodes(List<String> codes) {
        return categoryRepository.findByCodes(codes);
    }

    @Override
    public Category destroyCategory(Category category) {
        categoryRepository.delete(category);
        return category;
    }
}
