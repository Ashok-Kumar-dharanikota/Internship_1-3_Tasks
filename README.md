# Internship_1-3_Tasks

This is a 3-in-1 Android app that combines three essential features: login and registration, data save and retrieval from Firebase, and a digit classification model using TensorFlow Lite to predict digits in an image. This readme file provides an overview of the project, basic requirements to set up the project, and instructions for usage.

## Features

1. **Login and Registration**: Users can create an account or log in using their credentials. This feature ensures secure access to the app and enables personalized experiences for each user.

2. **Data Save and Retrieval from Firebase**: Users can save and retrieve data from Firebase, a real-time database provided by Google. This feature allows for seamless data synchronization across multiple devices.

3. **Digit Classification Model**: The app utilizes a TensorFlow Lite model trained to classify digits in an image. Users can capture or upload an image containing a handwritten digit, and the model will predict the digit accurately.

## Requirements

To set up and run this project, you need the following:

- Android Studio (version 3.2-7.3 or higher): The integrated development environment (IDE) for Android app development.
- Java Development Kit (JDK): Make sure you have Java installed on your machine.
- An Android device or emulator: To run the app and test its functionality.
- TensorFlow Lite: The TensorFlow Lite library is required for running the digit classification model.
- Firebase account: You need a Firebase account to set up the real-time database and enable data synchronization.

## Setup Instructions

Follow these steps to set up the project:

1. Clone the repository to your local machine using the following command:

   git clone [[https://github.com/Ashok-Kumar-dharanikota/Internship_1-3_Tasks.git](https://github.com/Ashok-Kumar-dharanikota/Internship_1-3_Tasks.git)]


2. Open Android Studio and select "Open an existing Android Studio project."

3. Navigate to the cloned repository's directory and select the project.

4. Connect your Android device to your machine or set up an emulator.

5. Ensure that your device/emulator has developer options and USB debugging enabled.

6. Build and run the project on your device/emulator.

7. Install the required dependencies for TensorFlow Lite using the following command:


8. Set up a Firebase project and create a real-time database.

9. Update the Firebase configuration details in the app to connect to your Firebase project.

10. Build and run the project again to ensure everything is set up correctly.

## Usage

Once the project is set up and running, you can use the app as follows:

1. Launch the app on your device/emulator.

2. If you are a new user, register with your credentials to create an account. If you are an existing user, log in using your credentials.

3. Explore the app's features, including data save and retrieval from Firebase.

4. To use the digit classification model:
- upload an image containing a handwritten digit.
- Submit the image to the model for prediction.
- The app will display the predicted digit.

## Additional Resources

For additional information and resources, refer to the following:

- [Android Studio Documentation](https://developer.android.com/docs)
- [TensorFlow Lite Documentation](https://www.tensorflow.org/lite)
- [Firebase Documentation](https://firebase.google.com/docs)

## License

This project is licensed under the [MIT License](LICENSE). Feel free to modify and distribute the code as per the terms of the license.
