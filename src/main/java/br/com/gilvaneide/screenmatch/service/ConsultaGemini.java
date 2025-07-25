package br.com.gilvaneide.screenmatch.service;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsultaGemini {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String obterTraducao(String texto) {
        try {
            GenerativeModel gm = new GenerativeModel("gemini-1.5-flash-latest", apiKey);
            String prompt = "Traduza o seguinte texto para o português do Brasil: " + texto;

            GenerateContentResponse response = gm.generateContent(prompt);
            return ResponseHandler.getText(response);
        } catch (Exception e) {
            System.err.println("Erro ao chamar a API Gemini para tradução: " + e.getMessage());
            throw new RuntimeException("Falha ao traduzir texto com a API Gemini.", e);
        }
    }
}