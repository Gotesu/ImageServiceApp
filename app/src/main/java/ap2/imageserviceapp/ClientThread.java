package ap2.imageserviceapp;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

public class ClientThread implements Runnable {
    private PictureSender picsend;
    private Context cont;
    public ClientThread(PictureSender sender, Context context) {
        picsend = sender;
        cont = context;
    }
    @Override
    public void run() {
        picsend.getPictures();
        picsend.sendPictures();
    }
}
