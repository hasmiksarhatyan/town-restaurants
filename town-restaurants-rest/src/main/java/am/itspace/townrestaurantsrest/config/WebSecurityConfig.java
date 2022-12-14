package am.itspace.townrestaurantsrest.config;

import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.security.UserDetailServiceImpl;
import am.itspace.townrestaurantsrest.security.JWTAuthenticationTokenFilter;
import am.itspace.townrestaurantsrest.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/registration").permitAll()
                .antMatchers(HttpMethod.POST, "/activation").permitAll()
                .antMatchers(HttpMethod.POST, "/verification").permitAll()

                .antMatchers(HttpMethod.GET, "/users").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/users/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/users/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/password/restore").authenticated()
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/events").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/events").authenticated()
                .antMatchers(HttpMethod.GET, "/events/byRestaurant/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/events/restaurants").authenticated()
                .antMatchers(HttpMethod.GET, "/events/getImages").permitAll()
                .antMatchers(HttpMethod.GET, "/events/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/events/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/events/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/products").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/products").permitAll()
                .antMatchers(HttpMethod.GET, "/products/byRole").authenticated()
                .antMatchers(HttpMethod.GET, "/products/byOwner").authenticated()
                .antMatchers(HttpMethod.GET, "/products/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/products/byRestaurant/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/products/getImages").permitAll()
                .antMatchers(HttpMethod.PUT, "/products/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/products/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/productCategories").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/productCategories").permitAll()
                .antMatchers(HttpMethod.GET, "/productCategories/{id}").permitAll()
                .antMatchers(HttpMethod.PUT, "/productCategories/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/productCategories/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/restaurants").authenticated()
                .antMatchers(HttpMethod.GET, "/restaurants").permitAll()
                .antMatchers(HttpMethod.GET, "/restaurants/user").authenticated()
                .antMatchers(HttpMethod.GET, "/restaurants/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/restaurants/getImages").permitAll()
                .antMatchers(HttpMethod.PUT, "/restaurants/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/restaurants/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/restaurantsCategories").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/restaurantsCategories").permitAll()
                .antMatchers(HttpMethod.GET, "/restaurantsCategories/{id}").permitAll()
                .antMatchers(HttpMethod.PUT, "/restaurantsCategories/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/restaurantsCategories/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/reservation").authenticated()
                .antMatchers(HttpMethod.GET, "/reservation").authenticated()
                .antMatchers(HttpMethod.GET, "/reservation/byUser").authenticated()
                .antMatchers(HttpMethod.PUT, "/reservation/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/reservation/{id}").hasAuthority(Role.MANAGER.name())

                .antMatchers(HttpMethod.POST, "/orders").authenticated()
                .antMatchers(HttpMethod.GET, "/orders").authenticated()
                .antMatchers(HttpMethod.GET, "/orders/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/orders/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/orders/{id}").hasAuthority(Role.MANAGER.name())


                .antMatchers(HttpMethod.GET, "/baskets/addTo/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/baskets").authenticated()
                .antMatchers(HttpMethod.GET, "/baskets/byUser").authenticated()
                .antMatchers(HttpMethod.DELETE, "/baskets/{id}").authenticated()


                .antMatchers(HttpMethod.GET, "/payments").authenticated()
                .antMatchers(HttpMethod.DELETE, "/payments/{id}").authenticated()

                .antMatchers(HttpMethod.GET, "/creditCards").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/creditCards/byUser").hasAuthority(Role.MANAGER.name())

                .anyRequest().permitAll();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}