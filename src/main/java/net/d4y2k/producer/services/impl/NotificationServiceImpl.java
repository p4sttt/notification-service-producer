package net.d4y2k.producer.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.d4y2k.producer.kafka.NotificationDto;
import net.d4y2k.producer.services.NotificationService;
import net.d4y2k.producer.store.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

	@Value("${kafka.topic}")
	public String TOPIC ;

	private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

	@Override
	public void notifyForSomethingDone(UserEntity user) {

		NotificationDto notificationDto = NotificationDto.builder()
				.email(user.getEmail())
				.title("Don't miss it!")
				.message("One of the users you are subscribed to did something.")
				.build();

		log.debug("Sending notification: {}", notificationDto);

		kafkaTemplate.send(TOPIC, notificationDto);
	}
}
