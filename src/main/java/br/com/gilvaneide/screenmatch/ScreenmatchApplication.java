package br.com.gilvaneide.screenmatch;

import br.com.gilvaneide.screenmatch.principal.Principal;
import br.com.gilvaneide.screenmatch.service.SerieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private SerieMapper serieMapper;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(serieMapper);
		principal.exibeMenu();
	}
}
