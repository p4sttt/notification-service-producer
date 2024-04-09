package net.d4y2k.producer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.d4y2k.producer.kafka.NotificationDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class NotificationSerializer implements Serializer<NotificationDto> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public byte[] serialize(String topic, NotificationDto data) {
		try {
			if (data == null){
				return null;
			}

			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new SerializationException("Error when serializing MessageDto to byte[]");
		}
	}

	@Override
	public void close() {
	}
}