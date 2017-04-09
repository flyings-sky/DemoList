// IOnNewBookArrivedListener.aidl
package andfans.com.demolist.IPC;

// Declare any non-default types here with import statements
import andfans.com.demolist.Book;

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
