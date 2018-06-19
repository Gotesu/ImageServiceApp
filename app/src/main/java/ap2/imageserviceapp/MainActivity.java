package ap2.imageserviceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        Toast.makeText(this,"You just Pushed the Start button",Toast.LENGTH_SHORT).show();
    }

    public void stopService(View view) {
        Toast.makeText(this,"You just Pushed the Stop button",Toast.LENGTH_SHORT).show();
    }

}
