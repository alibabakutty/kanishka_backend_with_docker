package kanishka.purchase_order.invoice.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequest {
    private String invoiceNo;
    private String date;
    private String customerName;
    private String orderNo;
    private String address;
    private List<Item> items;
}
