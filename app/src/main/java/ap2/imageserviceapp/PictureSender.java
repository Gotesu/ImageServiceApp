package ap2.imageserviceapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.io.File;

import static android.support.v4.content.ContextCompat.getSystemService;
import static java.lang.Thread.sleep;

public class PictureSender {

    private File[] pics;
    private ITCPCommunicator communicator;
    private Context cont;

    public PictureSender(Context con){
        pics = null;
        cont = con;
        communicator = new dummy(con);
    }

    public void getPictures() {
        File dcim = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (dcim != null) {
            pics = dcim.listFiles();
        }
    }

    public void sendPictures() {
        int number = pics.length;
        int i = 1;
        final int id = 1;
        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        NotificationCompat.Builder builder = new NotificationCompat.Builder(cont, "some_channel_id");
        NotificationManager NM = (NotificationManager)cont.getSystemService(Context.NOTIFICATION_SERVICE);
        NM.createNotificationChannel(notificationChannel);
        builder.setContentTitle("Picture Transfer").setContentText("Transfer in progress").setPriority(NotificationCompat.PRIORITY_LOW).setSmallIcon(R.drawable.ic_launcher_background);
        builder.setProgress(number,0,false);
        if(NM != null) {
            NM.notify(id, builder.build());
        }
        for (File pic : pics) {
            communicator.send(pic);
            builder.setProgress(number, i, false);
            NM.notify(id, builder.build());
            i++;
        }
        builder.setContentText("Download complete").setProgress(0, 0, false);
        NM.notify(id, builder.build());
    }


}
