package br.ufscar.dc.dsw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.model.Locacoes;
import br.ufscar.dc.dsw.model.UsuarioCliente;
import br.ufscar.dc.dsw.model.UsuarioLocadora;
import br.ufscar.dc.dsw.repository.UsuarioGeralRepository;
import br.ufscar.dc.dsw.repository.LocacoesRepository;

import br.ufscar.dc.dsw.service.impl.LocacoesService;
import br.ufscar.dc.dsw.service.impl.UsuarioGeralService;

@SpringBootApplication
public class AloMundoMvcApplication {
	private static final Logger log = LoggerFactory.getLogger(AloMundoMvcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AloMundoMvcApplication.class, args);

	}

	@Autowired
	private UsuarioGeralService usuarioGeralService;
	@Autowired
	private LocacoesService locacoesService;

	@Bean
	public CommandLineRunner usuarioGeralDemo(UsuarioGeralRepository uDao) {
		return (args) -> {
			UsuarioCliente usuarioCliente = new UsuarioCliente(
					"02096725526",
					"nayrakalinesv@gmail.com",
					1,
					"senha",
					"username",
					"F",
					"(79) 99175-4690",
					"1999-06-16");
			usuarioGeralService.salvar(usuarioCliente);

			UsuarioCliente usuarioCliente2 = new UsuarioCliente(
					"22222222222",
					"usuario2@exemplo.com",
					1,
					"senha2",
					"username2",
					"M",
					"(31) 2222-2222",
					"1992-11-10");
			usuarioGeralService.salvar(usuarioCliente2);

			UsuarioLocadora usuarioLocadora = new UsuarioLocadora(
					"30.786.232/0001-88",
					"usuariolocadora@gmail.com",
					1,
					"senhaloc",
					"usernameloc",
					"São Carlos");
			usuarioGeralService.salvar(usuarioLocadora);

			UsuarioLocadora usuarioLocadora2 = new UsuarioLocadora(
					"08.743.110/0001-55",
					"usuariolocadora@gmail.com",
					1,
					"senhaloc2",
					"usernameloc2",
					"São Carlos");
			usuarioGeralService.salvar(usuarioLocadora2);

			// buscar usuarioCliente
			UsuarioCliente novo = (UsuarioCliente) usuarioGeralService.buscarPorId((long) 1);

		};
	}

	@Bean
	public CommandLineRunner locacoesDemo(LocacoesRepository lDao) {
		return (args) -> {
			Locacoes locacoes = new Locacoes(
					"02096725526",
					"30.786.232/0001-88",
					"2023-08-07 18:00",
					"2023-09-07 10:00");
			locacoesService.salvar(locacoes);
		};
	}

}
