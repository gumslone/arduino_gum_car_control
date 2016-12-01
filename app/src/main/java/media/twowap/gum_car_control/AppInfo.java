package media.twowap.gum_car_control;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import media.twowap.gum_car_controll.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AppInfo extends AppCompatActivity {
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_info);

        b1 = (Button)findViewById(R.id.button1);

        // Set up the user interaction to manually show or hide the system UI.
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AppInfo.this, MainActivity.class);
                AppInfo.this.startActivity(intent);
            }
        });
    }


}
