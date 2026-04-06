package kanishka.purchase_order.purchase_order.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record PurchaseOrderWrapper(
        @JsonAlias("Voucher Details")
        PurchaseOrderInnerDto voucherDetails
) {
}
