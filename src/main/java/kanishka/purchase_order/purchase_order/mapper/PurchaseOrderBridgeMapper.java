package kanishka.purchase_order.purchase_order.mapper;

import kanishka.purchase_order.purchase_order.dto.tally_json.PurchaseOrderInnerDto;
import kanishka.purchase_order.purchase_order.dto.tally_json.PurchaseOrderItemRawDto;
import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderRequest;
import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderSubFormRequest;
import kanishka.purchase_order.purchase_order.util.DateUtil;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseOrderBridgeMapper {
    public static PurchaseOrderRequest toRequest(PurchaseOrderInnerDto dto) {
        if (dto == null) {
            throw new RuntimeException("PurchaseOrderInnerDto is null");
        }

        return new PurchaseOrderRequest(
                dto.voucherType(),
                DateUtil.parseDate(dto.voucherDate()),
                dto.voucherNumber(),
                dto.partyLedgerName(),
                dto.orderNo(),
                dto.totalAmount(),
                dto.narration(),
                dto.createdBy(),
                dto.approvedBy(),
                mapItems(dto.inventoryEntries())
        );
    }

    private static List<PurchaseOrderSubFormRequest> mapItems(List<PurchaseOrderItemRawDto> items) {
        if (items == null) return List.of();

        return items.stream().map(item -> new PurchaseOrderSubFormRequest(
                item.itemName(),
                item.hsnCode(),
                item.gstPercentage(),
                item.itemUom(),
                toBigDecimal(item.billedQty()),
                toBigDecimal(item.itemRate()),
                toBigDecimal(item.itemAmount())
        )).toList();
    }

    private static BigDecimal toBigDecimal(String value) {
        if (value == null || value.isBlank()) return BigDecimal.ZERO;
        return new BigDecimal(value);
    }
}
