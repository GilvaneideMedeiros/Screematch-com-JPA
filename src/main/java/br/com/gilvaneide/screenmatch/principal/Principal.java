package br.com.gilvaneide.screenmatch.principal;

import br.com.gilvaneide.screenmatch.model.DadosSerie;
import br.com.gilvaneide.screenmatch.model.DadosTemporada;
import br.com.gilvaneide.screenmatch.model.Serie;
import br.com.gilvaneide.screenmatch.service.ConsumoApi;
import br.com.gilvaneide.screenmatch.service.ConverteDados;
import br.com.gilvaneide.screenmatch.service.SerieMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=9de8e5d7";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieMapper serieMapper;

    public Principal(SerieMapper serieMapper) {
        this.serieMapper = serieMapper;
    }

    public void exibeMenu() {
        System.out.println("\n******************************");
        System.out.println("*  Bem vindo ao ScreenMatch  *");
        System.out.println("******************************");
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

//    @Autowired
//    private SerieMapper serieMapper;

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        dadosSeries.add(dados);

        Serie serie = serieMapper.converterDeDadosSerie(dados);
        System.out.println("\n*** Série encontrada ***");
        System.out.println(serie);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca\n");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie
                    .titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas() {
        System.out.println("Listagem de séries buscadas\n");
        List<Serie> series = dadosSeries.stream()
                .map(d -> serieMapper.converterDeDadosSerie(d))
                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}