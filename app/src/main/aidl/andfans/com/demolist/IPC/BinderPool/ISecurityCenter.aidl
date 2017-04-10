// ISecurityCenter.aidl
package andfans.com.demolist.IPC.BinderPool;

// Declare any non-default types here with import statements

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
