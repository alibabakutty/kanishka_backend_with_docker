package kanishka.purchase_order.purchase_order.dto.api_side;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record PurchaseOrderSubFormRequest(
        @NotBlank String itemName,
        String hsnCode,
        BigDecimal gstPercentage,
        String itemUom,
        @NotNull
        @PositiveOrZero
        BigDecimal billedQty,
        BigDecimal itemRate,
        BigDecimal itemAmount
) {
}
