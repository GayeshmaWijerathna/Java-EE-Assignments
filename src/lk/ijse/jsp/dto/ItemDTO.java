package lk.ijse.jsp.dto;

public class ItemDTO {
    private String code;
    private String description;
    private int qtyOnHand;
    private double unitPrice;


    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String itemName, int itemQty, double itemPrice) {
        this.code = itemCode;
        this.description = itemName;
        this.qtyOnHand = itemQty;
        this.unitPrice = itemPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
