package com.application.iitkharagpurproject;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



import java.util.concurrent.Executor;


public class LoginFragment extends Fragment implements View.OnClickListener {

    SignInButton btSignIn;
    Button Login,PhonenumberVerfification;
    EditText Mail,Password;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Login = view.findViewById(R.id.button);
        PhonenumberVerfification = view.findViewById(R.id.vpnumber);

        firebaseAuth = FirebaseAuth.getInstance();

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in
            // redirect to profile activity
            startActivity(new Intent(getActivity(), MLActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }



        Mail = view.findViewById(R.id.email);
        Password = view.findViewById(R.id.password);

        Login.setOnClickListener(this);
        PhonenumberVerfification.setOnClickListener(this);
        PhonenumberVerfification.setOnClickListener(this);

        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                verify();
                break;

            case R.id.vpnumber:
                Intent i = new Intent(getActivity(),LoginActivity.class);
                startActivity(i);
                break;
        }


    }





    public void verify() {

        if(isEmpty(Mail) || isEmpty(Password)){
            Toast.makeText(getActivity(),"Please enter your email and password to login",Toast.LENGTH_SHORT).show();
        }


        else {
            String email = Mail.getText().toString();
            String password = Password.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                        "Login successful!!",
                                                        Toast.LENGTH_LONG)
                                                .show();


                                        // if sign-in is successful
                                        // intent to home activity
                                        Intent intent
                                                = new Intent(getActivity(),
                                                ActivityTasks.class);
                                        startActivity(intent);
                                    }

                                    else {

                                        // sign-in failed
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                        "Login failed!!",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                    }
                                }
                            });


        }



    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }



}