package kanishka.purchase_order.purchase_order.dto.tally_json;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public record PurchaseOrderItemRawDto(
        @JsonAlias("Item Name")
        String itemName,

        @JsonAlias("HSN Code")
        String hsnCode,

        @JsonAlias("GST")
        BigDecimal gstPercentage,

        @JsonAlias("Item UOM")
        String itemUom,

        @JsonAlias("Billedqty")
        String billedQty,

        @JsonAlias("Rate")
        String itemRate,

        @JsonAlias("Amount")
        String itemAmount
) {
}
