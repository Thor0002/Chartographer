package Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Application.DAO.ChartaDAO;
import Application.Exception.ChartNotFoundException;
import Application.Exception.OutOfBoundsException;

import java.io.IOException;

@RestController
@RequestMapping("/chartas")
public class ChartographerController {

	@Autowired 
	private final ChartaDAO chartaDAO = new ChartaDAO();
	
	public int size() {return chartaDAO.toMap().size();}

	//	@Autowired 
	//	public ChartographerController(ChartaDAO chartaDAO){
	//		this.chartaDAO = chartaDAO;
	//	}

	@PostMapping("/")
	public ResponseEntity<String> create(@RequestParam int width, @RequestParam int height)  {
		try {
			return new ResponseEntity<String>(chartaDAO.create(width, height), HttpStatus.CREATED);
		} catch(OutOfBoundsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch(IOException e) {
			return ResponseEntity.badRequest().body("Ошибка");
		}

	}


	@PostMapping(value = "/{id}/", consumes = "image/bmp")
	//  @ResponseStatus(HttpStatus.OK)	
	public ResponseEntity<String> save(@PathVariable String  id, @RequestParam int x, @RequestParam int y,
			@RequestParam int width, @RequestParam int height, @RequestBody byte[] image){
		try { chartaDAO.save(id, x, y, width, height, image);
		    return ResponseEntity.ok("ok");
		} catch(OutOfBoundsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch(ChartNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(IOException e) {
			return ResponseEntity.badRequest().body("Ошибка");
		}
	}
	//   ?
	@GetMapping(value ="/{id}/", produces = "image/bmp")
	public ResponseEntity get(@PathVariable String  id, @RequestParam int x, @RequestParam int y,
			@RequestParam int width, @RequestParam int height) {
		try {
			byte[] bytes = chartaDAO.get(id, x, y, width, height);
			return new ResponseEntity<>(bytes, HttpStatus.OK); 
		} catch(OutOfBoundsException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch(ChartNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch(IOException e) {
			return ResponseEntity.badRequest().body("Ошибка");
		}


	}

	@DeleteMapping("/{id}/")
	//  @ResponseStatus(HttpStatus.OK)	
	public ResponseEntity delete(@PathVariable String  id) {
		try {
			chartaDAO.delete(id);
			return ResponseEntity.ok("ok");
		} catch(ChartNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Ошибка");
		} 
	}
}

