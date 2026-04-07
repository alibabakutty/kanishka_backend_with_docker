package kanishka.purchase_order.purchase_order.repository;

import kanishka.purchase_order.purchase_order.module.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
