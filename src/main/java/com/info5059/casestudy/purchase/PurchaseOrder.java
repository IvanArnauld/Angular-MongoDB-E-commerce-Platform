package com.info5059.casestudy.purchase;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Data
@RequiredArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // FK
    private Long vendorid;
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime podate;
    @OneToMany(targetEntity = PurchaseOrderLineItem.class, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "poid", referencedColumnName = "id")
    private List<PurchaseOrderLineItem> items = new ArrayList<PurchaseOrderLineItem>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getVendorid() {
        return vendorid;
    }
    public void setVendorid(Long vendorid) {
        this.vendorid = vendorid;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public LocalDateTime getPodate() {
        return podate;
    }
    public void setPodate(LocalDateTime podate) {
        this.podate = podate;
    }
}
