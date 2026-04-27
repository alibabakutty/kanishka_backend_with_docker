package kanishka.purchase_order.purchase_order.service.service_impl;

import jakarta.transaction.Transactional;
import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderRequest;
import kanishka.purchase_order.purchase_order.dto.response_side.PurchaseOrderResponse;
import kanishka.purchase_order.purchase_order.mapper.PurchaseOrderMapper;
import kanishka.purchase_order.purchase_order.module.PurchaseOrderEntity;
import kanishka.purchase_order.purchase_order.module.PurchaseOrderSubFormEntity;
import kanishka.purchase_order.purchase_order.repository.PurchaseOrderRepository;
import kanishka.purchase_order.purchase_order.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final PurchaseOrderMapper mapper;

     @Transactional
     @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {
        // check if an order with this voucher number already exists
        String superkey=request.companyName() + " - " + request.voucherType()+" - " +request.voucherNumber();
         return repository.findBySuperKey(superkey)
                .map(existingEntity -> {
                    mapper.updateEntityFromRequest(request, existingEntity);

                    existingEntity.getInventoryEntries().forEach(item -> item.setPurchaseOrder(null));
                    existingEntity.getInventoryEntries().clear();
                    repository.flush();
                    PurchaseOrderEntity tempEntity = mapper.toEntity(request);
                    if (tempEntity.getInventoryEntries() != null) {
                        tempEntity.getInventoryEntries().forEach(item -> {
                            item.setPurchaseOrder(existingEntity);
                            item.setSuperKey(superkey);
                        });
                        existingEntity.getInventoryEntries().addAll(tempEntity.getInventoryEntries());
                    }
                    existingEntity.setTotalAmount(calculateTotal(existingEntity.getInventoryEntries()));
                    existingEntity.setSuperKey(superkey);
                    return mapper.toDto(repository.save(existingEntity));
                })
                .orElseGet(() -> {
                    PurchaseOrderEntity newEntity = mapper.toEntity(request);
                    newEntity.setSuperKey(superkey);
                    newEntity.setTotalAmount(calculateTotal(newEntity.getInventoryEntries()));
                    if (newEntity.getInventoryEntries() != null) {
                        newEntity.getInventoryEntries().forEach(item -> {
                            item.setPurchaseOrder(newEntity);
                            item.setSuperKey(superkey);
                        });
                    }
                     return mapper.toDto(repository.save(newEntity));
                });
    }

    @Override
    public PurchaseOrderResponse getById(Long id) {
        PurchaseOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return mapper.toDto(entity);
    }



    @Override
    public List<PurchaseOrderResponse> getpo() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    @Override
    public List<PurchaseOrderResponse> generalpo() {
        return repository.generalpurchaseorder()
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    @Override
    public List<PurchaseOrderResponse> materialpo() {
        return repository.materialpo()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<PurchaseOrderResponse> labourpo() {
        return repository.labourpo()
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    @Override
    public PurchaseOrderResponse update(Long id, PurchaseOrderRequest request) {

         PurchaseOrderEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        // update basic fields
        mapper.updateEntityFromRequest(request, existing);

        // handle line items manually
        existing.getInventoryEntries().clear();
        String Validkey=request.companyName()+" - "+request.voucherType()+" - " +request.voucherNumber();
        List<PurchaseOrderSubFormEntity> newItems = mapper.toEntity(request).getInventoryEntries();

        if (newItems != null) {
            newItems.forEach(item -> {
                item.setPurchaseOrder(existing);
                item.setSuperKey(Validkey);
            });
            existing.getInventoryEntries().addAll(newItems);
        }

        // recalculate total
        existing.setTotalAmount(calculateTotal(existing.getInventoryEntries()));
        existing.setSuperKey(Validkey);

        PurchaseOrderEntity updated = repository.save(existing);

        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Purchase order not found");
        }
        repository.deleteById(id);
    }


    private BigDecimal calculateTotal(List<PurchaseOrderSubFormEntity> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = items.stream()
                .map(PurchaseOrderSubFormEntity::getItemAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // return absolute which is positive value
        return total.abs();
    }
}
