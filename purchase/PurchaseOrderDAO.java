package com.info5059.casestudy.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;

import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PurchaseOrderDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository prodRepo;

    @Transactional
    public PurchaseOrder create(PurchaseOrder vendor) {
        PurchaseOrder realPurchaseOrder = new PurchaseOrder();
        realPurchaseOrder.setPodate(LocalDateTime.now());
        realPurchaseOrder.setVendorid(vendor.getVendorid());
        realPurchaseOrder.setAmount(vendor.getAmount());
        entityManager.persist(realPurchaseOrder);

        for (PurchaseOrderLineItem item : vendor.getItems()) {
            PurchaseOrderLineItem realItem = new PurchaseOrderLineItem();
            realItem.setProductid(item.getProductid());
            realItem.setPoid(realPurchaseOrder.getId());
            realItem.setQty(item.getQty());
            realItem.setPrice(item.getPrice());
            entityManager.persist(realItem);
            // we also need to update the QOO on the product table
            Product prod = prodRepo.getReferenceById(item.getProductid());
            prod.setQoo(prod.getQoo() + item.getQty());
            prodRepo.saveAndFlush(prod);
        }
        entityManager.flush();
        entityManager.refresh(realPurchaseOrder);

        return realPurchaseOrder;
    }

    @Transactional
    public List<PurchaseOrder> getAllPurchaseOrders() {
        TypedQuery<PurchaseOrder> query = entityManager.createQuery("SELECT r FROM PurchaseOrder r", PurchaseOrder.class);
        return query.getResultList();
    }

    @Transactional
    public List<PurchaseOrder> getPurchaseOrdersByVendorId(Long vendorId) {
        String jpql = "SELECT r FROM PurchaseOrder r WHERE r.vendorid = :vendorId";
        return entityManager.createQuery(jpql, PurchaseOrder.class)
                .setParameter("vendorId", vendorId)
                .getResultList();
    }
}
