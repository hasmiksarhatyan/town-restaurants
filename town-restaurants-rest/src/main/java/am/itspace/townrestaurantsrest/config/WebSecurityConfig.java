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
                .antMatchers(HttpMethod.GET, "/users").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/users/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/users/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/users/password/restore").authenticated()
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/events").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/events/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/events/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/products").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers(HttpMethod.GET, "/products/byRole").authenticated()
                .antMatchers(HttpMethod.GET, "/products/byOwner").authenticated()
                .antMatchers(HttpMethod.PUT, "/products/{id}").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers(HttpMethod.DELETE, "/products/{id}").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers(HttpMethod.POST, "/productCategories").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers(HttpMethod.PUT, "/productCategories/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/productCategories/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/restaurants").authenticated()
                .antMatchers(HttpMethod.GET, "/restaurants/user").authenticated()
                .antMatchers(HttpMethod.PUT, "/restaurants/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/restaurants/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/restaurantsCategories").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers(HttpMethod.PUT, "/restaurantsCategories/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/restaurantsCategories/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/reservation").authenticated()
                .antMatchers(HttpMethod.GET, "/reservation").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/reservation/byUser").authenticated()
                .antMatchers(HttpMethod.PUT, "/reservation/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/reservation/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/orders").authenticated()
                .antMatchers(HttpMethod.GET, "/orders").authenticated()
                .antMatchers(HttpMethod.GET, "/orders/{id}").authenticated()
                .antMatchers(HttpMethod.PUT, "/orders/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/orders/{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/baskets/addTo/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/baskets").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.GET, "/baskets/byUser").authenticated()
                .antMatchers(HttpMethod.DELETE, "/baskets/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/payments").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/payments/{id}").hasAuthority(Role.MANAGER.name())
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