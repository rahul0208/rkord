package com.notes;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
@Profile("prod")
@Configuration
public class CosmosConfig extends AbstractCosmosConfiguration {
    @Value("${cosmos.dbname}")
    private  String dbname;
    @Value("${cosmos.loc}")
    private String url;

    protected String getDatabaseName() {
        return dbname;
    }

    @Bean
    public ManagedIdentityCredential dbCredential() {
         ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
                .build();
         return  managedIdentityCredential;
    }

    @Bean
    public CosmosClientBuilder cosmosClientBuilder(ManagedIdentityCredential dbCredential) {
        CosmosClientBuilder cosmosClientBuilder = new CosmosClientBuilder();
        cosmosClientBuilder.credential(dbCredential).endpoint(url);
        return cosmosClientBuilder;
    }
}
