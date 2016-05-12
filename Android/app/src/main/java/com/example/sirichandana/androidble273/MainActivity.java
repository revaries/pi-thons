package com.example.sirichandana.androidble273;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;

import static java.lang.Byte.decode;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket tmp = null;
    final int MESSAGE_READ = 9999;
    private static android.os.Handler mHandler;

    public static BluetoothSocket mmSocket= null,socket;
    private static InputStream mmInputStream;
    private static OutputStream mmOutputStream;

    //ConnectedThread ct = new ConnectedThread(ConnectedThread.mmSocket);

    private final static int REQUEST_ENABLE_BT = 1;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final InputStream tmpIn;
        final OutputStream tmpOut;


        Button attendanceMarker = (Button) findViewById(R.id.attButton);
        assert attendanceMarker != null;

        attendanceMarker.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                final BluetoothManager bluetoothManager =
                        (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                mBluetoothAdapter = bluetoothManager.getAdapter();

// Ensures Bluetooth is available on the device and it is enabled. If not,
// displays a dialog requesting user permission to enable Bluetooth.
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }

                // Get a BluetoothSocket for a connection with the
                // given BluetoothDevice
                try {
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice("B8:27:EB:5B:C0:69");
                    /*TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    String uuid = tManager.getDeviceId();*/
                    tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("e6e7f776-11a4-4cd7-b4fd-c44ecdbfcf90"));
                    Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                    tmp = (BluetoothSocket) m.invoke(device, 1);
                    tmp.connect();
                    mmOutputStream = tmp.getOutputStream();
                    mmInputStream = tmp.getInputStream();

                    mBluetoothAdapter.cancelDiscovery();

                    if (tmp != null) {
                        Log.d("Success", "Connection Success");
                        Toast.makeText(MainActivity.this, "Connected to PI", Toast.LENGTH_SHORT).show();
                    }

                    String sample = "Siri";
                    byte[] sbyte = sample.getBytes();

                    write(sbyte);  //sending data to pi
                    Toast.makeText(getApplicationContext(), "Attendance Marked Successsfully", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    String TAG = "a";
                    Log.e(TAG, "create() failed", e);
                } catch (NoSuchMethodException e) {

                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                mmSocket = tmp;


                /*try{
                    read();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
*/

                // Unable to connect; close the socket and get out
                    try {
                        mmSocket.close();
                    } catch (IOException closeException) {
                    }
                    return;


               /* // Do work to manage the connection (in a separate thread)
                manageConnectedSocket(mmSocket);*/
            }

            /**
             * Will cancel an in-progress connection, and close the socket
             * @param bytes
             */

            public void write(byte[] bytes) {
                try {
                    mmOutputStream.write(bytes);
                } catch (IOException e) {}
            }



            // Keep listening to the InputStream until an exception occurs
            /*public void read() {
                while (true) {
                    try {
                        byte[] buffer = new byte[1024];  // buffer store for the stream
                        int bytes= 0; //bytes returned from read()
                        String data = new String(buffer, 0, bytes);

                        // Read from the InputStream
                        bytes = mmInputStream.read(buffer);
                        // Send the obtained bytes to the UI activity
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget();


                    } catch (IOException e) {
                    }
                    break;
                }
            }*/

            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) {
                }
            }




        });



    }




}
