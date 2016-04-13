package entities;

/**
 * Created by shmuel on 13/03/2016.
 */
public class BooksForOrder {

    // variables
    BookSupplier bookSupplier;
    int sumOfBooks;
    boolean orderHasBeenCompleted;

    // constructor

    public BooksForOrder(BookSupplier bookSupplier, int sumOfBooks, boolean orderHasBeenCompleted) {
        this.bookSupplier = bookSupplier;
        this.sumOfBooks = sumOfBooks;
        this.orderHasBeenCompleted = orderHasBeenCompleted;
    }

    @Override
    public String toString() {
        return "BooksForOrder{" +
                "bookSupplier=" + bookSupplier +
                ", sumOfBooks=" + sumOfBooks +
                ", orderHasBeenCompleted=" + orderHasBeenCompleted +
                '}';
    }
// getters and setters


    public BookSupplier getBookSupplier() {
        return bookSupplier;
    }

    public void setBookSupplier(BookSupplier bookSupplier) {
        this.bookSupplier = bookSupplier;
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
