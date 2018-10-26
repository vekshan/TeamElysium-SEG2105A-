package com.example.vekshan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogIn;
    private EditText edit_txt_Email;
    private EditText edit_txt_Password;
    private TextView txt_view_Register;
    private FirebaseAuth firebaseAuth;
    private Spinner spinnerChoice;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //Intializing views
        btnLogIn = findViewById(R.id.btnLogIn);
        txt_view_Register =findViewById(R.id.txt_view_Register);

        edit_txt_Email= findViewById(R.id.edit_txt_Email);
        edit_txt_Password = findViewById(R.id.edit_txt_Password);
        spinnerChoice =findViewById(R.id.spinnerChoice);


        //spinner
        adapter =ArrayAdapter.createFromResource(this,R.array.accountTypes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChoice.setAdapter(adapter);

        //Setting listeners
        btnLogIn.setOnClickListener(this);
        txt_view_Register.setOnClickListener(this);







    }

    @Override
    public void onClick(View view){
        if (view == btnLogIn){
            //call login method
            login();
        }
        if (view == txt_view_Register) {
            //open RegisterActivity which is the signup screen
            startActivity(new Intent(this, Register.class));
        }


    }

    private void login() {
        final String email = edit_txt_Email.getText().toString().trim();
        final String password = edit_txt_Password.getText().toString().trim();
        final String accountType = spinnerChoice.getSelectedItem().toString().trim();

        data = FirebaseDatabase.getInstance().getReference().child(accountType).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //implement code to see required login screen

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Successfully logged in!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), WelcomeScreenActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Log in Failed! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
