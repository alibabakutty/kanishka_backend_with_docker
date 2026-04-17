package kanishka.purchase_order.purchase_order.dto.tally_xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PurchaseOrderTallyXmlItemDto {

    @JacksonXmlProperty(localName = "ItemName")
    private String itemName;

    @JacksonXmlProperty(localName = "HSN Code")
    private String hsnCode;

    @JacksonXmlProperty(localName = "GST")
    private BigDecimal gstPercentage;

    @JacksonXmlProperty(localName = "itemUOM")
    private String itemUom;

    @JacksonXmlProperty(localName = "BilledQty")
    private String billedQty;

    @JacksonXmlProperty(localName = "Rate")
    private String rate;

    @JacksonXmlProperty(localName = "Amount")
    private String amount;

}
