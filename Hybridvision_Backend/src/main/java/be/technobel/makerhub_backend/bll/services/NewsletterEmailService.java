package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.pl.models.forms.EmailForm;

public interface NewsletterEmailService {
    void subscribeToNewsletter(EmailForm form);
    void unsubscribeFromNewsletter(EmailForm form);
}
