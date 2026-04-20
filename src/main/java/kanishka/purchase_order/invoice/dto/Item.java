package kanishka.purchase_order.invoice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Item {
    private String partNo;
    private String name;
    private String hsn;
    private BigDecimal gst;
    private Integer qty;
    private BigDecimal rate;
    private String uom;
    private BigDecimal amount;

    private String formattedRate;
    private String formattedAmount;
}
