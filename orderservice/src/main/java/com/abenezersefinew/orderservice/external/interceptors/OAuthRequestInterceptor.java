package com.abenezersefinew.orderservice.external.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

/** This intercepts requests sent using the feign communication method. */
@Configuration
public class OAuthRequestInterceptor implements RequestInterceptor {

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Autowired
    public OAuthRequestInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " +
                oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest
                                .withClientRegistrationId("internal-client") /** Client registration name defined in application.yml*/
                                .principal("internal") /** Custom scope defined in okta dashboard. */
                                .build())
                        .getAccessToken().getTokenValue());
    }
}
