package id.veintechnology.apps.library.id.veintechnology.apps.service.category;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category createNewCategory(Category category);

    @Nullable
    Category findByCategoryName(String categoryName);

    @Nullable
    Category findByCategoryCode(String code);

    List<Category> retrieveAll();

    Set<Category> findByCategoryCodes(Collection<String> codes);

    Category destroyCategory(Category category);
}
