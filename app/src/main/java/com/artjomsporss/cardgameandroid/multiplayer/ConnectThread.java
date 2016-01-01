package com.artjomsporss.cardgameandroid.multiplayer;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * Used to connect as client to other device(server)
 */
public class ConnectThread extends Thread{
  private final BluetoothSocket mmSocket;
  private final BluetoothDevice mmDevice;

  public ConnectThread(BluetoothDevice device){
    // Use a temporary object that is later assigned to mmSocket,
    // because mmSocket is final
    BluetoothSocket tmp = null;
    mmDevice = device;

    // Get a BluetoothSocket to connect with the given BluetoothDevice
    try{
      // MY_UUID is the app's UUID string, also used by the server code
      tmp = device.createRfcommSocketToServiceRecord(Connector.MY_UUID);
    } catch (IOException e) {
      e.printStackTrace();
    }
    mmSocket = tmp;
  }

  public void run(){
    // Cancel discovery because it will slow down the connection
    Connector.getbAdapter().cancelDiscovery();

    try{
      // Connect the device through the socket. This will block
      // Until it succeeds or throws an exception
      mmSocket.connect();
    } catch (IOException e) {
      e.printStackTrace();
      // Unable to connect; close the socket and get out
      try{
        mmSocket.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      return;
    }


    // Do work to manage the connection (in a separate thread)
    if(Connector.connectedThread == null) {
      Connector.connectedThread = new ConnectedThread(mmSocket);
      Connector.connectedThread.start();
      Log.d("CONNECTIVITY", "Connection attempted by" + Connector.getbAdapter().getName());
    }
  }

  // Will cancel an in-progress connection, and close the socket
  public void cancel(){
    try{
      mmSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
