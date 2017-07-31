package me.wonwoo.support.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;

/**
 * Created by wonwoo on 2016. 10. 16..
 */
@Slf4j
public class DeserializingConverter implements Converter<byte[], Object> {

  private final Deserializer<Object> deserializer;

  public DeserializingConverter() {
    this.deserializer = new DefaultDeserializer();
  }

  public DeserializingConverter(ClassLoader classLoader) {
    this.deserializer = new DefaultDeserializer(classLoader);
  }

  public DeserializingConverter(Deserializer<Object> deserializer) {
    Assert.notNull(deserializer, "Deserializer must not be null");
    this.deserializer = deserializer;
  }


  @Override
  public Object convert(byte[] source) {
    ByteArrayInputStream byteStream = new ByteArrayInputStream(source);
    try {
      return this.deserializer.deserialize(byteStream);
    }
    catch (Throwable ex) {
      logger.debug("Failed to deserialize payload.");
      return "";
//      throw new SerializationFailedException("Failed to deserialize payload. " +
//        "Is the byte array a result of corresponding serialization for " +
//        this.deserializer.getClass().getSimpleName() + "?", ex);
    }
  }

}
