package Pojos;

public class Transaction {

    private Integer id;
    private Integer itemId;
    private Integer quantity;
    private String type;
    private String date;
    private Integer userId;
    private Boolean finished;

    public Transaction() {
    }

    public Transaction(Integer id, Integer itemId, Integer quantity, String type, String date, Integer userId, Boolean finished) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        this.type = type;
        this.date = date;
        this.userId = userId;
        this.finished = finished;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", userId=" + userId +
                ", finished=" + finished +
                '}';
    }
}
