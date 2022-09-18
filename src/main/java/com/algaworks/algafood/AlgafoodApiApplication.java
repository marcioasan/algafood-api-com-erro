package com.algaworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) //5.20. Estendendo o JpaRepository para customizar o repositório base - 13'
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}
	
	//*** Para saber quantos beans gerenciados tem na aplicação
	//https://app.algaworks.com/forum/topicos/85840/specification-classe-restaurantespecs-uso-metodos-static
//	public static void main(String[] args) {
//		ApplicationContext applicationContext = SpringApplication.run(AlgafoodApiApplication.class, args);
//		String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//		Arrays.stream(allBeanNames).forEach(System.out::println); //nomes
//
//                System.out.println(allBeanNames.length); //quantidade
//                System.out.println(applicationContext.getBeanDefinitionCount()); // quantidade
//    
//	}
	

}
