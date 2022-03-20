package Application.DAO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Application.Exception.ChartNotFoundException;
import Application.Exception.OutOfBoundsException;
import Application.Models.Charta;


@Service
public class ChartaDAO {
	private Integer id = 0;

	private Map<String, Charta> Chartas = new HashMap<String, Charta>();

	public ChartaDAO() {Chartas = new HashMap<String, Charta>();}

	public Map<String, Charta> toMap(){return Chartas;}

	public Charta get(String id) {return Chartas.get(id);}

	public String create(int width, int height) throws OutOfBoundsException, IOException {
		if(width > 20000 || width <= 0 || height > 50000 || height <= 0) {
			throw new OutOfBoundsException("Некоректные размеры изображения");
		}
		Charta ch = new Charta(width, height, id.toString());
		Chartas.put(id.toString(), ch);
		
		File fileCharta = new File(ch.getPathToImage());
		BufferedImage imageCharta = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		ImageIO.write(imageCharta, "bmp", fileCharta);
		
		return (id++).toString();
	}

	public void save( String  id,  int x,  int y, int width,  int height,  byte[] image) throws IOException, OutOfBoundsException, ChartNotFoundException {
		Charta ch = Chartas.get(id);
		if(ch == null) {
			throw new ChartNotFoundException();
		}
		if(width > 5000 || width <= 0 || height > 5000 || height <= 0) {
			throw new OutOfBoundsException("Некоректные размеры фрагмента");
		}
		
		BufferedImage input;
		ByteArrayInputStream stream = new ByteArrayInputStream(image);
		input = ImageIO.read(stream);
		if(width != input.getWidth() || height != input.getHeight()) {
			throw new OutOfBoundsException("Реальные размеры фрагмента не соответсвуют заданным");
		}
		File fileCharta = new File(ch.getPathToImage());
		BufferedImage imageCharta = ImageIO.read(fileCharta);
		
		boolean t = true;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(0 <= x + i && x + i < ch.getWidth() && 0 <= y + j && y + j < ch.getHeight()) {
					t = false;
					int rgb = input.getRGB(i, j);
					imageCharta.setRGB(x + i, y + j, rgb);
				}
			}
		}
		if(t){
			throw new OutOfBoundsException("Фрагмент не пересекается по координатам с изображением");
		}
		
		ImageIO.write(imageCharta, "bmp", fileCharta);

	}


	public byte[] get( String  id,  int x,  int y, int width,  int height) throws ChartNotFoundException, OutOfBoundsException, IOException {
		Charta ch = Chartas.get(id);
		if(ch == null) {
			throw new ChartNotFoundException();
		}
		if(width > 5000 || width <= 0 || height > 5000 || height <= 0) {
			throw new OutOfBoundsException("Некоректные размеры фрагмента");
		}
		
		BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		File fileCharta = new File(ch.getPathToImage());
		BufferedImage imageCharta = ImageIO.read(fileCharta);
		boolean t = true;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(0 <= x + i && x + i < ch.getWidth() && 0 <= y + j && y + j < ch.getHeight() ) {
					t = false;
					int rgb = imageCharta.getRGB(x + i, y + j);
					output.setRGB(i, j, rgb);
				}
			}
		}
		if(t){
			throw new OutOfBoundsException("Фрагмент не пересекается по координатам с изображением");
		}
		byte[] bytes;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ImageIO.write(output, "bmp", stream);
		bytes = stream.toByteArray();
		return bytes;
	}

	public void delete(String id) throws ChartNotFoundException, IOException {
		Charta ch = Chartas.remove(id);
		if(ch == null) {
			throw new ChartNotFoundException();
		}
		File file = new File(ch.getPathToImage());
		if(!file.delete()) {throw new IOException("Файл не удалён");}
	}
}
