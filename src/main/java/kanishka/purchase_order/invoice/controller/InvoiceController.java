package kanishka.purchase_order.invoice.controller;

import kanishka.purchase_order.invoice.dto.InvoiceRequest;
import kanishka.purchase_order.invoice.dto.Item;
import kanishka.purchase_order.invoice.service.PdfService;
import kanishka.purchase_order.invoice.util.InvoiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = "http://localhost:5173")
public class InvoiceController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest request) throws Exception{
        InvoiceUtil invoiceUtil = new InvoiceUtil();

        BigDecimal total = BigDecimal.ZERO;
        int itemCount = request.getItems().size();
        int rowHeight = 40;   // approx height per row
        int maxRowsPerPage = 12;   // how many rows fit in 1 page
        int emptyRows = maxRowsPerPage - itemCount;
        int spacerHeight = emptyRows * rowHeight;


        // prevent negative
        if(spacerHeight < 0) {
            spacerHeight = 0;
        }

        // ✅ FORMAT EACH ITEM
        for (Item item : request.getItems()) {

            BigDecimal amount = item.getRate()
                    .multiply(BigDecimal.valueOf(item.getQty()));

            item.setAmount(amount);

            // ✅ SET FORMATTED VALUES
            item.setFormattedRate(invoiceUtil.formatINR(item.getRate()));
            item.setFormattedAmount(invoiceUtil.formatINR(amount));

            total = total.add(amount);
        }

        // ✅ FORMAT TOTAL
        String formattedTotal = invoiceUtil.formatINR(total);
        String totalInWords = invoiceUtil.convertToWords(total);


        // send data to thymeleaf
        Context context = new Context();
        context.setVariable("invoiceNo", request.getInvoiceNo());
        context.setVariable("date", request.getDate());
        context.setVariable("customerName", request.getCustomerName());
        context.setVariable("address", request.getAddress());
        context.setVariable("items", request.getItems());

        context.setVariable("spacerHeight", spacerHeight);

        context.setVariable("formattedTotal", formattedTotal);
        context.setVariable("totalInWords", totalInWords);

        // generate html
        String html = templateEngine.process("invoice", context);
        // convert to pdf
        byte[] pdf = pdfService.generatePdf(html);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=invoice.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}
