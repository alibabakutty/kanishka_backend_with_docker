package kanishka.purchase_order.purchase_order.dto.response_side;

import java.math.BigDecimal;

public record PurchaseOrderSubFormResponse(
        Long id,
        String itemName,
        String hsnCode,
        BigDecimal gstPercentage,
        String itemUom,
        BigDecimal billedQty,
        BigDecimal itemRate,
        BigDecimal itemAmount
) {
}
