package com.application.iitkharagpurproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterFragment extends Fragment {

    public static final String TAG = "TAG";
    EditText personFullName,personEmailAddress,personPass,personConfPass,phoneCountryCode,phoneNumber;
    Button regsiterAccountBtn;
    Boolean isDataValid = false;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Firebase mRef;

    UserData UserInfo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_register, container, false);

        personFullName = v.findViewById(R.id.registerFullName);
        personEmailAddress = v.findViewById(R.id.registerEmail);
        personPass = v.findViewById(R.id.regsiterPass);
        personConfPass = v.findViewById(R.id.retypePass);
        phoneCountryCode = v.findViewById(R.id.countryCode);
        phoneNumber = v.findViewById(R.id.registerPhoneNumber);
        regsiterAccountBtn = v.findViewById(R.id.registerBtn);


        fAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference( "UserInfo");


        // validating the data
        validateData(personFullName);
        validateData(personEmailAddress);
        validateData(personPass);
        validateData(personConfPass);
        validateData(phoneCountryCode);
        validateData(phoneNumber);


        regsiterAccountBtn.setOnClickListener(view -> {

            CheckDataEntered();
        });
        return v;
    }

        public void validateData(EditText field){
        if(field.getText().toString().isEmpty()){
            isDataValid = false;
            field.setError("Required Field.");
        }else {
            isDataValid = true;
        }
    }


    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    public void passwordCheck(){
        if(!personPass.getText().toString().equals(personConfPass.getText().toString())){
            isDataValid = false;
            personConfPass.setError("Password Do not Match");
        }else {
            isDataValid = true;
        }

    }

     public boolean isEmailValid(EditText text) {
        CharSequence email = text.getText().toString();

       boolean validemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
       if(!validemail) {
           personEmailAddress.setError("Invalid Email");
           return validemail;
       }
        return validemail;
    }


    public void CheckDataEntered(){

        if(isEmpty(personFullName) || isEmpty(personPass) || isEmpty(personConfPass) || isEmpty(personEmailAddress) || isEmpty(phoneCountryCode) || isEmpty(phoneNumber)){
            Toast.makeText(getActivity(),"Please fill the form to complete the Registration",Toast.LENGTH_SHORT).show();
        }

        else{
            passwordCheck();
            String password =  personPass.getText().toString();
            if(isDataValid && isEmailValid(personEmailAddress) && password.length()>= 6 ){
                String name =  personFullName.getText().toString();
                String number =  phoneNumber.getText().toString();
                String mail =  personEmailAddress.getText().toString();

                addUserCredentials(mail,password,number,name);

            }
            if(password.length()<6){
                Toast.makeText(getActivity(),"Password atleast contains 6 characters",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void addUserCredentials(String mail,String Pswd,String number,String name){

        fAuth.createUserWithEmailAndPassword(mail,Pswd).addOnSuccessListener(authResult -> {
            Toast.makeText(getActivity(), "User Account is Created.", Toast.LENGTH_SHORT).show();
            addDatatoFirebase(name,number,mail);
            // send the user to verify the phone
            Intent phone = new Intent(getActivity(),ActivityTasks.class);
            startActivity(phone);
            Log.d(TAG, "onSuccess: "+"+"+phoneCountryCode.getText().toString()+phoneNumber.getText().toString() );
        }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Error !" + e.getMessage(), Toast.LENGTH_SHORT).show());

    }

    private void addDatatoFirebase(String name, String phone, String address) {

        UserData user = new UserData(name, phone, address);
        String userId = databaseReference.push().getKey();
        databaseReference.child(userId).setValue(user);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(UserInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(getActivity(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
