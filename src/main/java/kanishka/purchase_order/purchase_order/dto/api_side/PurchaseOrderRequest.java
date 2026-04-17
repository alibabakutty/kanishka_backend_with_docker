package kanishka.purchase_order.purchase_order.dto.api_side;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderRequest(

        @NotBlank String voucherType,
        @NotNull LocalDate voucherDate,
        @NotBlank String voucherNumber,
        @NotBlank String partyLedgerName,
        @NotBlank String orderNo,
        BigDecimal totalAmount,
        String narration,
        String createdBy,
        String approvedBy,
        List<PurchaseOrderSubFormRequest> inventoryEntries
) {
}
