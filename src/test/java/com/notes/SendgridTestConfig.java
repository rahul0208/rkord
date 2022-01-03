package com.notes;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendgridTestConfig {
    @Value("${sendgrid.key}")
    private String key;
    @Value("${sendgrid.from}")
    private String from;

    @Bean
    public SendGrid sendGridBean() {
        SendGrid sg = new SendGrid(key);
        return sg;
    }

    @Bean
    public Email emailFrom() {
        return new Email(from);
    }

}
