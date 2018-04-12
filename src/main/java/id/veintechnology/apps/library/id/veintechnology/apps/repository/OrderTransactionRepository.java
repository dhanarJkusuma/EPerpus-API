package id.veintechnology.apps.library.id.veintechnology.apps.repository;

import id.veintechnology.apps.library.id.veintechnology.apps.dao.OrderTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, Long> {
    OrderTransaction findByPublicId(String publicId);

    @Query("SELECT o FROM OrderTransaction o WHERE o.member.publicId = :memberId AND ( o.returnDate is NULL OR o.isApproved = false )")
    Page<OrderTransaction> findInCompleteTransactionByMemberId(@Param("memberId") String memberId, Pageable pageable);

    @Query("SELECT o FROM OrderTransaction o WHERE o.returnDate is NULL OR o.isApproved = false ")
    Page<OrderTransaction> findAllInCompleteTransactionBy(Pageable pageable);

    @Query("SELECT o FROM OrderTransaction o WHERE o.createdOn >= ?1 AND o.createdOn <= ?2 AND o.returnDate IS NOT NULL AND o.isApproved = true")
    Page<OrderTransaction> findCompleteHistoryTransaction(Date from, Date to, Pageable pageable);
}
