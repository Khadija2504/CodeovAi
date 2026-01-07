package main.java.com.workpal.model;

public class Payment {
    private int id;
    private int price;
    private String cardInfo;
    private String payment_type;
    private int member_id;

    public Payment(int id, int price, String cardInfo, String payment_type, int member_id) {
        this.id = id;
        this.price = price;
        this.cardInfo = cardInfo;
        this.payment_type = payment_type;
        this.member_id = member_id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }
    
    public String getPayment_type() {
        return payment_type;
    }
    
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
    
    public int getMember_id() {
        return member_id;
    }
    
    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", price=" + price +
                ", cardInfo='" + cardInfo + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", member_id=" + member_id +
                '}';
    }
}
