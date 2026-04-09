package kanishka.purchase_order.purchase_order.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.math.BigDecimal;
import java.util.List;

public record PurchaseOrderInnerDto(
        @JsonAlias("Voucher Type")
        String voucherType,
        @JsonAlias("Voucher Date")
        String voucherDate,
        @JsonAlias("Voucher Number")
        String voucherNumber,
        @JsonAlias("PartyLedgerName")
        String partyLedgerName,
        @JsonAlias("Order No")
        String orderNo,
        @JsonAlias("Total Amount")
        BigDecimal totalAmount,
        @JsonAlias("PO Created By")
        String createdBy,
        @JsonAlias("PO Approved By")
        String approvedByTally,
        @JsonAlias("Approved By Tab")
        String approvedByTab,
        @JsonAlias("Inventory Entries")
        List<PurchaseOrderItemRawDto> inventoryEntries
        ) {
}
