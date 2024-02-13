INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email)VALUES ('123 Maple
St','London','On', 'N1N-1N1','(555)555-5555','Trusted','ABC Supply Co.','abc@supply.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('543
Sycamore Ave','Toronto','On', 'N1P-1N1','(999)555-5555','Trusted','Big Bills
Depot','bb@depot.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('922 Oak
St','London','On', 'N1N-1N1','(555)555-5599','Untrusted','Shady Sams','ss@underthetable.com');
INSERT INTO Vendor (Address1,City,Province,PostalCode,Phone,Type,Name,Email) VALUES ('1001 Oxford
St','London','On', 'N2N-2N2','(555)555-5500','Trusted','Ivan Kepseu','ik@abc.com');
-- Addinng some products to its table
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('FB001', 1, 'Football', 10.00, 15.00, 5, 20, 50, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('FC001', 2, 'Football Cleats', 30.00, 50.00, 5, 15, 25, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('GG001', 1, 'Goalkeeper Gloves', 15.00, 25.00, 3, 10, 20, 7, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('CS001', 4, 'Cones (Set of 10)', 5.00, 10.00, 2, 30, 50, 15, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES  ('SG001', 3, 'Shin Guards', 8.00, 12.00, 3, 25, 40, 12, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('FTB001', 3, 'Football Training Bibs (Set of 12)', 20.00, 35.00, 5, 20, 30, 8, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('FGN001', 1, 'Football Goal Net', 40.00, 60.00, 2, 5, 12, 3, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('CA001', 4, 'Captain''s Armband', 3.00, 7.00, 1, 50, 80, 20, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('WH001', 4, 'Whistle', 5.00, 7.00, 1, 5, 20, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('BP001', 3, 'Ball Pump', 8.00, 15.00, 2, 15, 30, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('KB001', 2, 'Kickboard', 18.00, 30.00, 5, 10, 20, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('WS001', 1, 'Water Bottle (Set of 3)', 6.00, 12.00, 3, 20, 35, 1, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('BB001', 3, 'Ball Bag', 25.00, 40.00, 5, 12, 20, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('SH001', 4, 'Football Shorts', 15.00, 25.00, 3, 20, 35, 0, null, null);
INSERT INTO Product (id, vendorid, name, costprice, msrp, rop, eoq, qoh, qoo, qrcode, qrcodetxt)
VALUES ('JS001', 4, 'Football Jersey', 25.00, 40.00, 4, 15, 25, 0, null, null);