package ru.rsreu.lab.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vosk.Model;
import org.vosk.Recognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SpeechRecognitionService {

    private static final String MODEL_PATH = "src/main/resources/vosk-model-small-ru-0.22"; // путь к модели



    public String recognize(MultipartFile file) throws Exception {
        File original = File.createTempFile("original", ".wav");
        file.transferTo(original);

        // переменная для сбора ответа
        StringBuilder text = new StringBuilder();

        try (Model model = new Model(MODEL_PATH);
             Recognizer recognizer = new Recognizer(model, 16000);
             InputStream ais = new FileInputStream(original)) {

            byte[] buffer = new byte[4096];
            int read;

            while ((read = ais.read(buffer)) >= 0) {
                if (recognizer.acceptWaveForm(buffer, read)) {
                    String json = recognizer.getResult();
                    JSONObject obj = new JSONObject(json);
                    text.append(obj.getString("text")).append(" ");
                }
            }

            // добавляем финальный результат
            JSONObject finalObj = new JSONObject(recognizer.getResult());
            text.append(finalObj.getString("text"));
        }

        return text.toString().trim(); // возвращаем чистый текст
    }

}
