package br.com.gilvaneide.screenmatch.service;

import com.google.cloud.translate.v2.Translate;
import com.google.cloud.translate.v2.Translation;
import org.slf4j.Logger; // 1. Importe o Logger do SLF4J
import org.slf4j.LoggerFactory; // 2. Importe o LoggerFactory
import org.springframework.stereotype.Service;

@Service
public class ConsultaGoogleTranslation {

    // 3. Crie uma instância estática e final do Logger
    private static final Logger log = LoggerFactory.getLogger(ConsultaGoogleTranslation.class);

    private final Translate translateService;

    public ConsultaGoogleTranslation(Translate translateService) {
        this.translateService = translateService;
    }

    public String translateText(String text, String targetLanguage) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        try {
            Translation translation = translateService.translate(
                    text,
                    Translate.TranslateOption.targetLanguage(targetLanguage)
            );
            return translation.getTranslatedText();
        } catch (Exception e) {
            // 4. Use o logger para registrar o erro de forma estruturada
            log.error("Erro ao traduzir texto. Causa: {}", e.getMessage());
            // Retornar o texto original em caso de falha é uma estratégia segura.
            return text;
        }
    }
}