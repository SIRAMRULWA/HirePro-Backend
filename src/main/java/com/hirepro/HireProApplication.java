package com.hirepro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class HireProApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(HireProApplication.class);
		Environment env = app.run(args).getEnvironment();

		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = env.getProperty("server.ssl.key-store") != null ? "https" : "http";
		String serverPort = env.getProperty("server.port", "8080");
		String contextPath = env.getProperty("server.servlet.context-path", "");
		String h2ConsolePath = env.getProperty("spring.h2.console.path", "/h2-console");

		String[] profiles = env.getActiveProfiles().length == 0
				? env.getDefaultProfiles()
				: env.getActiveProfiles();

		System.out.printf("\n----------------------------------------------------------\n" +
						"Application '%s' is running!\n" +
						"Access URLs:\n" +
						"Local: \t\t%s://localhost:%s%s\n" +
						"External: \t%s://%s:%s%s\n" +
						"Profile(s): \t%s\n" +
						"H2 Console: \t%s://localhost:%s%s\n" +
						"----------------------------------------------------------\n",
				env.getProperty("spring.application.name", "HirePro"),
				protocol, serverPort, contextPath,
				protocol, env.getProperty("server.address", "localhost"), serverPort, contextPath,
				String.join(", ", profiles),
				protocol, serverPort, h2ConsolePath);
	}
}
