package Application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChartographerApplication {
	
	public static String path = System.getProperty("user.dir");

    public static void main(String[] args) {
    	if(args.length != 0 && args[0] != null) {path = args[0];}
        SpringApplication.run(ChartographerApplication.class, args);
    }
}
