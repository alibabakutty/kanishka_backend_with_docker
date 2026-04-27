package kanishka.purchase_order.tally_update.dto;


import kanishka.purchase_order.purchase_order.module.PurchaseOrderEntity;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Data
public class Tally_Dto {
    private String companyName;
    private String VchType;
    private String VchNo;
    private LocalDate date;
}
