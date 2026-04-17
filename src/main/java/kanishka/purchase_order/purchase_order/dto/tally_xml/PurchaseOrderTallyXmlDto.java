package kanishka.purchase_order.purchase_order.dto.tally_xml;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Voucher")
public class PurchaseOrderTallyXmlDto {

    @JacksonXmlProperty(localName = "VoucherType")
    private String voucherType;

    @JacksonXmlProperty(localName = "VoucherDate")
    private String voucherDate;

    @JacksonXmlProperty(localName = "VoucherNumber")
    private String voucherNumber;

    @JacksonXmlProperty(localName = "PartyLedgerName")
    private String partyLedgerName;

    @JacksonXmlProperty(localName = "OrderNo")
    private String orderNo;

    @JacksonXmlProperty(localName = "TotalAmount")
    private BigDecimal totalAmount;

    @JacksonXmlProperty(localName = "Narration")
    private String narration;

    @JacksonXmlProperty(localName = "PO Created By")
    private String createdBy;

    @JacksonXmlProperty(localName = "PO Approved By")
    private String approvedBy;

    @JacksonXmlElementWrapper(localName = "InventoryEntries")
    @JacksonXmlProperty(localName = "Item")
    private List<PurchaseOrderTallyXmlItemDto> inventoryEntries;
}
