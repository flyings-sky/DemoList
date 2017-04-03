package andfans.com.demolist.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 使用懒汉式单例
 * Created by 兆鹏 on 2017/4/3.
 */
public class User implements Parcelable{
    private String id;
    private String name;
    private volatile static User user;
    public static User getUser(String id, String name){
        if(user == null){
            synchronized (User.class){
                if(user == null){
                    user = new User(id,name);
                    return user;
                }
            }
        }
        return user;
    }

    private User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id+": "+name;
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
