package am.itspace.townrestaurantsrest.config;
import am.itspace.townrestaurantscommon.entity.Role;
import am.itspace.townrestaurantscommon.security.UserDetailServiceImpl;
import am.itspace.townrestaurantsrest.security.JWTAuthenticationTokenFilter;
import am.itspace.townrestaurantsrest.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
//                .antMatchers(HttpMethod.POST, "/categories").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "//{id}").hasAuthority(Role.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/products").hasAuthority(Role.CUSTOMER.name())
                .antMatchers(HttpMethod.PUT, "/products").hasAuthority(Role.CUSTOMER.name())
                .antMatchers(HttpMethod.DELETE, "/products/{id}").hasAuthority(Role.CUSTOMER.name())
                .anyRequest().permitAll();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}