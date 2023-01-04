package com.elvir.backend.model.entity;

import com.elvir.backend.model.request.ClientInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clients")
@EqualsAndHashCode(of = "id")
@ToString
public class Client {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(updatable = false, nullable = false)
    private Long phone;

    @Column(name = "chat_id", updatable = false)
    private String chatId;

    @Column(name = "sent_code")
    private String sentCode;

    public void updateByClientInfo(ClientInfo clientInfo) {
        this.firstName = clientInfo.getFirstName();
        this.lastName = clientInfo.getLastName();
        this.phone = clientInfo.getPhone();
    }
}
