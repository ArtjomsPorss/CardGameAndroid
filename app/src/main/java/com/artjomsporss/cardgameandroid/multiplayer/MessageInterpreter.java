package com.artjomsporss.cardgameandroid.multiplayer;

import android.util.Log;

import com.artjomsporss.cardgameandroid.GameActivity;

import java.util.Arrays;

/**
 * Created by artash on 01/01/16.
 */
public class MessageInterpreter {

  public void interpretIncoming(byte[] buffer, int numBytes){
    Log.d("CONNECTIVITY", "Interpreting incoming");
    GameActivity.view.showTextInChat(new String(Arrays.copyOf(buffer, numBytes)));
  }

  public void interpretOutcoming(String command){
    Log.d("CONNECTIVITY", "Interpreting outcoming");
    byte[] buffer = command.getBytes();
    if(Connector.connectedThread != null){
      Connector.connectedThread.write(buffer);
    }
  }
}
