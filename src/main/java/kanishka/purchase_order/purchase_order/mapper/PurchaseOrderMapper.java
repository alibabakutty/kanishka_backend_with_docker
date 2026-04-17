package kanishka.purchase_order.purchase_order.mapper;

import kanishka.purchase_order.purchase_order.dto.api_side.PurchaseOrderRequest;
import kanishka.purchase_order.purchase_order.dto.response_side.PurchaseOrderResponse;
import kanishka.purchase_order.purchase_order.module.PurchaseOrderEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PurchaseOrderSubFormMapper.class})
public interface PurchaseOrderMapper {
    // request -> entity
    PurchaseOrderEntity toEntity(PurchaseOrderRequest request);
    // entity -> response
    PurchaseOrderResponse toDto(PurchaseOrderEntity entity);

    // update
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(PurchaseOrderRequest request, @MappingTarget PurchaseOrderEntity entity);

    @AfterMapping
    default void setParent(@MappingTarget PurchaseOrderEntity entity) {
        if (entity.getInventoryEntries() != null) {
            entity.getInventoryEntries().forEach(item -> item.setPurchaseOrder(entity));
        }
    }
}
