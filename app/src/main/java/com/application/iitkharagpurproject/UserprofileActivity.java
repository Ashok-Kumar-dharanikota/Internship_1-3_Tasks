//package com.application.iitkharagpurproject;
//
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class UserprofileActivity extends AppCompatActivity {
//
//    TextView Username,USermail,Usernumber;
//
//    FirebaseDatabase firebaseDatabase;
//
//    private Firebase ref;
//
//    // creating a variable for our
//    // Database Reference for Firebase.
//    DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.userprofile_layout);
//
//        Username = findViewById(R.id.username);
//        Usernumber = findViewById(R.id.usernumber);
//        USermail = findViewById(R.id.usermail);
//
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // below line is used to get
//        // reference for our database.
//        databaseReference = firebaseDatabase.getReference("UserInfo");
//
//        // initializing our object class variable.
//
//        // calling method
//        // for getting data.
//        getdata();
//
//
//    }
//
//    private void getdata() {
//
////        ref = new Firebase("https://iit-kharagpur-project-default-rtdb.firebaseio.com/UserInfo");
////        ref.addChildEventListener(new com.firebase.client.ChildEventListener() {
////            @Override
////            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
////                String name = dataSnapshot.child("UserName").getValue(String.class);
////                String mail = dataSnapshot.child("UserContactNumber").getValue(String.class);
////                String number = dataSnapshot.child("UseremailAddress").getValue(String.class);
////
////                Username.setText(name);
////                Usernumber.setText(number);
////                USermail.setText(mail);
////
////            }
////
////            @Override
////            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {
////
////            }
////
////            @Override
////            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {
////
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////
////            }
////        });
//
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String name = snapshot.child("UserName").getValue(String.class);
//                String mail = snapshot.child("UserContactNumber").getValue(String.class);
//                String number = snapshot.child("UseremailAddress").getValue(String.class);
//
//                Username.setText(name);
//                Usernumber.setText(number);
//                USermail.setText(mail);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        // calling add value event listener method
//        // for getting the values from database.
////        databaseReference.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                // this method is call to get the realtime
////                // updates in the data.
////                // this method is called when the data is
////                // changed in our Firebase console.
////                // below line is for getting the data from
////                // snapshot of our database.
////                String name = snapshot.child("UserInfo").child("UserName").getValue(String.class);
////                String number = snapshot.child("UserInfo").child("UserContactNumber").getValue(String.class);
////                String mail = snapshot.child("UserInfo").child("UseremailAddress").getValue(String.class);
////
////                Username.setText(name);
////                Usernumber.setText(number);
////                USermail.setText(mail);
////
////                // after getting the value we are setting
////                // our value to our text view in below line.
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                // calling on cancelled method when we receive
////                // any error or we are not able to get the data.
////                Toast.makeText(UserprofileActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
////            }
////        });
//    }
//
//
//
//}

package com.application.iitkharagpurproject;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserprofileActivity extends AppCompatActivity {

    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient googleSignInClient;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    // variable for Text view.
    private TextView name,mail,number;
    private Button LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile_layout);

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("UserInfo");

        // initializing our object class variable.
        name = findViewById(R.id.username);
        mail = findViewById(R.id.usermail);
        number = findViewById(R.id.usernumber);
        LogoutBtn = findViewById(R.id.logOut);

        firebaseAuth=FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(UserprofileActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);


        // calling method
        // for getting data.
        getdata();



        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out from google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Check condition
                        if(task.isSuccessful())
                        {
                            // When task is successful
                            // Sign out from firebase
                            firebaseAuth.signOut();

                            // Display Toast
                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();

                            // Finish activity
                            finish();
                        }
                    }
                });
            }
        });





    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String value1 = snapshot.child("userName").getValue(String.class);
                String value2 = snapshot.child("userContactNumber").getValue(String.class);
                String value3 = snapshot.child("useremailAddress").getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                name.setText(value1);
                number.setText(value2);
                mail.setText(value3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(UserprofileActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
