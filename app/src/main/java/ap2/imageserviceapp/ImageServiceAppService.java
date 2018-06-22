package ap2.imageserviceapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class ImageServiceAppService extends Service{
    private BroadcastReceiver yourReceiver;
    private PictureSender picSender;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * onCreate method, init of wifi checking, everytime wifi is on we tranfer pictures
     */
    @Override
    public void onCreate() {
        super.onCreate();
        picSender = new PictureSender(this);
        //the following code was taken from exercise 13, it has some deprecation from api 28 on but
        //i preferred using the code we were given formally in the exercise
        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        theFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.yourReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        //get the different network states
                        if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            startTransfer();            // Starting the Transfer
                        }
                    }
                }
            }
        };
        this.registerReceiver(this.yourReceiver, theFilter);
    }

    /**
     * sending pictures
     */
    private void startTransfer() {
        //sending pictures using a different thread
        Runnable r = new ClientThread(picSender,this);
        new Thread(r).start();
    }

    /**
     * on start command of the function
     * @param intent intent
     * @param flag flag
     * @param startId start id
     * @return start sticky
     */
    public int onStartCommand(Intent intent, int flag, int startId) {
        //making the message
        Toast.makeText(this,"Service starting...", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    /**
     * on destroy
     */
    public void onDestroy() {
        //making the message
        Toast.makeText(this,"Service ending...", Toast.LENGTH_SHORT).show();
    }



}
