package sg.edu.iss.nus.pafworkshop7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.iss.nus.pafworkshop7.respository.GameRepository;

@SpringBootApplication
public class Pafworkshop7Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Pafworkshop7Application.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
