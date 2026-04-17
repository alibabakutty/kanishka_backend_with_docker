package kanishka.purchase_order.purchase_order.service.service_impl;

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

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {
        // check if an order with this voucher number already exists
        return repository.findByVoucherNumber(request.voucherNumber())
                .map(existingEntity -> {
                    // if exists
                    mapper.updateEntityFromRequest(request, existingEntity);
                    // clear and replace line items
                    existingEntity.getInventoryEntries().clear();
                    PurchaseOrderEntity tempEntity = mapper.toEntity(request);

                    if (tempEntity.getInventoryEntries() != null) {
                        tempEntity.getInventoryEntries().forEach(item -> item.setPurchaseOrder(existingEntity));
                        existingEntity.getInventoryEntries().addAll(tempEntity.getInventoryEntries());
                    }
                    existingEntity.setTotalAmount(calculateTotal(existingEntity.getInventoryEntries()));
                    return mapper.toDto(repository.save(existingEntity));
                })
                .orElseGet(() -> {
                    // if it does not exist: create new
                    PurchaseOrderEntity newEntity = mapper.toEntity(request);
                    newEntity.setTotalAmount(calculateTotal(newEntity.getInventoryEntries()));
                    // ensure back-reference for line items is set
                    if (newEntity.getInventoryEntries() != null) {
                        newEntity.getInventoryEntries().forEach(item -> item.setPurchaseOrder(newEntity));
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
    public List<PurchaseOrderResponse> getAll() {
        return repository.findAll()
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

        List<PurchaseOrderSubFormEntity> newItems = mapper.toEntity(request).getInventoryEntries();

        if (newItems != null) {
            newItems.forEach(item -> item.setPurchaseOrder(existing));
            existing.getInventoryEntries().addAll(newItems);
        }

        // recalculate total
        existing.setTotalAmount(calculateTotal(existing.getInventoryEntries()));

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
