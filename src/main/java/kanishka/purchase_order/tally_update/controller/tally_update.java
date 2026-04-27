package kanishka.purchase_order.tally_update.controller;

import kanishka.purchase_order.tally_update.service.Tally_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


@RestController
@RequestMapping("api/v1/webapproved")
public class tally_update {

    @Autowired
    private Tally_Service tally_service;

    @PostMapping("/approved")
    public ResponseEntity<?> approvedlist(@RequestBody Map<String,String> CompanyName){
        String companyName = CompanyName.get("Company Name");
        return tally_service.getUpdateWeb(companyName);
    }
}
