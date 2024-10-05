package be.technobel.makerhub_backend.dal.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity @Getter @Setter
public class NewsletterEmailEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsletter_email_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private boolean subscribed = true;

}
