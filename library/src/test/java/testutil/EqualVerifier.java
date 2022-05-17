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
        Object value1;
        Object value2;
        Field field;

        public FieldComparison(Field field, Object obj1, Object obj2) {
            this.field = field;
            value1 = value(field, obj1);
            value2 = value(field, obj2);
        }

        boolean areEqual() {
            if (value1 == null && value2 == null) return true;
            if (value1 == null || value2 == null) return false;
            return value1.equals(value2);
        }

        String message() {
            return String.format("\tobj1.%s <<%s>> %s obj2.%s <<%s>>",
                    field.getName(), value1,
                    areEqual() ? "==" : "!=",
                    field.getName(), value2);
        }
    }

    public <T> boolean areAllFieldsEqual(T obj1, T obj2) {
        this.errorMessages = fieldErrorMessages(obj1, obj2);
        return errorMessages.isEmpty();
    }

    private <T> List<String> fieldErrorMessages(T obj1, T obj2) {
        return Arrays.stream(obj1.getClass().getDeclaredFields())
                .map(this::makeAccessible)
                .map(field -> new FieldComparison(field, obj1, obj2))
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

    public <T> boolean areAllFieldsEqualInList(List<T> list1, List<T> list2) {
        errorMessages = new ArrayList<>();
        if (list1.size() != list2.size()) {
            errorMessages.add("lists vary in size");
            return false;
        }

        for (var i = 0; i < list1.size(); i++) {
            var fieldErrorMessages = fieldErrorMessages(list1.get(i), list2.get(i));
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
