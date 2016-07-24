package media.twowap.gum_car_control;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import android.view.View.OnTouchListener;

import media.twowap.gum_car_controll.R;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT=1;
    ImageButton b1,b2,b3,b4,b5,b6,b7,b8;
    TextView t1,t2;
    SeekBar s1;
    int b1_status=0;
    int b2_status=0;
    int b3_status=0;
    int b4_status=0;
    char bluetooth_action = 'S';
    char bluetooth_lights = 'w';
    char bluetooth_sound = 'v';
    char bluetooth_track = 'x';
    String address = null;
    private ProgressDialog progress;
    private BluetoothAdapter BA;
    private BluetoothSocket BS = null;
    private boolean isBtConnected = false;
    private Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(ImageButton)findViewById(R.id.imageButton1);
        b2=(ImageButton)findViewById(R.id.imageButton2);
        b3=(ImageButton)findViewById(R.id.imageButton3);
        b4=(ImageButton)findViewById(R.id.imageButton4);
        b5=(ImageButton)findViewById(R.id.imageButton5);
        b6=(ImageButton)findViewById(R.id.imageButton6);
        b7=(ImageButton)findViewById(R.id.imageButton7);
        b8=(ImageButton)findViewById(R.id.imageButton8);



        t1=(TextView)findViewById(R.id.textView1);
        t2=(TextView)findViewById(R.id.textView2);
        s1=(SeekBar)findViewById(R.id.seekBar1);

        //receive the address of the bluetooth device
        Intent newint = getIntent();

        //Intent newint = new Intent(this, devicelist.class);
        address = newint.getStringExtra(devicelist.EXTRA_ADDRESS);


        //msg(address);
        if(address!=null) {

            new ConnectBT().execute(); //Call the class to connect
        }

        //commands to be sent to bluetooth
        b1.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    b1_status=1;
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    b1_status=0;
                }
                send_movement_action();
                return false;
            }

        });
        b2.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    b2_status=1;
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    b2_status=0;
                }
                send_movement_action();
                return false;
            }

        });

        b3.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    b3_status=1;
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    b3_status=0;
                }
                send_movement_action();
                return false;
            }

        });

        b4.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    b4_status=1;
                }
                if (event.getAction()==MotionEvent.ACTION_UP){
                    b4_status=0;
                }
                send_movement_action();
                return false;
            }

        });

        //commands to be sent to bluetooth


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Disconnect();
                t1.setText("Disconnected");
                b5.setImageResource(R.drawable.disconnected);
                setContentView(R.layout.activity_devicelist);
                Intent intent = new Intent(MainActivity.this, devicelist.class);
                MainActivity.this.startActivity(intent);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (BS!=null)
                {
                    try
                    {
                        if(bluetooth_lights=='w')
                        {
                            bluetooth_lights = 'W';
                            b6.setImageResource(R.drawable.light_on);
                        }
                        else
                        {
                            bluetooth_lights = 'w';
                            b6.setImageResource(R.drawable.light_off);
                        }
                        BS.getOutputStream().write(bluetooth_lights);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }
                else
                {
                    msg("Not connected");
                }

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (BS!=null)
                {
                    try
                    {
                        if(bluetooth_sound=='v')
                        {
                            bluetooth_sound = 'V';
                            b7.setImageResource(R.drawable.sound_on);
                        }
                        else
                        {
                            bluetooth_sound = 'v';
                            b7.setImageResource(R.drawable.sound_off);
                        }
                        BS.getOutputStream().write(bluetooth_sound);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }
                else
                {
                    msg("Not connected");
                }
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (BS!=null)
                {
                    try
                    {
                        if(bluetooth_track=='x')
                        {
                            bluetooth_track = 'X';
                            b8.setImageResource(R.drawable.track_on);
                        }
                        else
                        {
                            bluetooth_track = 'x';
                            b8.setImageResource(R.drawable.track_off);
                        }
                        BS.getOutputStream().write(bluetooth_track);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }
                else
                {
                    msg("Not connected");
                }
            }
        });

        s1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t2.setText("Speed: "+String.valueOf(progress));
                if (BS!=null)
                {
                    try
                    {
                        BS.getOutputStream().write(String.valueOf(progress).getBytes());
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }
                else
                {
                    msg("Not connected");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void Disconnect()
    {
        if (BS!=null) //If the btSocket is busy
        {
            try
            {
                BS.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void send_movement_action()
    {
        if(b1_status==1 && b3_status==1)
        {
            bluetooth_action = 'G'; //Forward left
        }
        else if(b1_status==1 && b4_status==1)
        {
            bluetooth_action = 'I'; //Forward right
        }
        else if(b1_status==1)
        {
            bluetooth_action = 'F';  //Forward
        }
        else if(b2_status==1 && b3_status==1)
        {
            bluetooth_action = 'H'; // Backward LEFT
        }
        else if(b2_status==1 && b4_status==1)
        {
            bluetooth_action = 'J'; // Backward RIGHT
        }
        else if(b2_status==1)
        {
            bluetooth_action = 'B'; //Backward
        }
        else if(b3_status==1)
        {
            bluetooth_action = 'L'; //Left
        }
        else if(b4_status==1)
        {
            bluetooth_action = 'R'; //Left
        }
        else
        {
            bluetooth_action = 'S';
        }

        if (BS!=null)
        {
            try
            {
                BS.getOutputStream().write(bluetooth_action);
                //BS.getOutputStream().write("F".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }


    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (BS == null || !isBtConnected)
                {
                    BA = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = BA.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    BS = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    BS.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                t1.setText("Connected to: "+address);
                b5.setImageResource(R.drawable.connected);
                isBtConnected = true;
            }
            progress.dismiss();
        }





    }







}


