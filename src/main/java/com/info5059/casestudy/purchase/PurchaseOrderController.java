package com.info5059.casestudy.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderDAO purchaseOrderDAO;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @PostMapping("api/purchaseorders")
    public ResponseEntity<PurchaseOrder> addOne(@RequestBody PurchaseOrder vendor)
    {
        return new ResponseEntity<PurchaseOrder>(purchaseOrderDAO.create(vendor), HttpStatus.OK);
    }

    @GetMapping("/api/purchaseorders")
    public ResponseEntity<Iterable<PurchaseOrder>> findAll() {
        Iterable<PurchaseOrder> pos = purchaseOrderRepository.findAll();
        return new ResponseEntity<Iterable<PurchaseOrder>>(pos, HttpStatus.OK);
    }

    @GetMapping("/api/purchaseorders/{vendorid}")
    public ResponseEntity<Iterable<PurchaseOrder>> findByVendor(@PathVariable Long vendorid) {
        Iterable<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByVendorid(vendorid);
        return new ResponseEntity<Iterable<PurchaseOrder>>(purchaseOrders, HttpStatus.OK);
    }

}
