package Application.Models;

import Application.ChartographerApplication;

public class Charta {
	private String id;
	private int width;
	private int height;
    private String pathToImage;
	
	public Charta(int width, int height, String id) {
		this.width = width; this.height = height; this.id = id;
		pathToImage = ChartographerApplication.path + "/" + java.time.LocalDate.now().toString() + "_" + id +  ".bmp";
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getPathToImage() {
		return pathToImage;
	}

	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}

}
