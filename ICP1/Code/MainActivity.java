package adi.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Name;
    EditText Password;
    Button Login;
    TextView Info;
    private int counter =5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = findViewById(R.id.eTname);
        Password = findViewById(R.id.eTPassword);
        Login = findViewById(R.id.btnlogin);
        Info = findViewById(R.id.tV2);

        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                validate();
            }
        });

    }
    protected void validate() {
        if ((Name.getText().toString().equals("admin")) && Password.getText().toString().equals("12345")) {
            Intent intent=new Intent(MainActivity.this,secondactivity.class);
            startActivity(intent);

        }else{

            Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

            Info.setVisibility(View.VISIBLE);
            Info.setBackgroundColor(Color.RED);
            counter--;
            Info.setText("inValid Credentials");

            if (counter == 0){
                Login.setEnabled(false);
            }

        }

    }
}
