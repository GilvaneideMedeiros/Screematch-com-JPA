package br.com.gilvaneide.screenmatch.service;

import com.gemini.api.client.Client;
import com.gemini.api.client.models.GenerateContentResponse;

public class ConsultaGemini {
    public static String obterTraducao(String texto){
        String modelo = "gemini-2.0-flash-lite"; // Pode modificar a versão se desejar
        String prompt = "Traduz o seguinte texto para português brasileiro: " + texto;

        Client cliente = new Client.Builder().apiKey("AIzaSyBcxDe4QFsPHvVa5zY_miO1WA6P4KGnsdQ").build();

        try{
            GenerateContentResponse response = client.models().generateContent(
                    modelo,
                    prompt,
                    null); // parâmetro de configurações adicionais

            if(!response.text().isEmpty()) {
                return response.text();
            }
        }catch (Exception e){
            System.err.println("Erro ao chamar a API Gemini para tradução: " + e.getMessage());
        }

        return null;
    }
}