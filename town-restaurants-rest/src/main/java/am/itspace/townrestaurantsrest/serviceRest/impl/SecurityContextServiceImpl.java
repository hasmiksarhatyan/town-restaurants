package am.itspace.townrestaurantsrest.serviceRest.impl;

import am.itspace.townrestaurantscommon.security.CurrentUser;
import am.itspace.townrestaurantsrest.serviceRest.SecurityContextService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    @Override
    public CurrentUser getUserDetails() {
        return (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void setAuthentication(UsernamePasswordAuthenticationToken auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}

