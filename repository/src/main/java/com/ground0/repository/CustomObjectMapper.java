package com.ground0.repository;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by zer0 on 22/12/16.
 */

public class CustomObjectMapper extends ObjectMapper {

  public CustomObjectMapper() {
    registerModule(new CustomLocalDateTimeJsonModule());
  }

  private static class CustomLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    public static final CustomLocalDateTimeSerializer INSTANCE =
        new CustomLocalDateTimeSerializer();

    @Override public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider arg2)
        throws IOException, JsonProcessingException {
      gen.writeString(DATE_TIME_FORMATTER.format(
          ZonedDateTime.of(value, ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)));
    }
  }

  private static class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final CustomLocalDateTimeDeserializer INSTANCE =
        new CustomLocalDateTimeDeserializer();

    @Override public LocalDateTime deserialize(JsonParser jsonParser,
        DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
      JsonToken currentToken = jsonParser.getCurrentToken();
      if (currentToken == JsonToken.VALUE_STRING) {
        String dateTimeAsString = jsonParser.getText().trim();
        return ZonedDateTime.parse(dateTimeAsString, DATE_TIME_FORMATTER.withZone(ZoneOffset.UTC))
            .withZoneSameInstant(ZoneId.systemDefault())
            .toLocalDateTime();
      } else {
        throw deserializationContext.mappingException(LocalDateTime.class);
      }
    }
  }

  public class CustomLocalDateTimeJsonModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public CustomLocalDateTimeJsonModule() {
      super();
      addSerializer(LocalDateTime.class, CustomLocalDateTimeSerializer.INSTANCE);
      addDeserializer(LocalDateTime.class, CustomLocalDateTimeDeserializer.INSTANCE);
    }
  }
}