package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.services.NewsletterEmailService;
import be.technobel.makerhub_backend.dal.models.entities.NewsletterEmailEntity;
import be.technobel.makerhub_backend.dal.repositories.NewsletterEmailRepository;
import be.technobel.makerhub_backend.pl.models.forms.EmailForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing newsletter subscriptions.
 * Provides functionality to subscribe and unsubscribe from the newsletter.
 */
@Service
public class NewsletterEmailServiceImpl implements NewsletterEmailService {
    private final NewsletterEmailRepository newsletterEmailRepository;

    public NewsletterEmailServiceImpl(NewsletterEmailRepository newsletterEmailRepository) {
        this.newsletterEmailRepository = newsletterEmailRepository;
    }

    /**
     * Subscribes a user to the newsletter using email provided in the form.
     * @param form Contains the email address for subscription.
     * @throws IllegalArgumentException if the form is null.
     */
    @Override
    public void subscribeToNewsletter(EmailForm form) {
        if(form == null){
            throw new IllegalArgumentException("Form can't be null");
        }

        NewsletterEmailEntity newsletterEmailEntity = new NewsletterEmailEntity();
        newsletterEmailEntity.setEmail(form.getEmail());
        newsletterEmailRepository.save(newsletterEmailEntity);
    }

    /**
     * Unsubscribes a user from the newsletter using email provided in the form.
     * @param form Contains the email address for unsubscription.
     * @throws IllegalArgumentException if the form is null.
     * @throws EntityNotFoundException if the email is not found in the repository.
     */
    @Override
    public void unsubscribeFromNewsletter(EmailForm form) {
        if (form==null){
            throw new IllegalArgumentException("Form can't be null");
        }

        NewsletterEmailEntity user = newsletterEmailRepository.findByEmail(form.getEmail()).orElseThrow(()-> new EntityNotFoundException("Email not found."));
        user.setSubscribed(false);
        newsletterEmailRepository.save(user);
    }
}
