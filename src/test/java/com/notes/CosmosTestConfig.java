package com.notes;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.spring.autoconfigure.cosmos.CosmosAutoConfiguration;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CosmosTestConfig extends AbstractCosmosConfiguration {
    @Value("${cosmos.dbname}")
    private  String dbname;
    @Value("${cosmos.loc}")
    private String url;
    @Value("${cosmos.key}")
    private String key;

    protected String getDatabaseName() {
        return dbname;
    }

    @Bean
    public AzureKeyCredential dbCredential() {
         return  new AzureKeyCredential(key);
    }

    @Bean
    public CosmosClientBuilder cosmosClientBuilder(AzureKeyCredential dbCredential) {
        CosmosClientBuilder cosmosClientBuilder = new CosmosClientBuilder();
        cosmosClientBuilder.credential(dbCredential).endpoint(url);
        return cosmosClientBuilder;
    }
}
