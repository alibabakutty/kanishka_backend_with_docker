package kanishka.purchase_order.tally_update.service;
import kanishka.purchase_order.purchase_order.module.PurchaseOrderEntity;
import kanishka.purchase_order.purchase_order.repository.PurchaseOrderRepository;
import kanishka.purchase_order.tally_update.dto.Tally_Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.annotation.JsonAppend;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Tally_Service {

    @Autowired
    private PurchaseOrderRepository WebRepository;

    public ResponseEntity<?> getUpdateWeb(String companyName) {
        System.out.println("companyName: " + companyName);
        List<PurchaseOrderEntity> result = WebRepository.findByCompanyName(companyName);
        List<Tally_Dto> Dto=result.stream()
                .map(purchase->{
                    Tally_Dto  dto=new Tally_Dto();
                    dto.setCompanyName(purchase.getCompanyName());
                    dto.setVchType(purchase.getVoucherType());
                    dto.setVchNo(purchase.getVoucherNumber());
                    dto.setDate(purchase.getVoucherDate());
                    return dto;
                })
                .toList();

        Map<String,Object> map=new HashMap<>();
        map.put("approved",Dto);


        return ResponseEntity.ok(map); // return actual data
    }
}
