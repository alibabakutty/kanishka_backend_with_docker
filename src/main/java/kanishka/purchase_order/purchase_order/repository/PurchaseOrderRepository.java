package kanishka.purchase_order.purchase_order.repository;

import jakarta.persistence.LockModeType;
import kanishka.purchase_order.purchase_order.module.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {

    // find all
    List<PurchaseOrderEntity> findByOrderNo(String orderNo);
    // check if order number exists
    boolean existsByOrderNo(String orderNo);
    // check if voucher number exists
    Optional<PurchaseOrderEntity> findByVoucherNumber(String voucherNumber);

    @Query("SELECT p FROM PurchaseOrderEntity p WHERE p.superKey = :superKey")
    Optional<PurchaseOrderEntity> findBySuperKey(String superKey);

    // Only General Purchase Order
    @Query("SELECT s From PurchaseOrderEntity s WHERE s.voucherType='Purchase Order'")
    List<PurchaseOrderEntity> generalpurchaseorder();

    // Only Purchase Order  Material
    @Query("SELECT s From PurchaseOrderEntity s WHERE s.voucherType='Purchase Order - Material'")
    List<PurchaseOrderEntity> materialpo();

    // Only Purchase Order Labour
    @Query("SELECT s From PurchaseOrderEntity s WHERE s.voucherType='Purchase Order - Labour'")
    List<PurchaseOrderEntity> labourpo();


    @Query("SELECT s FROM PurchaseOrderEntity s WHERE s.companyName = :companyName and s.approvedBy='Approved'")
    List<PurchaseOrderEntity> findByCompanyName(@Param("companyName") String companyName);
}
