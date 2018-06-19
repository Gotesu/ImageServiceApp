package ap2.imageserviceapp;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import static java.lang.Thread.sleep;

public class PictureSender {

    private File[] pics;
    private ITCPCommunicator communicator;

    public PictureSender(Context con){
        pics = null;
        communicator = new dummy(con);
    }

    public void getPictures() {
        File dcim = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (dcim != null) {
            pics = dcim.listFiles();
        }
    }

    public void sendPictures() {
        for (File pic : pics) {
            communicator.send(pic);
        }
    }


}
