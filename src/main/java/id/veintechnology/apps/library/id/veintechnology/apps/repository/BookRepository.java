package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Transactional
public interface BookRepository extends JpaRepository<Book, Long>{

    Book findFirstByCode(String code);

    Book findById(Long bookId);

    List<Book> findByCodeIn(Collection<String> codes);

    @Override
    Page<Book> findAll(Pageable pageable);

    @Query("SELECT distinct b " +
            "from Book b " +
            "join b.categories c " +
            "where c.code = :categoryId")
    Page<Book> findByTitle(@Param("categoryId") String categoryId, Pageable pageable);

    @Query("SELECT b from Book b WHERE b.code in :codes")
    Set<Book> findByCodes(@Param("codes") List<String> codes);

    @Query("SELECT c.books FROM Category c WHERE c.code = :code")
    Page<Book> findByCategoryCode(@Param("code") String code, Pageable pageable);

    @Query("SELECT b from Book b WHERE b.title like %:title%")
    Page<Book> searchBookByTitle(@Param("title") String title, Pageable pageable);


    @Modifying
    @Query("UPDATE Book b SET b.stock = :quantity WHERE b.id = :bookId")
    void updateStock(@Param("bookId") long bookId, @Param("quantity") int quantity);

    @Modifying
    @Query("UPDATE Book b SET b.stock = b.stock + :quantity WHERE b.id = :bookId")
    void addStock(@Param("bookId") long bookId, @Param("quantity") int quantity);

    @Modifying
    @Query("UPDATE Book b SET b.stock = b.stock - :quantity WHERE b.id = :bookId")
    void subtractStock(@Param("bookId") long bookId, @Param("quantity") int quantity);
}
