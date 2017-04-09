// BookManager.aidl
package andfans.com.demolist;

// Declare any non-default types here with import statements
import andfans.com.demolist.Book;
import andfans.com.demolist.IPC.IOnNewBookArrivedListener;

interface BookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
