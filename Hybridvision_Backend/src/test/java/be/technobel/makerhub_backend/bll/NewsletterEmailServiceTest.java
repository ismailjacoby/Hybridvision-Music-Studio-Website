package be.technobel.makerhub_backend.bll;

import be.technobel.makerhub_backend.bll.services.implementations.NewsletterEmailServiceImpl;
import be.technobel.makerhub_backend.dal.models.entities.NewsletterEmailEntity;
import be.technobel.makerhub_backend.dal.repositories.NewsletterEmailRepository;
import be.technobel.makerhub_backend.pl.models.forms.EmailForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsletterEmailServiceTest {
    @Mock
    private NewsletterEmailRepository newsletterEmailRepository;

    @InjectMocks
    private NewsletterEmailServiceImpl newsletterEmailService;

    // Newsletter Subscribe - Success
    @Test
    void subscribeToNewsletterSuccessfully() {
        EmailForm form = new EmailForm("user@example.com");

        newsletterEmailService.subscribeToNewsletter(form);

        verify(newsletterEmailRepository, times(1)).save(any(NewsletterEmailEntity.class));
    }

    // Newsletter Subscribe is null
    @Test
    void subscribeToNewsletterWithNullFormThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> newsletterEmailService.subscribeToNewsletter(null),
                "Expected an IllegalArgumentException to be thrown when the form is null");
    }

    // Newsletter Unsubscribe - Success
    @Test
    void unsubscribeFromNewsletterSuccessfully() {
        String email = "user@example.com";
        NewsletterEmailEntity existingSubscription = new NewsletterEmailEntity();
        existingSubscription.setEmail(email);
        existingSubscription.setSubscribed(true);

        when(newsletterEmailRepository.findByEmail(email)).thenReturn(Optional.of(existingSubscription));

        newsletterEmailService.unsubscribeFromNewsletter(new EmailForm(email));

        verify(newsletterEmailRepository).save(argThat(user -> !user.isSubscribed()));
    }

    //Newsletter Unsubscribe - Null
    @Test
    void unsubscribeFromNewsletterWithNullFormThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> newsletterEmailService.unsubscribeFromNewsletter(null),
                "Expected IllegalArgumentException to be thrown when form is null");
    }

    //Newsletter Unsubscribe - Email not Found
    @Test
    void unsubscribeFromNewsletterEmailNotFoundThrowsException() {
        String nonExistentEmail = "nonexistent@example.com";
        when(newsletterEmailRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> newsletterEmailService.unsubscribeFromNewsletter(new EmailForm(nonExistentEmail)),
                "Expected EntityNotFoundException to be thrown when email is not found");
    }

}
