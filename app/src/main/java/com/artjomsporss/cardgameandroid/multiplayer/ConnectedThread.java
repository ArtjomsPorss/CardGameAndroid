package com.artjomsporss.cardgameandroid.multiplayer;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Used to send and receive messages between devices when connection has been established
 */
public class ConnectedThread extends Thread {
  private final BluetoothSocket mmSocket;
  private final InputStream mmInStream;
  private final OutputStream mmOutStream;

  public ConnectedThread(BluetoothSocket socket){
    Log.d("CONNECTIVITY", "ConnectedThread Constructor called");
    Log.d("CONNECTIVITY", "ConnectedThread socket=" + socket);
    mmSocket = socket;
    InputStream tmpIn = null;
    OutputStream tmpOut = null;

    // Get the inpu and output streams, using temp objects because
    // member streams are final
    try {
      tmpIn = socket.getInputStream();
      tmpOut = socket.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }

    mmInStream = tmpIn;
    mmOutStream = tmpOut;
  }

  public void run() {
    Log.d("CONNECTIVITY", "ConnectedThread run()");
    byte[] buffer = new byte[1024]; // buffer store for the stream
    int bytes; // bytes returned from read()

    // Keep listening to the InputStream until an exception occurs
    while(true){
      try{
        //Log.d("CONNECTIVITY", "Start to read incoming");
        // Read from the InputStream
        bytes = mmInStream.read(buffer);
        //Log.d("CONNECTIVITY", "Read incoming");
        // Send the obtained bytes to the UI activity
        Connector.messageInterpreter.interpretIncoming(buffer, bytes);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // Call this from the main activity to send data to the remote device
  public void write(byte[] bytes){
    try{
      mmOutStream.write(bytes);
      Log.d("CONNECTIVITY", "Wrote outcoming");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Call this from the main activity to shutdown the connection
  public void cancel() {
    try{
      mmSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
