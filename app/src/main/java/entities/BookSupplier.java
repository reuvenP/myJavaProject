package entities;

/**
 * Created by shmuel on 13/03/2016.
 */
public class BookSupplier {

    // variables
    Supplier supplier;
    Book book;
    float price;
    int amount;


    // constructor
    public BookSupplier(Supplier supplier, Book book, float price, int amount) {
        this.supplier = supplier;
        this.book = book;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BookSupplier{" +
                "supplier=" + supplier +
                ", book=" + book +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    // getters and setters
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
