package me.braun.authentityservice.config.security;


import lombok.AllArgsConstructor;
import me.braun.authentityservice.config.Tuple2;
import me.braun.authentityservice.config.security.jwt.JwtAuthenticationEntryPoint;
import me.braun.authentityservice.config.security.jwt.JwtConfigurer;
import me.braun.authentityservice.config.security.jwt.JwtProcessor;
import me.braun.authentityservice.config.security.jwt.JwtProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class SecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtProperties jwtProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtConfigurer jwtConfigurer() {
        return new JwtConfigurer(jwtTokenProvider());
    }

    @Bean
    public JwtProcessor jwtTokenProvider() {
        return new JwtProcessor(userDetailsService, jwtProperties);
    }

    @Override
    protected void configure(@NotNull AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        provider.setAuthoritiesMapper(authoritiesMapper());
        return provider;
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        authorityMapper.setDefaultAuthority("user");
        return authorityMapper;
    }

    @Override
    protected void configure(@NotNull HttpSecurity http) throws Exception {
        List<Tuple2<HttpMethod, String>> permittedPaths = Arrays.asList(
                new Tuple2<>(HttpMethod.POST, "/login"),
                new Tuple2<>(HttpMethod.POST, "/signup"),
                new Tuple2<>(HttpMethod.GET, "/spacecrafts"),
                new Tuple2<>(HttpMethod.GET, "/spacecrafts/{id}"),
                new Tuple2<>(HttpMethod.GET, "/craft_titles"),
                new Tuple2<>(HttpMethod.GET, "/craft_titles/{id}"),
                new Tuple2<>(HttpMethod.GET, "/services"),
                new Tuple2<>(HttpMethod.GET, "/services/{id}")

        );
        AtomicReference<ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry> expressionInterceptUrlRegistry = new AtomicReference<>(http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeRequests());
        permittedPaths.forEach(path -> {
            expressionInterceptUrlRegistry.set(expressionInterceptUrlRegistry.get()
                    .antMatchers(path.getFirst(), path.getSecond()).permitAll()
                    .antMatchers(path.getFirst(), path.getSecond() + "/").permitAll());
        });
        expressionInterceptUrlRegistry.get()
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurer());
    }
}
