package kanishka.purchase_order.purchase_order.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class PurchaseOrderXmlItemDto {

    @JacksonXmlProperty(localName = "ItemName")
    private String itemName;

    @JacksonXmlProperty(localName = "itemUOM")
    private String itemUom;

    @JacksonXmlProperty(localName = "BilledQty")
    private String billedQty;

    @JacksonXmlProperty(localName = "Rate")
    private String rate;

    @JacksonXmlProperty(localName = "Amount")
    private String amount;

}
