package br.com.gilvaneide.screenmatch.service;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaGemini {

    // O Spring irá injetar o bean do GenerativeModel configurado automaticamente
    private final GenerativeModel generativeModel;

    // Usamos o construtor para receber a dependência (melhor prática)
    @Autowired
    public ConsultaGemini(GenerativeModel generativeModel) {
        this.generativeModel = generativeModel;
    }

    public String obterTraducao(String texto) {
        try {
            String prompt = "Traduza o seguinte texto para o português do Brasil: " + texto;

            // Agora usamos o objeto que o Spring nos deu
            GenerateContentResponse response = generativeModel.generateContent(prompt);

            return ResponseHandler.getText(response);
        } catch (Exception e) {
            System.err.println("Erro ao chamar a API Gemini para tradução: " + e.getMessage());
            throw new RuntimeException("Falha ao traduzir texto com a API Gemini.", e);
        }
    }
}