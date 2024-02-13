package com.info5059.casestudy.purchase;

import com.info5059.casestudy.vendor.Vendor;
import com.info5059.casestudy.vendor.VendorRepository;
import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;
import com.info5059.casestudy.product.QRCodeGenerator;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import org.springframework.web.servlet.view.document.AbstractPdfView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
//import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class PurchaseOrderPDFGenerator extends AbstractPdfView{
    public static ByteArrayInputStream generatePurchaseOrder(String poid, PurchaseOrderRepository purchaseOrderRepository, VendorRepository vendorRepository, ProductRepository productRepository) throws IOException
    {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        URL imageUrl = PurchaseOrderPDFGenerator.class.getResource("/static/images/ik_logo.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        // Initialize PDF document to be written to a stream not a file
        PdfDocument pdf = new PdfDocument(writer);

        // Document is the main object
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // add the image to the document
        PageSize pg = PageSize.A4;
        Image img = new Image(ImageDataFactory.create(imageUrl))
                .scaleAbsolute(250, 220)
                .setFixedPosition(pg.getWidth() / 2 - 315, 650);
        document.add(img);

        // now let's add a big heading
        document.add(new Paragraph("\n\n"));
        Locale locale = Locale.of("en", "US");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);

        try {
            // Get the purchaseorder data
            Optional<PurchaseOrder> opt = purchaseOrderRepository.findById(Long.parseLong(poid));
            if (opt.isPresent()) {
                purchaseOrder = opt.get();
            }
            document.add(new Paragraph("\n\n"));
            // Optional<PurchaseOrder> purchaseorderOption =
            // purchaseorderRepository.findById(Long.parseLong(poid));
            document.add(new Paragraph("Purchase Order\n#" + poid)
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setMarginLeft(pg.getWidth() / 2).setMarginTop(-35)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n\n"));
            // add the vendor info for the order here
            Table vendorTable = new Table(2).setWidth(new UnitValue(UnitValue.PERCENT, 30))
                    .setHorizontalAlignment(HorizontalAlignment.LEFT);

            // Dump out the vendor information
            Optional<Vendor> vendorOpt = vendorRepository.findById(purchaseOrder.getVendorid());
            if (vendorOpt.isPresent()) {
                Vendor vendor = vendorOpt.get();
                Cell cell = new Cell().add(new Paragraph("Vendor: ")
                        .setBorder(Border.NO_BORDER)
                        .setBold());
                vendorTable.addCell(cell);
                cell = new Cell().add(new Paragraph(vendor.getName())
                        .setBold()
                        .setBorder(Border.NO_BORDER));
                cell.add(new Paragraph(vendor.getAddress1())).setBold().setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(vendor.getCity())).setBold().setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(vendor.getProvince())).setBold().setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(vendor.getEmail())).setBold().setBorder(Border.NO_BORDER);
                
                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                vendorTable.addCell(cell);
            }

            // Product details table
            Table productTable = new Table(5);
            productTable.setWidth(new UnitValue(UnitValue.PERCENT, 100));
            BigDecimal subTotal = new BigDecimal(0.0);
            BigDecimal tax = new BigDecimal(0.0);
            BigDecimal poTotal = new BigDecimal(0.0);

            // Add a table header row
            Cell headerCell = new Cell().add(new Paragraph("Product Code")
                    .setBackgroundColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER));

            productTable.addCell(headerCell);

            headerCell = new Cell().add(new Paragraph("Description")
                    .setBackgroundColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER));
            headerCell.setTextAlignment(TextAlignment.CENTER);
            productTable.addCell(headerCell);

            headerCell = new Cell().add(new Paragraph("Qty Sold")
                    .setBackgroundColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER));
            headerCell.setTextAlignment(TextAlignment.CENTER);
            productTable.addCell(headerCell);

            headerCell = new Cell().add(new Paragraph("Price")
                    .setBackgroundColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER));
            headerCell.setTextAlignment(TextAlignment.CENTER);
            productTable.addCell(headerCell);

            headerCell = new Cell().add(new Paragraph("Ext. Price")
                    .setBackgroundColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER));
            headerCell.setTextAlignment(TextAlignment.CENTER);
            productTable.addCell(headerCell);

            // Dump out the line items
            for (PurchaseOrderLineItem line : purchaseOrder.getItems()) {
                Optional<Product> optx = productRepository.findById(line.getProductid());
                if (optx.isPresent()) {
                    Product product = optx.get();

                    // Dump Product Code, Description, Qty Sold, Price and Ext. Price columns
                    Cell cell = new Cell().add(new Paragraph(String.valueOf(product.getId()))
                            .setTextAlignment(TextAlignment.CENTER));
                    productTable.addCell(cell);

                    cell = new Cell()
                            .add(new Paragraph(product.getName())
                                    .setTextAlignment(TextAlignment.CENTER));
                    productTable.addCell(cell);

                    cell = new Cell()
                            .add(new Paragraph(String.valueOf(line.getQty()))
                                    .setTextAlignment(TextAlignment.RIGHT));
                    cell.setTextAlignment(TextAlignment.RIGHT);
                    productTable.addCell(cell);

                    cell = new Cell()
                            .add(new Paragraph(formatter.format(line.getPrice()))
                                    .setTextAlignment(TextAlignment.RIGHT));
                    cell.setTextAlignment(TextAlignment.RIGHT);
                    productTable.addCell(cell);

                    BigDecimal extPrice = line.getPrice().multiply(BigDecimal.valueOf(line.getQty()));
                    cell = new Cell()
                            .add(new Paragraph(formatter.format(extPrice))
                                    .setTextAlignment(TextAlignment.RIGHT));
                    productTable.addCell(cell);

                    subTotal = subTotal.add(extPrice, new MathContext(8, RoundingMode.UP));
                }
            }

            tax = subTotal.multiply(BigDecimal.valueOf(0.13));
            poTotal = subTotal.add(tax);

            Cell cell = new Cell(1, 4).add(new Paragraph("Sub Total:")
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT));
            productTable.addCell(cell);

            cell = new Cell().add(new Paragraph(formatter.format(subTotal))
                    .setTextAlignment(TextAlignment.RIGHT));
            productTable.addCell(cell);
            
            cell = new Cell(1, 4).add(new Paragraph("Tax:")
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT));
            productTable.addCell(cell);

            cell = new Cell().add(new Paragraph(formatter.format(tax))
                    .setTextAlignment(TextAlignment.RIGHT));
            productTable.addCell(cell);

            cell = new Cell(1, 4).add(new Paragraph("PO Total:")
                    .setBorder(null)
                    .setTextAlignment(TextAlignment.RIGHT));
            productTable.addCell(cell);

            cell = new Cell().add(new Paragraph(formatter.format(poTotal))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBackgroundColor(ColorConstants.YELLOW));
            productTable.addCell(cell);
            cell.setTextAlignment(TextAlignment.RIGHT);

            // Add the tables to the document
            document.add(vendorTable);
            document.add(new Paragraph("\n\n"));
            document.add(productTable);
            document.add(new Paragraph("\n\n"));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
            document.add(new Paragraph(dateFormatter.format(purchaseOrder.getPodate()))
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n\n"));
            Image qrcode = addSummaryQRCode(vendorOpt.get(), purchaseOrder);
            document.add(qrcode);
            document.close();
            
        } catch (Exception ex) {
            Logger.getLogger(PurchaseOrderPDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        // finally send stream back to the controller
        return new ByteArrayInputStream(baos.toByteArray());
    }
    
        public static Image addSummaryQRCode(Vendor vendor, PurchaseOrder po) throws Exception {
                QRCodeGenerator qrGenerator = new QRCodeGenerator();
                Locale locale = Locale.of("en", "US");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
                String summary = "Summary for Purchase Order:" + po.getId() + "\nDate:"
                                + dateFormatter.format(po.getPodate()) + "\nVendor:"
                                + vendor.getName()
                                + "\nTotal:" + formatter.format(po.getAmount());
                byte[] qrcodebin = qrGenerator.generateQRCode(summary);
                Image qrcode = new Image(ImageDataFactory.create(qrcodebin)).scaleAbsolute(100, 100)
                                .setFixedPosition(460, 60);

                return qrcode;
        }
}
