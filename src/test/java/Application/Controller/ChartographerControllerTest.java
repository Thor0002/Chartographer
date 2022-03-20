package Application.Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

@SpringBootTest
public class ChartographerControllerTest {
	
	@Test
	void create() {
		ChartographerController chartographerController = new ChartographerController();
		Assertions.assertEquals(HttpStatus.CREATED, chartographerController.create(10, 10).getStatusCode());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, chartographerController.create(20001, 50001).getStatusCode());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, chartographerController.create(-10, -10).getStatusCode());
	}
	
	@Test
    void save() {
		ChartographerController chartographerController = new ChartographerController();
		chartographerController.create(10, 10);
		
		BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR);
        try {
        	ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "bmp", stream);
            Assertions.assertEquals(HttpStatus.OK, chartographerController.save("0", 5, 5, 10, 10, stream.toByteArray()).getStatusCode());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, chartographerController.save("0", 20, 20, 10, 10, stream.toByteArray()).getStatusCode());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, chartographerController.save("0", 0, 0, 5001, 5001, stream.toByteArray()).getStatusCode());
            Assertions.assertEquals(HttpStatus.NOT_FOUND, chartographerController.save("1", 5, 5, 10, 10, stream.toByteArray()).getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	@Test
	void get() {
		ChartographerController chartographerController = new ChartographerController();
		chartographerController.create(100, 100);
		Assertions.assertEquals(HttpStatus.OK, chartographerController.get("0", 5, 5, 100, 100).getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, chartographerController.get("0", 101, 101, 10, 10).getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, chartographerController.get("0", 0, 0, 5001, 5001).getStatusCode());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, chartographerController.get("1", 5, 5, 10, 10).getStatusCode());
	}
	
	@Test
	void delete() {
		ChartographerController chartographerController = new ChartographerController();
		chartographerController.create(100, 100);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, chartographerController.delete("1").getStatusCode());
		Assertions.assertEquals(HttpStatus.OK, chartographerController.delete("0").getStatusCode());
		Assertions.assertEquals(0, chartographerController.size());
		
	}

}
