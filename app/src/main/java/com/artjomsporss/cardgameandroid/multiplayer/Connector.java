package com.artjomsporss.cardgameandroid.multiplayer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.artjomsporss.cardgameandroid.GameActivity;

import java.util.Set;
import java.util.UUID;

/**
 * Idea is to research and understand how to create connectivity between two phones and send game messages to each other
 * Bluetooth tutorial from android developer website is used for this purpose
 */
public class Connector {
  public static ConnectedThread connectedThread;
  public static final MessageInterpreter messageInterpreter = new MessageInterpreter();
  private static BluetoothAdapter bAdapter;
  private static boolean paired;
  public static final UUID MY_UUID = UUID.fromString("1-23-45-6-78");

  public Connector(){
    bAdapter = BluetoothAdapter.getDefaultAdapter();
    if(bAdapter == null){
      GameActivity.view.showTextInChat("Bluetooth adapter is null");
    }else{
      GameActivity.view.showTextInChat("Bluetooth adapter is not null");
    }

    AcceptThread acceptThread = new AcceptThread();
    ConnectThread connectThread = new ConnectThread(getPairedDevice());
    acceptThread.start();
    connectThread.start();
  }

  public static boolean isPaired() {
    return paired;
  }

  public static BluetoothAdapter getbAdapter() {
    return bAdapter;
  }

  public BluetoothDevice getPairedDevice(){
    Log.d("CONNECTIVITY", "getPairedDevice");

    Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();

   return pairedDevices.size() == 1 ? pairedDevices.iterator().next() : null;
  }
}
