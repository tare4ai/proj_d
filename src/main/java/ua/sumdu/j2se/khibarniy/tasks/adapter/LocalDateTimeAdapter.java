package ua.sumdu.j2se.khibarniy.tasks.adapter;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    // Формат для серіалізації/десеріалізації LocalDateTime
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Серіалізація: перетворення LocalDateTime у строку
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        // Перетворюємо LocalDateTime у строку з певним форматом
        return new JsonPrimitive(src.format(formatter));
    }

    // Десеріалізація: перетворення строки у LocalDateTime
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Перетворюємо строку назад в LocalDateTime
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}
