package kanishka.purchase_order.invoice.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.util.XRLog; // Import this for logging control
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    // This block runs once when the service is loaded
    static {
        // Method 1: Total Silence (Recommended)
        XRLog.setLoggingEnabled(false);

        // Method 2: If you still want to see errors but no "Info" logs:
        // XRLog.setLevel(Level.WARNING);
    }

    public byte[] generatePdf(String html) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode(); // Optional: Makes generation faster

        builder.useFont(() ->
            getClass().getResourceAsStream("/fonts/NotoSans-Regular.ttf"),
                "Noto Sans"
        );

        builder.withHtmlContent(html, null);
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }
}