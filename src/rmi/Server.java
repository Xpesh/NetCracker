package rmi;

import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    static private String securityPolicyPath = "/Users/xpesh/IdeaProjects/NetCracker/src/rmi/security.policy";
    static final private String EXECUTOR_NAME = "Task";
    static private String codebaseUrl;
    static private int registryPort = 25565;
    static private String registryAddres = "127.0.0.1";
    static private int executorPort = 1604;
    static private String useCodebaseOnly = "yes";


    public static void main(String[] args) throws URISyntaxException, RemoteException {
        codebaseUrl = "file://".concat(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());//получает путь до jar файла
        System.setProperty("java.rmi.server.codebase", codebaseUrl);
        System.setProperty("java.rmi.server.useCodebaseOnly", useCodebaseOnly); // искать классы в codebase, а не в class path
        System.setProperty("java.rmi.server.logCalls", "true"); // чтоб сервер в консоль выводил инку по коннекта - запросы на поиск объекта
        System.setProperty("java.security.policy", securityPolicyPath); // путь к файлу с правами доступа
        System.setProperty("java.rmi.server.hostname", registryAddres);
        System.setSecurityManager(new SecurityManager());
        Registry registry = null;
        try {
            if (useCodebaseOnly.equals("yes"))
                registry = LocateRegistry.createRegistry(registryPort);
            else registry = LocateRegistry.getRegistry(registryPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        TaskInterfeice task = new Task(10,1);
        try {
            System.out.println("exporting object...");
            UnicastRemoteObject.exportObject(task, executorPort); // экспорт объекта - необходимо, если класс, реализующий удаленный интерфейс, не наследуется от UnicastRemoteObject
            registry.rebind(EXECUTOR_NAME, task); // бинтом объект, чтоб клиент мог найти его по имени
            System.out.println("idl-ing");
        } catch (RemoteException re) {
            System.err.println("cant export or bind object");
            re.printStackTrace();
        }

    }
}
