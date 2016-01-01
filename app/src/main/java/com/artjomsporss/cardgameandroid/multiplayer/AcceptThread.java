package com.artjomsporss.cardgameandroid.multiplayer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Used to accept connection(server) from other device(client)
 */
public class AcceptThread extends Thread {
  private final BluetoothServerSocket mmServerSocket;

  public AcceptThread(){
    // Use a temporary object that is later assigned to mmServerSocket,
    // because mmServerSocket is final
    BluetoothServerSocket tmpSSocket = null;
    try {
      // MY_UUID is the app's UUID string, also used by the client code
      BluetoothAdapter adapter = Connector.getbAdapter();
      String name = adapter.getName();
      UUID uuid = Connector.MY_UUID;

      tmpSSocket = adapter.listenUsingRfcommWithServiceRecord(name, uuid);
    } catch(IOException e){}
    mmServerSocket = tmpSSocket;
  }

  public void run(){
    BluetoothSocket socket = null;
    // Keep listening until exception occurs or a socket is returned
    while(true){
      try{
        socket = mmServerSocket.accept();
      }catch(IOException e){
        break;
      }
      // If a connection was accepted
      if (socket != null){
        // Do work to manage the connection (in a separate thread)
        if(Connector.connectedThread == null) {
          Connector.connectedThread = new ConnectedThread(socket);
          Connector.connectedThread.start();
          Log.d("CONNECTIVITY", "Connection accepted by"+Connector.getbAdapter().getName());
        }
        try {
          mmServerSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      }
    }
  }

  // Will cancel the listening socket, and cause the thread to finish
  public void cancel(){
    try {
      mmServerSocket.close();
    } catch (IOException e){
      e.printStackTrace();
    }
  }
}
