package am.itspace.townrestaurantsrest.serviceRest;

import am.itspace.townrestaurantscommon.entity.User;
import am.itspace.townrestaurantscommon.entity.VerificationToken;

public interface VerificationTokenServiceRest {

    VerificationToken createToken(User user);

    VerificationToken findByPlainToken(String plainToken);

    void delete(VerificationToken token);
}

