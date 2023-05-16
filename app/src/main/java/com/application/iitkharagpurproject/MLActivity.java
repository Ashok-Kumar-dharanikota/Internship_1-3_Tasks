package com.application.iitkharagpurproject;


import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MLActivity extends AppCompatActivity {
    private Interpreter tflite;
    private ImageView imageView;
    private TextView predictionTextView , TrainModel;
    private TextView accuracyTextView;
    private Button chooseImageButton;
    private Button classifyButton;
    private Bitmap image;
    TextView btLogout;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;



    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ml_layout);

        // Initialize TensorFlow Lite model
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get references to views
        imageView = findViewById(R.id.image_view);
        predictionTextView = findViewById(R.id.prediction_text_view);
        accuracyTextView = findViewById(R.id.accuracy_text_view);
        chooseImageButton = findViewById(R.id.choose_image_button);
        classifyButton = findViewById(R.id.classify_button);
        btLogout=findViewById(R.id.logoutM);

        firebaseAuth=FirebaseAuth.getInstance();

        googleSignInClient = GoogleSignIn.getClient(MLActivity.this
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


        // Set up choose image button
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open image picker
                // ...
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        // Set up classify button
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null) {
                    // Preprocess the image
                    float[] input = getPreprocessedImage(image);

                    // Classify the image
                    float[][] output = new float[1][10];
                    tflite.run(input, output);

                    // Find the class with the highest confidence
                    int classIndex = 0;
                    float maxConfidence = output[0][0];
                    for (int i = 1; i < 10; i++) {
                        if (output[0][i] > maxConfidence) {
                            classIndex = i;
                            maxConfidence = output[0][i];
                        }
                    }

                    // Display the prediction
                    predictionTextView.setText(String.valueOf(classIndex));
                    accuracyTextView.setText(String.valueOf(maxConfidence));
                }
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image URI
            Uri imageUri = data.getData();

            try {
                // Convert the image URI to a Bitmap
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Display the image
                imageView.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private float[] getPreprocessedImage(Bitmap image) {
        // Resize the image
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, 28, 28, true);

        // Convert the image to a float array
        int width = resizedImage.getWidth();
        int height = resizedImage.getHeight();
        int[] pixels = new int[width * height];
        resizedImage.getPixels(pixels, 0, width, 0, 0, width, height);

        float[] imageData = new float[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            imageData[i] = (pixels[i] & 0xff) / 255.0f;
        }

        return imageData;
    }





    private MappedByteBuffer loadModelFile() throws IOException {
        // Open the model file from the assets folder
        AssetFileDescriptor fileDescriptor = getAssets().openFd("mnist_ML_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }

}
