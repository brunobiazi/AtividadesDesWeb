package br.ufscar.dc.dsw.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import br.ufscar.dc.dsw.conversor.BigDecimalConversor;

@Configuration
@ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
public class MvcConfig implements WebMvcConfigurer {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                // Controladores REST
                .antMatchers("/clientes", "/locadoras", "/locacoes").permitAll()
                .antMatchers("/cliente/{\\d+}", "/locadora/{\\d+}").permitAll()
                .antMatchers("/locacoes/{\\d+}").permitAll()
                .antMatchers("/locadoras/cidades/{\\w+}").permitAll()
                .antMatchers("/locacoes/clientes/{\\d+}").permitAll()
                .antMatchers("/locacoes/locadoras/{\\d+}").permitAll()
                // Demais linhas
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
}