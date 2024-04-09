package net.d4y2k.producer.services;

import net.d4y2k.producer.store.entities.UserEntity;

public interface NotificationService {

	void notifyForSomethingDone(UserEntity user);

}
