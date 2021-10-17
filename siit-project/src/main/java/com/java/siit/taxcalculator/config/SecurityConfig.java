package com.java.siit.taxcalculator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomLoginSuccessHandler successHandler;

    @Autowired
    private DataSource dataSource;

    @Value("${myapp.queries.users-query}")
    private String usersQuery;


//
//    @Component("userSecurity")
//    public class UserSecurity {
//        public boolean hasUserId(LoginEntity loginEntity, Long id) {
//            boolean answer;
//            if (loginEntity.getId() == id) {
//                answer = true;
//            } else { answer = false;}
//            return answer;
//        }
//    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("SELECT user_email, user_password, enabled " +"FROM login " +"WHERE user_email= ?").authoritiesByUsernameQuery("SELECT user_email, authority "+ "FROM authorities "+ "WHERE user_email= ?").passwordEncoder(bCryptPasswordEncoder);


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery("SELECT user_email, 'ROLE_USER' FROM login WHERE user_email=?")
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**/{id}").hasRole("USER")
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable().formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(successHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

}