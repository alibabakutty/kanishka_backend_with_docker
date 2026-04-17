package kanishka.purchase_order.purchase_order.dto.response_side;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderResponse(
        Long id,
        String voucherType,
        LocalDate voucherDate,
        String voucherNumber,
        String partyLedgerName,
        String orderNo,
        BigDecimal totalAmount,
        String narration,
        String createdBy,
        String approvedBy,
        List<PurchaseOrderSubFormResponse> inventoryEntries
) {
}
