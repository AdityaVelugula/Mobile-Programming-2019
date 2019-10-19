package adi.example.loginapp;

import android.content.Intent;
        import android.os.Bundle;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.android.material.snackbar.Snackbar;

        import androidx.appcompat.app.AppCompatActivity;
        import android.widget.Button;
        import androidx.appcompat.widget.Toolbar;

        import android.view.View;

public class secondactivity extends AppCompatActivity {
    Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);

        logOut = findViewById(R.id.btnLogOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent=new Intent(secondactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

}