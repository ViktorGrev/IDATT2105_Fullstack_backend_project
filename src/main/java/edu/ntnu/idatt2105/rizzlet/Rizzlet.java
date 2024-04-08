package edu.ntnu.idatt2105.rizzlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the application.
 * It is responsible for starting the application.
 */
@SpringBootApplication
public class Rizzlet {

	/**
	 * Main method to start the application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Rizzlet.class, args);
	}

}
