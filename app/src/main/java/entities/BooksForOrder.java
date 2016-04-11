package entities;

/**
 * Created by shmuel on 13/03/2016.
 */
public class BooksForOrder {

    // variables
    //TODO: replace "supplier" and "book" with "bookSupplier"
    Supplier supplier;
    Book book;
    int sumOfBooks;
    boolean orderHasBeenCompleted;

    // constructor
    public BooksForOrder(Supplier supplier, Book book, int sumOfBooks, boolean orderHasBeenCompleted) {
        this.supplier = supplier;
        this.book = book;
        this.sumOfBooks = sumOfBooks;
        this.orderHasBeenCompleted = orderHasBeenCompleted;
    }

    @Override
    public String toString() {
        return "BooksForOrder{" +
                "book=" + book +
                ", supplier=" + supplier +
                ", sumOfBooks=" + sumOfBooks +
                ", orderHasBeenCompleted=" + orderHasBeenCompleted +
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

    public int getSumOfBooks() {
        return sumOfBooks;
    }

    public void setSumOfBooks(int sumOfBooks) {
        this.sumOfBooks = sumOfBooks;
    }

    public boolean isOrderHasBeenCompleted() {
        return orderHasBeenCompleted;
    }

    public void setOrderHasBeenCompleted(boolean orderHasBeenCompleted) {
        this.orderHasBeenCompleted = orderHasBeenCompleted;
    }
}
