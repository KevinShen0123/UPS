package amazon_ups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import  org.springframework.security.provisioning.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;
    @Autowired
    @Lazy
    private CustomeUserDetailService service;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }
    @Bean
    public PasswordEncoder myencoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers( "/").permitAll()
                        .requestMatchers("/start/*").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(service)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
    @Service
    public class CustomeUserDetailService implements UserDetailsService {
        @Autowired
        private CustomUserRepositoryImpl repo;
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            List<Object[]> users=repo.findUserByName(username);
            System.out.println("size:"+users.size());

            System.out.println("class:"+users.get(0)[0]);
            System.out.println("class:"+users.get(0)[1]);
            System.out.println("class:"+users.get(0)[2]);
            System.out.println("class:"+users.get(0)[3]);
            amazon_ups.UserEntity user=new UserEntity();
            user.setUsername((String)users.get(0)[3]);
            user.setPassword((String)users.get(0)[2]);
            user.setEmail((String)users.get(0)[1]);
            user.setId((Long)users.get(0)[0]);
            if(users==null){
                System.out.println("AAAAAAAAAAAAAAAAAA");
                throw new UsernameNotFoundException("user not found!");
            }else if(users.size()!=1) {
                System.out.println("AAABBBBBBBBBBBBBB");
                throw new IllegalArgumentException();
            }
            System.out.println("set OK!!!!!!!!!");
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

// Encode the password
            String encodedPassword =myencoder().encode(user.getPassword());
           return  org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername()) // Assuming username is at index 3
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
        }
    }


}