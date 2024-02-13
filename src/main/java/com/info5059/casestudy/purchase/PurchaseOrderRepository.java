package com.info5059.casestudy.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "pos", path = "pos")
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long>{
    // Extend so we can return the number of rows deleted
    @Modifying
    @Transactional
    @Query("delete from PurchaseOrder where id = ?1")
    int deleteOne(Long poId);
    List<PurchaseOrder> findByVendorid(Long vendorid);
}
