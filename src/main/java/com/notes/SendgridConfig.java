package com.notes;

import com.azure.identity.ManagedIdentityCredential;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.notes.controller.NotesController;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class SendgridConfig {
    private static Logger log = LoggerFactory.getLogger(NotesController.class);

    @Value("${sendgrid.from}")
    private String from;
    @Value("${vault.loc}")
    private String loc;

    @Bean
    public SendGrid sendGridBean(ManagedIdentityCredential identityCredential) {
        SecretClient client = new SecretClientBuilder()
                .vaultUrl(loc)
                .credential(identityCredential)
                .buildClient();
        KeyVaultSecret key = client.getSecret("SENDGRID-KEY");
        log.info("Using key {}",key.getValue());
        SendGrid sg = new SendGrid(key.getValue());
        return sg;
    }

    @Bean
    public Email emailFrom() {
        return new Email(from);
    }

}
