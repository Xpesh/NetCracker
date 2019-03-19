package rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaskInterfeice extends Remote, Serializable {
    int getX() throws RemoteException;
    int getY() throws RemoteException;
    double function() throws RemoteException;
}
