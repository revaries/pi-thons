package com.example.sirichandana.androidble273;

import android.bluetooth.BluetoothSocket;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.logging.LogRecord;
import android.os.Handler;

/**
 * Created by Sirichandana on 5/8/2016.
 */
public class ConnectedThread extends Thread {
    private static final Object MESSAGE_READ = 0 ;
    public static BluetoothSocket mmSocket;
    private static InputStream mmInStream;
    private static OutputStream mmOutStream;
    private static android.os.Handler mHandler;

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;


        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
          // buffer store for the stream
        //int bytes; //bytes returned from read()



        // Keep listening to the InputStream until an exception occurs
           while (true) {
                try {
                    byte[] buffer = new byte[1024];
                    // Read from the InputStream
                    int bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage((Integer) MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */

    public static void write(Byte bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    public final Message obtainMessage(int what, int arg1, int arg2, Object obj) {
        return null;
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}

