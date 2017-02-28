// BookManager.aidl
package andfans.com.demolist;

// Declare any non-default types here with import statements
import andfans.com.demolist.Book;
interface BookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
