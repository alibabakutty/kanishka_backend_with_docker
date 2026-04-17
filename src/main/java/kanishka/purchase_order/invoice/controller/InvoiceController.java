package kanishka.purchase_order.invoice.controller;

import kanishka.purchase_order.invoice.dto.InvoiceRequest;
import kanishka.purchase_order.invoice.service.PdfService;
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
        // calculate total
        // Professional way to sum BigDecimals
        BigDecimal total = request.getItems()
                .stream()
                .map(item -> item.getRate().multiply(BigDecimal.valueOf(item.getQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // send data to thymeleaf
        Context context = new Context();
        context.setVariable("invoiceNo", request.getInvoiceNo());
        context.setVariable("date", request.getDate());
        context.setVariable("customerName", request.getCustomerName());
        context.setVariable("address", request.getAddress());
        context.setVariable("items", request.getItems());
        context.setVariable("total", total);

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
