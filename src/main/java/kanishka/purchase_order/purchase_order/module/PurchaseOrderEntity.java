package kanishka.purchase_order.purchase_order.module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
/*@Table(name = "purchase_order", uniqueConstraints = {
        @UniqueConstraint(columnNames = "super_key")
})*/
@Table(name = "purchase_order")
public class PurchaseOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_seq")
    @SequenceGenerator(name = "purchase_order_seq", sequenceName = "purchase_order_sequence", allocationSize = 50)
    private Long id;

    @Column(unique = true)
    private String superKey;

    @NotBlank(message = "Voucher type is required")
    @Column(nullable = false)
    private String voucherType;

    @NotNull(message = "Voucher date is required")
    @Column(name = "voucher_date", nullable = false)
    private LocalDate voucherDate;

    @NotBlank(message = "Voucher number is required")
    @Column(name = "voucher_number", nullable = false)
    private String voucherNumber;

    @NotBlank(message = "Party ledger name is required")
    @Column(name = "party_ledger_name", nullable = false)
    private String partyLedgerName;

    @NotBlank(message = "Order number is required")
    @Column(name = "order_number", nullable = false)
    private String orderNo;

    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 1000)
    private String narration;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name="companyName")
    private String companyName;

    @ToString.Exclude
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PurchaseOrderSubFormEntity> inventoryEntries;


}
