//package config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
////    @Value("${eureka.username}")
////    private String username;
////    @Value("${eureka.password}")
////    private String password;
//
////    @Bean
////    public InMemoryUserDetailsManager userDetailsService() {
////        UserDetails user = User.withUsername(username)
//////                .username(username)
////                .password(password)
////                .roles("USER")
////                .build();
////        return new InMemoryUserDetailsManager(user);
////    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
////        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/eureka/**"));
////        httpSecurity.csrf()
////                ((csrf) -> csrf.disable()
////                        .ignoringRequestMatchers("/eureka/**");
////        );
////        httpSecurity.csrf().ignoringRequestMatchers("/eureka/**");
////                .authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated())
////                .httpBasic(Customizer.withDefaults())
//        return httpSecurity.build();
//    }
//}
