package br.com.gilvaneide.screenmatch.service;

import br.com.gilvaneide.screenmatch.model.Categoria;
import br.com.gilvaneide.screenmatch.model.DadosSerie;
import br.com.gilvaneide.screenmatch.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.OptionalDouble;

@Service
public class SerieMapper {

    @Autowired
    private ConsultaGemini consultaGemini; // O Spring injeta a instância

    public Serie converterDeDadosSerie(DadosSerie dados) {
        Serie serie = new Serie();

        serie.setTitulo(dados.titulo());
        serie.setTotalTemporadas(dados.totalTemporadas());
        serie.setAvaliacao(OptionalDouble.of(Double.parseDouble(dados.avaliacao())).orElse(0));
        serie.setGenero(Categoria.fromString(dados.genero().split(",")[0].trim()));
        serie.setAtores(dados.atores());
        serie.setPoster(dados.poster());

        String sinopseTraduzida = consultaGemini.obterTraducao(dados.sinopse());
        serie.setSinopse(sinopseTraduzida.trim());

        return serie;
    }
}