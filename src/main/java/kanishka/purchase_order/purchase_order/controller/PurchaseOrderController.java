package kanishka.purchase_order.purchase_order.controller;

import kanishka.purchase_order.purchase_order.converter.PurchaseOrderConverter;
import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderRequest;
import kanishka.purchase_order.purchase_order.dto.response_side.PurchaseOrderResponse;
import kanishka.purchase_order.purchase_order.dto.tally_json.PurchaseOrderWrapper;
import kanishka.purchase_order.purchase_order.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService service;
    private final PurchaseOrderConverter converter;

//    @PostMapping
//    public ResponseEntity<PurchaseOrderResponse> create(@RequestBody PurchaseOrderRequest request) {
//        PurchaseOrderResponse response = service.create(request);
//        return ResponseEntity.status(201).body(response);
//    }

//    @PostMapping("/tally")
//    public ResponseEntity<String> create(@RequestBody String request) {
//        System.out.println(request);
//        return ResponseEntity.ok("Success");
//    }


    // tally json
    @PostMapping("/tally")
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrderFromTally(
            @RequestBody PurchaseOrderWrapper wrapper
    ){
        if (wrapper == null || wrapper.voucherDetails() == null) {
            throw new RuntimeException("Invalid Tally JSON: Voucher Details missing");
        }

        PurchaseOrderRequest request = converter.fromTallyJson(wrapper);

        PurchaseOrderResponse response = service.create(request);

        return ResponseEntity.status(201).body(response);
    }

    // Normal JSON (API Format)
    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
        System.out.println("NORMAL API HIT ✅" + request);

        return ResponseEntity.status(201).body(service.create(request));
    }

//    @PostMapping
//    public PurchaseOrderResponse createTally(@RequestBody String rawJson) {
//        System.out.println(rawJson);
//        return null;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> update(@PathVariable Long id, @RequestBody PurchaseOrderRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
