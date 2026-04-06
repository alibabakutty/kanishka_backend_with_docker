package kanishka.purchase_order.purchase_order.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record PurchaseOrderItemRawDto(
        @JsonAlias("Item Name")
        String itemName,

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
