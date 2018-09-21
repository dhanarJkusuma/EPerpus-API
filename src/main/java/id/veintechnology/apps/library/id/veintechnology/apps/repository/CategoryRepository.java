package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Category findFirstByName(String categoryName);

    Category findOneByCode(String categoryCode);

    @Query("select c from Category c where code in :codes")
    Set<Category> findByCodes(@Param("codes") Collection<String> codes);

    @Query("SELECT c.books FROM Category c WHERE c.code = :categoryCode")
    Page<Book> findBooksByCategory(@Param("categoryCode") String categoryCode, Pageable pageable);
}
