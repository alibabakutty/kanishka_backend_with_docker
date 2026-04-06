package kanishka.purchase_order.purchase_order.dto;

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
        List<PurchaseOrderSubFormResponse> inventoryEntries
) {
}
