package net.d4y2k.producer.store.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class UserEntity {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Builder.Default
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder.Default
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<UserEntity> subscriptions = new HashSet<>();

}