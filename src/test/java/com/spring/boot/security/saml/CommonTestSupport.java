package com.spring.boot.security.saml;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommonTestSupport {

    public static final String USER_NAME = "UserName";

    public static final String USER_PASSWORD = "<abc123>";

    public static final String USER_ROLE = "ROLE_USER";

    public static final String ANONYMOUS_USER_KEY = "UserKey";

    public static final String ANONYMOUS_USER_PRINCIPAL = "UserPrincipal";

    public static final List<GrantedAuthority> AUTHORITIES =
            Collections.singletonList(new SimpleGrantedAuthority(USER_ROLE));

    public static final User USER_DETAILS = new User(USER_NAME, USER_PASSWORD, AUTHORITIES);

    public MockHttpSession mockHttpSession(boolean secured) {
        MockHttpSession mockSession = new MockHttpSession();

        SecurityContext mockSecurityContext = mock(SecurityContext.class);

        if (secured) {
            ExpiringUsernameAuthenticationToken principal =
                    new ExpiringUsernameAuthenticationToken(null, USER_DETAILS, USER_NAME, AUTHORITIES);
            principal.setDetails(USER_DETAILS);
            when(mockSecurityContext.getAuthentication()).thenReturn(principal);
        }

        SecurityContextHolder.setContext(mockSecurityContext);
        mockSession.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                mockSecurityContext);

        return mockSession;
    }

    public MockHttpSession mockAnonymousHttpSession() {
        MockHttpSession mockSession = new MockHttpSession();

        SecurityContext mockSecurityContext = mock(SecurityContext.class);

        AnonymousAuthenticationToken principal =
                new AnonymousAuthenticationToken(
                        ANONYMOUS_USER_KEY,
                        ANONYMOUS_USER_PRINCIPAL,
                        AUTHORITIES);

        when(mockSecurityContext.getAuthentication()).thenReturn(principal);

        SecurityContextHolder.setContext(mockSecurityContext);
        mockSession.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                mockSecurityContext);

        return mockSession;
    }
}
