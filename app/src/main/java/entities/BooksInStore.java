package entities;

/**
 * Created by shmuel on 29/03/2016.
 */
public class BooksInStore {

    // variables
    Book book;
    int amount;
    int booksInStoreID;

    // constructor
    public BooksInStore(Book book, int amount) {
        this.book = book;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BooksInStore{" +
                "amount=" + amount +
                ", book=" + book +
                ", booksInStoreID=" + booksInStoreID +
                '}';
    }

    // getters and setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBooksInStoreID() {
        return booksInStoreID;
    }

    public void setBooksInStoreID(int booksInStoreID) {
        this.booksInStoreID = booksInStoreID;
    }
}
