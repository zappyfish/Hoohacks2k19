package com.example.syllasnap.test_activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.syllasnap.R;
import com.example.syllasnap.data.OCRData;
import com.example.syllasnap.data.OCRResponse;
import com.example.syllasnap.data.SyllabusEvent;
import com.example.syllasnap.parsing.SyllabusParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AngelTestActivity extends AppCompatActivity {

    Map<String, Integer> examplesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_test);

        examplesMap = new HashMap<>();
        examplesMap.put("1", R.raw.ex_1);
        examplesMap.put("2", R.raw.ex_2);
        examplesMap.put("3", R.raw.ex_3);
        examplesMap.put("4", R.raw.ex_4);

//
//        initTestButton(examplesMap);

    }

<<<<<<< HEAD
//    private void initTestButton(final Map<String, Integer> examplesMap) {
//        Button button = (Button) findViewById(R.id.run_angle_test);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText choice = (EditText) findViewById(R.id.ex_num);
//                int resource = examplesMap.get(choice.getText().toString());
//                OCRResponse response = createTestResponse(resource);
//                SyllabusParser parser = new SyllabusParser();
//                List<SyllabusEvent> events = parser.getSyllabusEvents(response);
//                TextView viewResult = (TextView)findViewById(R.id.ex_result);
//                String result = "";
//                for (SyllabusEvent event: events) {
//                    result += event.getLine() + "\n";
//                }
//                viewResult.setText(result);
//            }
//        });
//    }
=======
    private void initTestButton(final Map<String, Integer> examplesMap) {
        Button button = (Button) findViewById(R.id.run_angle_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText choice = (EditText) findViewById(R.id.ex_num);
                int resource = examplesMap.get(choice.getText().toString());
                OCRResponse response = createTestResponse(resource);
                SyllabusParser parser = new SyllabusParser();
                List<SyllabusEvent> events = parser.getSyllabusEvents(response, "Class Name Here");
                TextView viewResult = (TextView)findViewById(R.id.ex_result);
                String result = "";
                for (SyllabusEvent event: events) {
                    result += event.getLine() + "\n";
                }
                viewResult.setText(result);
            }
        });
    }
>>>>>>> de3df4fd9992ee5154b3808aee183e80c27e470c

    private OCRResponse createTestResponse(int id) {
        OCRResponse response = new OCRResponse();
        InputStream is = getResources().openRawResource(id);
        try {
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            int pos = 0;
            while (pos < buffer.length) {
                String text = "";
                pos++;
                while (buffer[pos] != '\"') {
                    text += (char)buffer[pos++];
                }
                pos++;
                int end = pos;
                while (((char)buffer[end++]) != '\n');
                String bounds = "";
                for (; pos < end - 1; pos++) {
                    bounds += (char)buffer[pos];
                }
                pos++;
                response.addData(createData(text, bounds));
            }
        } catch (Exception e) {
        }
        return response;
    }

    private static OCRData createData(String text, String bounds) {
        String[] bnds = bounds.split(",");
        int[] intbnds = new int[4];
        int i = 0;
        for (String bnd : bnds) {
            intbnds[i++] = Integer.parseInt(bnd);
        }
        return new OCRData(text, intbnds[0], intbnds[2], intbnds[1], intbnds[3]);
    }
}
