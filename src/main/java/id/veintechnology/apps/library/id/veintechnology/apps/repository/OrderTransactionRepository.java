package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.OrderTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {
    OrderTransaction findByPublicId(String publicId);

    @Query("SELECT o FROM OrderTransaction o WHERE o.member.publicId = :memberId AND o.returnDate is NULL")
    Page<OrderTransaction> findInCompleteTransactionByMemberId(@Param("memberId") String memberId, Pageable pageable);
}
