import java.lang.reflect.Field;
import java.util.function.Function;

public class LambdaQuery<T> {
    private Class<T> clazz;

    public LambdaQuery(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getFieldName(Function<T, ?> field) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (field.apply(null).equals(f.get(clazz.newInstance()))) {
                    return f.getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 示例用法
    public static void main(String[] args) {
        LambdaQuery<User> query = new LambdaQuery<>(User.class);

        String fieldName = query.getFieldName(User::getName);
        System.out.println("Field Name for 'getName': " + fieldName);

        fieldName = query.getFieldName(User::getAge);
        System.out.println("Field Name for 'getAge': " + fieldName);
    }
}

class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
