package ap2.imageserviceapp;

import android.content.Context;
import android.widget.Toast;

import java.io.File;

public class dummy implements ITCPCommunicator {

    private Context context;
    public dummy(Context con) {
        context = con;
    }
    @Override
    public void connect() {
    }

    @Override
    public void send(File img) {
        String message = img.getName();
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
