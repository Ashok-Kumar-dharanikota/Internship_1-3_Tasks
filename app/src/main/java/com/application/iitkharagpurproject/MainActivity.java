package com.application.iitkharagpurproject;

//import androidx.appcompat.app.ActionBar;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> imagelist;
    RecyclerView recyclerView;
    Button ChooseButton,UploadButton,RetrieveImageBtn;
    Uri imageuri;
    ImageView FirebaseImage;
    StorageReference storagereference;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ImageAdapter adapter;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    TextView btLogout;

    ActivityResultLauncher<String> mGetContent;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChooseButton = findViewById(R.id.Choosebtn);
        UploadButton = findViewById(R.id.uploadbtn);
        FirebaseImage = findViewById(R.id.imageView);
        RetrieveImageBtn = findViewById(R.id.Rtvimage);
        imagelist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new ImageAdapter(imagelist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        btLogout=findViewById(R.id.logout);


        firebaseAuth=FirebaseAuth.getInstance();

        googleSignInClient = GoogleSignIn.getClient(MainActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);


        btLogout.setOnClickListener(new View.OnClickListener() {
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


        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();
            }
        });

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadimage();
            }
        });

        RetrieveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference listRef = FirebaseStorage.getInstance().getReference().child("Images");
                listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for(StorageReference file:listResult.getItems()){
                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // adding the url in the arraylist
                                    imagelist.add(uri.toString());
                                    Log.e("Itemvalue",uri.toString());
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                });
                RetrieveImage();
            }
        });

    }

    private void RetrieveImage(){

        if(i==0){

            ChooseButton.setVisibility(View.GONE);
            UploadButton.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            i++;

        }
        else{
            recyclerView.setVisibility(View.GONE);
            ChooseButton.setVisibility(View.VISIBLE);
            UploadButton.setVisibility(View.VISIBLE);
            i--;
        }

    }

    private void Uploadimage(){

        try{
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File.....");
            progressDialog.show();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
            Date now = new Date();
            String filename = formatter.format(now);

            storagereference = FirebaseStorage.getInstance().getReference("Images/"+filename);
            storagereference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    FirebaseImage.setImageURI(null);
                    Toast.makeText(MainActivity.this,"Successfully uploaded",Toast.LENGTH_LONG).show();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Failed to upload...",Toast.LENGTH_LONG).show();

                }
            });

        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"Select any image to upload!",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private  void ChooseImage(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null){
            imageuri = data.getData();
            FirebaseImage.setImageURI(imageuri);

        }
    }


}

