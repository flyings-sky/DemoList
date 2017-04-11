package andfans.com.demolist.Data;

import android.app.Service;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 使用懒汉式单例
 * Created by 兆鹏 on 2017/4/3.
 */
public class User implements Parcelable,Serializable{
    private int id;
    private String name;
    private boolean sex;



    private volatile static User user;
//    public static User getUser(String id, String name){
//        if(user == null){
//            synchronized (User.class){
//                if(user == null){
//                    user = new User(id,name);
//                    return user;
//                }
//            }
//        }
//        return user;
//    }


    public User(int id, String name, boolean sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return id+": "+name;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        sex = in.readByte() != 0;
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
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte)(sex ? 1:0));
    }
}
