package rmi;

import java.net.URISyntaxException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    static private String securityPolicyPath = "/Users/xpesh/IdeaProjects/NetCracker/src/rmi/security.policy";
    static final private String EXECUTOR_NAME = "Task";
    static private String codebaseUrl;
    static private int registryPort = 25565;
    static private String registryAddres = "127.0.0.1";
    static private int executorPort = 1604;
    static private String useCodebaseOnly = "yes";

    public static void main(String[] args) throws URISyntaxException {
        codebaseUrl = "file://".concat(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());//получает путь до jar файла
        System.setProperty("java.rmi.server.codebase", codebaseUrl);
        System.setProperty("java.rmi.server.useCodebaseOnly", useCodebaseOnly);
        System.setProperty("java.security.policy", securityPolicyPath);

        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(registryAddres, registryPort); // получает ссылку на реестр
        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
        if (registry != null) {
            try {
                Remote remote = registry.lookup(EXECUTOR_NAME); // получаем ссылку на удаленный объект
                test((TaskInterfeice) remote);
            } catch (RemoteException re) {
                System.err.println("something unbelievable happens");
                re.printStackTrace();
            } catch (Exception e) {
                System.err.println("user input err");
                e.printStackTrace();
            }
        }
    }

    public static void test(TaskInterfeice task) throws RemoteException {
        System.out.format("X = %d\n",task.getX());
        System.out.format("Y = %d\n",task.getY());
        System.out.format("(x*PI)^y = %f\n",task.function());
    }
}
