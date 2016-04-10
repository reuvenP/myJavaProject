package entities;

/**
 * Created by shmuel on 13/03/2016.
 */
public class BookSupplier {

    // variables
    Supplier supplier;
    Book book;
    float price;


    // constructor
    public BookSupplier(Supplier supplier, Book book, float price) {

        this.supplier = supplier;
        this.book = book;
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookSupplier{" +
                "book=" + book +
                ", supplier=" + supplier +
                ", price=" + price +
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


}
