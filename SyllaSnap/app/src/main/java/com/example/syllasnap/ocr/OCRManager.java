package com.example.syllasnap.ocr;

import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Vertex;
import com.google.protobuf.ByteString;

import java.util.*;


public class OCRManager {

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

    /**
     * makeOCRRequest(byte[] syllabusData)
     *
     * @param syllabusData - a byte[] containing the image of syllabus
     * @return OCRRequest ... containg
     */
    public OCRResponse makeOCRRequest(byte[] syllabusData) throws Exception {
        OCRResponse requestResult = new OCRResponse();
        List<AnnotateImageRequest> requests = new ArrayList<>();
        ByteString imgBytes = ByteString.copyFrom(syllabusData);

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            requests.add(request);

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    return null;
                }
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    if (annotation.getDescription() != null) {
                        String text = annotation.getDescription();
                        List<Vertex> vertices = annotation.getBoundingPoly().getVerticesList();

                        int left = vertices.get(0).getX();
                        int top = vertices.get(1).getY();
                        int right = vertices.get(2).getX();
                        int bottom = vertices.get(3).getY();

                        OCRData data = new OCRData(text, left, top, right, bottom);
                        requestResult.addData(data);

                    }
                }
            }

        }

        return requestResult;
    }
}
