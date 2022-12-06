package am.itspace.townrestaurantsweb.config;

import am.itspace.townrestaurantscommon.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/loginPage")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .failureUrl("/loginPage?error=true")
                .defaultSuccessUrl("/users/home")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/loginPage").permitAll()
                .antMatchers("/users").hasAuthority(Role.MANAGER.name())
                .antMatchers("/users/add").not().authenticated()
                .antMatchers("/users/delete").hasAuthority(Role.MANAGER.name())
                .antMatchers("/users/edit").hasAuthority(Role.MANAGER.name())
                .antMatchers("/users/changePassword").authenticated()
                .antMatchers("/users/verify").authenticated()
                .antMatchers("/users/home").authenticated()
                .antMatchers("/restaurants").permitAll()
                .antMatchers("/restaurants/add").authenticated()
                .antMatchers("/restaurants/delete/*").hasAuthority(Role.MANAGER.name())
                .antMatchers("/restaurants/my").authenticated()
                .antMatchers("/restaurants/edit/*").hasAuthority(Role.MANAGER.name())
                .antMatchers("/restaurantsCategory").permitAll()
                .antMatchers("/restaurantsCategory/add").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers("/restaurantsCategory/delete/*").hasAuthority(Role.MANAGER.name())
                .antMatchers("/products").permitAll()
                .antMatchers("/products/add").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers("/products/my").authenticated()
                .antMatchers("/products/edit/*").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers("/products/delete/*").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers("/productCategories").hasAnyAuthority(Role.MANAGER.name(), Role.RESTAURANT_OWNER.name())
                .antMatchers("/productCategories/**").hasAuthority(Role.MANAGER.name())
                .antMatchers("/payments").authenticated()
                .antMatchers("/payments/add").authenticated()
                .antMatchers("/payments/delete/*").hasAuthority(Role.MANAGER.name())
                .antMatchers("/orders").hasAuthority(Role.MANAGER.name())
                .antMatchers("/delete").hasAuthority(Role.MANAGER.name())
                .antMatchers("/events").permitAll()
                .antMatchers("/events/add").hasAuthority(Role.MANAGER.name())
                .antMatchers("/events/edit/*").hasAuthority(Role.MANAGER.name())
                .antMatchers("/events/delete/*").hasAuthority(Role.MANAGER.name())
                .antMatchers("/cards").hasAuthority(Role.MANAGER.name())
                .antMatchers("/cards/add").authenticated()
                .antMatchers("/baskets").authenticated()
                .antMatchers("/baskets/add").authenticated()
                .antMatchers("/baskets/delete/*").authenticated()
                .antMatchers("/reservations").authenticated()
                .antMatchers("/reservations/edit/*").authenticated()
                .antMatchers("/restaurants/*/reserve").authenticated()
                .antMatchers("/reservations/delete/*").hasAuthority(Role.MANAGER.name())
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
