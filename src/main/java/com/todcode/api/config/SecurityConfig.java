package com.todcode.api.config;

import com.todcode.api.web.security.JwtAuthenticationEntryPoint;
import com.todcode.api.web.security.JwtAuthenticationFilter;
import com.todcode.api.web.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * <p>
     * CustomUserDetailsService - To authenticate a User or perform various
     * role-based checks, Spring security needs to load users details somehow. For
     * this purpose, It consists of an interface called UserDetailsService which has
     * a single method that loads a user based on username
     * </p>
     *
     * <p>
     * <b>UserDetails loadUserByUsername(String username) throws
     * UsernameNotFoundException;</b>
     * </p>
     * We'll define a CustomUserDetailsService that implements UserDetailsService
     * interface and provides the implementation for loadUserByUsername() method.
     * </p>
     *
     * <p>
     * Note that, the loadUserByUsername() method returns a UserDetails object that
     * Spring Security uses for performing various authentication and role based
     * validations.
     * </p>
     *
     * <p>
     * In our implementation, We'll also define a custom UserPrincipal class that
     * will implement UserDetails interface, and return the UserPrincipal object
     * from loadUserByUsername() method.
     * </p>
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    /**
     * <p>
     * This class is used to return a 401 unauthorized error to clients that try to
     * access a protected resource without proper authentication. It implements
     * Spring Security's <i>AuthenticationEntryPoint</i> interface.
     * </p>
     */
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    /**
     *
     * @Bean tells Spring 'here is an instance of this class, please keep hold of it
     *       and give it back to me when I ask'.
     *
     * @Autowired says 'please give me an instance of this class, for example, one
     *            that I created with an @Bean annotation earlier'.
     */
    /**
     * <p>PlatformJwtConfiguration
     * We'll use JWTAuthenticationFilter to implement a filter that -
     *
     * <br>
     * --reads JWT authentication token from the Authorization header of all the
     * requests <br>
     * --validates the token <br>
     * --loads the user details associated with that token. <br>
     * --Sets the user details in Spring Security's SecurityContext. Spring Security
     * uses the user details to perform authorization checks. We can also access the
     * user details stored in the SecurityContext in our controllers to perform our
     * business logic.
     * <p>
     *
     * @return
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    /**
     * AuthenticationManagerBuilder is used to create an AuthenticationManager
     * instance which is the main Spring Security interface for authenticating a
     * user.<br>
     * <p>
     * You can use AuthenticationManagerBuilder to build in-memory authentication,
     * LDAP authentication, JDBC authentication, or add your custom authentication
     * provider.<br>
     * <p>
     * In our example, we've provided our customUserDetailsService and a
     * passwordEncoder to build the AuthenticationManager.</br>
     * <p>
     * We'll use the configured AuthenticationManager to authenticate a user in the
     * login API.<br>
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * The HttpSecurity configurations are used to configure security
     * functionalities like csrf, sessionManagement, and add rules to protect
     * resources based on various conditions.<br>
     * <p>
     * In our example, we're permitting access to static resources and few other
     * public APIs to everyone and restricting access to other APIs to authenticated
     * users only.<br>
     * <p>
     * We've also added the JWTAuthenticationEntryPoint and the custom
     * JWTAuthenticationFilter in the HttpSecurity configuration.<br>
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/", "favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
                        "/**/*.css", "/**/*.js")
                .permitAll().antMatchers("/api/auth/**").permitAll().antMatchers("/swagger-resources/**").permitAll()// for
                // swagger
                .antMatchers("/v2/api-docs/**").permitAll() // for swagger documentation
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
                .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**").permitAll().anyRequest().authenticated();
        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
