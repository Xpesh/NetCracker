import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

    public static void main (String[] args) {
        args = new String[]{"java.lang.Math","cos","0"};
        try {
            Class classDesc = Class.forName(args[0]);
            Method[] methods = classDesc.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(args[1])) {
                    System.out.println(
                            methods[i].invoke(methods[i], Double.parseDouble(args[2]))
                    );
                    return;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            System.out.println("Error occurred while invoking");
            e.printStackTrace();
        }
    }
}
