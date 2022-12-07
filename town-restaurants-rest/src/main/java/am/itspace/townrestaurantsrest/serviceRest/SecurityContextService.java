package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.security.CurrentUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SecurityContextService {

    CurrentUser getUserDetails();

    void setAuthentication(final UsernamePasswordAuthenticationToken auth);

    void clearAuthentication();
}
