package kanishka.purchase_order.purchase_order.converter;

import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderRequest;
import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderSubFormRequest;
import kanishka.purchase_order.purchase_order.dto.tally_json.PurchaseOrderWrapper;
import kanishka.purchase_order.purchase_order.dto.tally_xml.PurchaseOrderTallyXmlDto;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PurchaseOrderConverter {
    // tally json -> main dto
    public PurchaseOrderRequest fromTallyJson(PurchaseOrderWrapper wrapper) {
        var dto = wrapper.voucherDetails();

        List<PurchaseOrderSubFormRequest> items = dto.inventoryEntries()
                .stream()
                .map(item -> new PurchaseOrderSubFormRequest(
                        item.itemName(),
                        item.hsnCode(),
                        item.gstPercentage(),
                        item.itemUom(),
                        extractQty(item.billedQty()),
                        new BigDecimal(item.itemRate()),
                        new BigDecimal(item.itemAmount())
                )).toList();

        return new PurchaseOrderRequest(
                dto.voucherType(),
                parseDate(dto.voucherDate()),
                dto.voucherNumber(),
                dto.partyLedgerName(),
                dto.orderNo(),
                dto.totalAmount(),
                dto.narration(),
                dto.createdBy(),
                dto.approvedBy(),
                items
        );
    }

    // xml -> main dto
    public PurchaseOrderRequest fromXml(PurchaseOrderTallyXmlDto xml) {
        List<PurchaseOrderSubFormRequest> items = xml.getInventoryEntries()
                .stream()
                .map(item -> new PurchaseOrderSubFormRequest(
                        item.getItemName(),
                        item.getHsnCode(),
                        item.getGstPercentage(),
                        item.getItemUom(),
                        new BigDecimal(item.getBilledQty()),
                        new BigDecimal(item.getRate()),
                        new BigDecimal(item.getAmount())
                )).toList();

        return new PurchaseOrderRequest(
                xml.getVoucherType(),
                parseDate(xml.getVoucherDate()),
                xml.getVoucherNumber(),
                xml.getPartyLedgerName(),
                xml.getOrderNo(),
                xml.getTotalAmount(),
                xml.getCreatedBy(),
                xml.getApprovedBy(),
                xml.getNarration(),
                items
        );
    }

    private BigDecimal extractQty(String billedQty) {
        return new BigDecimal(billedQty.split(" ")[0]);
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("d-M-yyyy"));
    }
}
