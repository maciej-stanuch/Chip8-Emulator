import java.util.Collections;
import java.util.List;

public class ErrorCollection {
    private final List<String> errors;

    private ErrorCollection(List<String> errors) {
        this.errors = errors;
    }

    public static ErrorCollection of(List<String> errors) {
        return new ErrorCollection(errors);
    }

    public static ErrorCollection of(String error) {
        return new ErrorCollection(Collections.singletonList(error));
    }

    public static ErrorCollection empty() {
        return new ErrorCollection(Collections.emptyList());
    }

    public boolean hasAnyErrors() {
        return !errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
