package com.example.syllasnap.ocr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.Vertex;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.protobuf.ByteString;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.*;


public class OCRManager {

    // TODO: REMOVE THIS! THIS IS TERRIBLE PRACTICE
    private static final String API_KEY = "AIzaSyB6e44sijZP2uJAoMuf34KYIu6pTLoe_YY";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";

    private static OCRManager sInstance;

    // TODO: fill in constructor as appropriate
    private OCRManager() {

    }

    public static synchronized OCRManager getInstance() {
        if (sInstance == null) {
            sInstance = new OCRManager();
        }
        return sInstance;
    }

    public OCRResponse makeOCRRequest(Bitmap croppedSyllabus, Activity activity, OCRCallback callback) throws Exception {
        OCRResponse requestResult = new OCRResponse();

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, BatchAnnotateImagesResponse> labelDetectionTask = new LableDetectionTask(prepareAnnotationRequest(croppedSyllabus, activity), callback);
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d("ocr", "failed to make API request because of other IOException " +
                    e.getMessage());
        }


        return requestResult;
    }

    private Vision.Images.Annotate prepareAnnotationRequest(final Bitmap bitmap, final Activity activity) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = activity.getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(activity.getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType(com.google.cloud.vision.v1.Feature.Type.TEXT_DETECTION.toString());
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);

        return annotateRequest;
    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, BatchAnnotateImagesResponse> {
        private Vision.Images.Annotate mRequest;
        private final WeakReference<OCRCallback> mOCRWeakCallback;

        LableDetectionTask(Vision.Images.Annotate annotate, OCRCallback callback) {
            mRequest = annotate;
            mOCRWeakCallback = new WeakReference<>(callback);
        }

        @Override
        protected BatchAnnotateImagesResponse doInBackground(Object... params) {
            try {
                return mRequest.execute();

            } catch (GoogleJsonResponseException e) {
                Log.d("ocr", "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d("ocr", "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(BatchAnnotateImagesResponse response) {
            if (response != null) {
                OCRResponse requestResult = new OCRResponse();
                List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();

                if (labels != null) {
                    for (EntityAnnotation annotation : labels) {
                        if (annotation.getDescription() != null) {
                            String text = annotation.getDescription();
                            List<Vertex> vertices = annotation.getBoundingPoly().getVertices();
                            if (vertices != null && vertices.size() == 4) {
                                int left = -1, right = -1, top = -1, bottom = -1;
                                try {
                                    for (Vertex vertex : vertices) {
                                        if (left == -1) {
                                            left = vertex.getX();
                                            right = left;
                                            top = vertex.getY();
                                            bottom = top;
                                        } else {
                                            if (vertex.getX() < left) {
                                                left = vertex.getX();
                                            }
                                            if (vertex.getX() > right) {
                                                right = vertex.getX();
                                            }
                                            if (vertex.getY() < top) {
                                                top = vertex.getY();
                                            }
                                            if (vertex.getY() > bottom) {
                                                bottom = vertex.getY();
                                            }
                                        }
                                    }

                                    OCRData data = new OCRData(text, left, right, top, bottom);
                                    if (!exists(data, requestResult)) {
                                        requestResult.addData(data);
                                    }
                                } catch (NullPointerException np) {

                                }
                            }
                        }
                    }
                    mOCRWeakCallback.get().onOCRComplete(requestResult);
                }
            }
        }
    }

    private static boolean exists(OCRData data, OCRResponse currentData) {
        for (int i = 0; i < currentData.getNumData(); i++) {
            if (data.getText().equals(currentData.getData(i).getText())) {
                return true;
            }
        }
        return false;
    }

    public interface OCRCallback {

        void onOCRComplete(OCRResponse response);
    }
}
