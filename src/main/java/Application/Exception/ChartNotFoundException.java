package Application.Exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ChartNotFoundException extends Exception {
    public ChartNotFoundException() {
        super("Изображение не найдено");
    }
}
