package testutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EqualVerifier {
    private List<String> errorMessages = new ArrayList<>();

    private Field makeAccessible(Field field) {
        if (Modifier.isPrivate(field.getModifiers()))
            field.setAccessible(true);
        return field;
    }

    // TODO Java 17 record?
    class FieldComparison {
        Object expected;
        Object actual;
        Field field;

        public FieldComparison(Field field, Object expected, Object actual) {
            this.field = field;
            this.expected = value(field, expected);
            this.actual = value(field, actual);
        }

        boolean areEqual() {
            if (expected == null && actual == null) return true;
            if (expected == null || actual == null) return false;
            return expected.equals(actual);
        }

        String message() {
            return String.format("\texpected.%s <<%s>> %s actual.%s <<%s>>",
                    field.getName(), expected,
                    areEqual() ? "equal" : "not equal",
                    field.getName(), actual);
        }
    }

    public <T> boolean areAllFieldsEqual(T expected, T actual) {
        this.errorMessages = fieldErrorMessages(expected, actual);
        return errorMessages.isEmpty();
    }

    private <T> List<String> fieldErrorMessages(T expected, T actual) {
        return Arrays.stream(expected.getClass().getDeclaredFields())
                .map(this::makeAccessible)
                .map(field -> new FieldComparison(field, expected, actual))
                .filter(fieldComparison -> !fieldComparison.areEqual())
                .map(FieldComparison::message)
                .collect(Collectors.toList());
    }

    private <T> Object value(Field field, T obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> boolean areAllFieldsEqualInList(List<T> expectedList, List<T> actualList) {
        errorMessages = new ArrayList<>();
        if (expectedList.size() != actualList.size()) {
            errorMessages.add("lists vary in size");
            return false;
        }

        for (var i = 0; i < expectedList.size(); i++) {
            var fieldErrorMessages = fieldErrorMessages(expectedList.get(i), actualList.get(i));
            if (!fieldErrorMessages.isEmpty()) {
                errorMessages.add("mismatch at index " + i);
                errorMessages.addAll(fieldErrorMessages);
            }
        }
        return errorMessages.isEmpty();
    }

    public List<String> errorMessages() {
        return errorMessages;
    }
}
