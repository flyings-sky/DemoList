package andfans.com.demolist;

/**
 *
 * Created by 兆鹏 on 2017/2/2.
 */
public class ClassAndName {
    //用于保存activity的字节码
    private Class<?> aClass;
    //用于保存名字
    private String name;

    public ClassAndName(Class<?> aClass, String name) {
        this.aClass = aClass;
        this.name = name;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
