package kanishka.purchase_order.purchase_order.module;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_order_subform")
@EntityListeners(AuditingEntityListener.class)
public class PurchaseOrderSubFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_sub_seq")
    @SequenceGenerator(name = "purchase_order_sub_seq", sequenceName = "purchase_order_subform_sequence", allocationSize = 50)
    private Long id;

    private String superKey;

    @NotBlank(message = "Item name is required")
    @Column(nullable = false)
    private String itemName;

    @Size(min = 4, max = 10, message = "HSN Code should be between 4 to 10 characters")
    private String hsnCode;

    @Column(precision = 5, scale = 2)
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "100.0", inclusive = true)
    private BigDecimal gstPercentage;

    @Column(nullable = false)
    private String itemUom;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer billedQty;

    @Column(precision = 19, scale = 2)
    private BigDecimal itemRate;

    @Column(precision = 19, scale = 2)
    private BigDecimal itemAmount;

    @Column(name="Company Name")
    private String companyName;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrderEntity purchaseOrder;
}
