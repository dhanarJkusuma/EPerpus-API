package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long>{

    Book findFirstByCode(String code);

    @Override
    Page<Book> findAll(Pageable pageable);

    @Query("SELECT distinct b " +
            "from Book b " +
            "join b.categories c " +
            "where c.code = :categoryId")
    Page<Book> findByTitle(@Param("categoryId") String categoryId, Pageable pageable);
    // @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE CONCAT('%',:title,'%')")

    @Query("SELECT b from Book b WHERE b.code in :codes")
    Set<Book> findByCodes(@Param("codes") List<String> codes);


    @Modifying
    @Query("UPDATE Book b SET b.stock = :quantity WHERE b.id = :bookId")
    void updateStock(@Param("bookId") long bookId, @Param("quantity") int quantity);
}
