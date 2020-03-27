package com.nelioalves.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nelioalves.cursomc.security.JWTAuthenticationFilter;
import com.nelioalves.cursomc.security.JWTAuthorizationFilter;
import com.nelioalves.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity

//Permite colocarmos anotações de pre-autorização nos endpoints - @PreAuthorize("hasAnyRole('ADMIN')")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Injetamos a interface, o Spring vai procurar quem está implementando
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	// Para que possamos acessar o h2-console
	@Autowired
	private Environment env;

	// os vetores PUBLIC MATCHERS indicam quais rotas estão liberadas por padrão
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	// esse caminho só permite recuperar os dados , só GET
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**", "/estados/**" };

	private static final String[] PUBLIC_MATCHERS_POST = { "/clientes/**", "/auth/forgot/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Se nos meus profiles ativos do projeto estive o profile "test" significa que
		// vou
		// querer acessar o h2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			// esse comando liberara o acesso ao h2 e sua autenticação
			http.headers().frameOptions().disable();
		}

		// Se tiver alguma configuração de CORS feita, esse método ativa ela
		http.cors().and().csrf().disable();

		// Esse metodo vai permitir que os Public_Matchers tenham acesso sem probemas
		// todo os outros tipo s de requests exigirão autenticação
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
				// só pode fazer post/get quem estiver na lista o Public Matchers
				.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().anyRequest().authenticated();

		// Filto de Autenticacao
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		// Filto de Autorização
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));

		// Para assegurar que nosso BackEnd não criará sessão de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	// Para que possamos implementar a autenticacao dos usuários precisamos
	// fazer uma sobrecarga para informar quem é o UserDetail(o cara que é capaz de
	// procurar o usuario por email) que estamos usando e qual o algoitmo de
	// codificação da senha que usaremos
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		// Por padrão não são permitidas requisições de CROSS-ORIGIN
		// Para que sejam aceitas requisições de multiplas fontes (CROSS-ORIGIN) em
		// nosso Back
		// precisamos configurar essa liberação através desse método, inclusive especificando quais
		// verbos HTML ou allowedMethods serão aceitos
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	// Quando armazenamos uma senha no BD não armazenamos a senha como ela é, ela
	// tem que ser encriptada para isso.
	// Essa Bean irá encodar a senha do usuário de forma encriptada
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
