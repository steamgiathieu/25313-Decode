// Camera.java
package org.firstinspires.ftc.team25313.subsystems.vision;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Camera {
    private WebcamName webcamName;
    private VideoCapture videoCapture;

    public Camera(WebcamName webcamName) {
        this.webcamName = webcamName;
        this.videoCapture = new VideoCapture();
    }

    // Initialize the camera
    public boolean initialize() {
        // Open the camera using the hardware map and the webcamName
        boolean isOpen = videoCapture.open(0);  // Normally 0 works for webcam in FTC
        return isOpen;
    }

    // Capture a frame from the camera
    public Mat captureFrame() {
        Mat frame = new Mat();
        if (videoCapture.isOpened()) {
            videoCapture.read(frame); // Read the current frame
        }
        return frame;
    }

    // Close the camera when done
    public void close() {
        videoCapture.release();
    }
}
