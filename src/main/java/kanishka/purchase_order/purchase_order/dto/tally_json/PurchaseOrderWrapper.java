package kanishka.purchase_order.purchase_order.dto.tally_json;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record PurchaseOrderWrapper(
        @JsonAlias("Voucher Details")
        PurchaseOrderInnerDto voucherDetails,

        @JsonAlias("purchaseOrders")
        List<PurchaseOrderInnerDto> purchseOrders
) {
        // professional safety: ensure this record is never "null-ish"
//        public PurchaseOrderWrapper {
//                if (voucherDetails == null) {
//                        throw new IllegalArgumentException("Voucher details cannot be null");
//                }
//        }

        public List<PurchaseOrderInnerDto> getOrders() {
                if (purchseOrders != null && !purchseOrders.isEmpty()) {
                        return purchseOrders;
                }
                if (voucherDetails != null) {
                        return List.of(voucherDetails);
                }
                throw new RuntimeException("No purchase order data found");
        }
}
