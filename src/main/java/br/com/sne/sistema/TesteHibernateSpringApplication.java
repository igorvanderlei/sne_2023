package br.com.sne.sistema;

import java.util.List;
import java.util.TimeZone;

import javax.swing.SwingUtilities;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoRecurso;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.main.TelaPrincipal;

import com.formdev.flatlaf.themes.FlatMacLightLaf;



@SpringBootApplication
public class TesteHibernateSpringApplication {
	public static void main(String[] args) {
	    ConfigurableApplicationContext ctx = new SpringApplicationBuilder(TesteHibernateSpringApplication.class).headless(false).run(args);
		//SpringApplication.run(TesteHibernateSpringApplication.class, args);
		//test();
		//TestReport tr = new TestReport();
	    TimeZone.setDefault(TimeZone.getTimeZone("America/Recife"));
	    SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FlatMacLightLaf.setup();

				final TelaPrincipal tela = new TelaPrincipal();
				tela.setVisible(true);
			}
		} );
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
		//	test();
		};
	}  


	public static void test() {
		List<Grupo> g = Facade.getInstance().listarGrupos();
		g.stream().forEach( e -> System.out.println(e) );
	}

	public static void test2() {
		Grupo g = new Grupo();
		g.setCodigo("01");
		g.setNome("Audio");
		g.setTipoRecurso(TipoRecurso.EQUIPE_TECNICA);

		Facade.getInstance().salvarGrupo(g);

	}




}
