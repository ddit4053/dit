package kr.or.ddit.util;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RequestToVoMapper {
    private static final Map<Class<?>, Function<String, ?>> TYPE_CONVERTERS = new HashMap<>();

    static {
        TYPE_CONVERTERS.put(String.class, value -> value);
        TYPE_CONVERTERS.put(int.class, Integer::parseInt);
        TYPE_CONVERTERS.put(Integer.class, Integer::parseInt);
        TYPE_CONVERTERS.put(double.class, Double::parseDouble);
        TYPE_CONVERTERS.put(Double.class, Double::parseDouble);
        TYPE_CONVERTERS.put(boolean.class, Boolean::parseBoolean);
        TYPE_CONVERTERS.put(Boolean.class, Boolean::parseBoolean);
        TYPE_CONVERTERS.put(long.class, Long::parseLong);
        TYPE_CONVERTERS.put(Long.class, Long::parseLong);
        TYPE_CONVERTERS.put(float.class, Float::parseFloat);
        TYPE_CONVERTERS.put(Float.class, Float::parseFloat);
        TYPE_CONVERTERS.put(LocalDate.class, value -> {
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format: " + value, e);
            }
        });
    }

    public static <T> T mapRequestToVo(HttpServletRequest request, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String paramValue = request.getParameter(field.getName());

                if (paramValue != null) {
                    Object value = convertToFieldType(field.getType(), paramValue);
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("VO 매핑 중 에러 발생", e);
        }
    }

    private static Object convertToFieldType(Class<?> fieldType, String value) {
        Function<String, ?> converter = TYPE_CONVERTERS.get(fieldType);
        if (converter != null) {
            return converter.apply(value);
        }
        throw new UnsupportedOperationException("Unsupported field type: " + fieldType.getName());
    }
}
