## Task Kauã base CRUD

### Crie uma API REST que tenha 5 endpoints:
	- POST -> createChampion 
		- body da request:
			```
				"name": "Yasuo",
				"ability": "wind",
				"championType": "carry",
				"human": true,
				"ativo": "true"
			```
		- responseCode 201, emptyBody
		
	- GET -> findById 
		-> path /id
		-> responseCode 200, Champion
		
	- PUT -> inactivate 
		-> path /id
		-> responseCode 200, emptyBody
	
	- PUT -> activate 
		-> path /id
		-> responseCode 200, emptyBody
	
	- GET -> getAll
		-> path /
		-> responseCode 200, Champion[]

### Tecnologias a serem utilizadas
	- Java 21
	- SpringBoot e Spring Data JPA
	- H2 (banco em memoria)
	- Swagger para documentar os endpoints

### Regras
	- Fazer o projeto do 0 com o Spring Initializer
	- Montar toda a estrutura de pacotes
	- Tentar não utilizar chatGPT
	- Tentar utiliar DTOS ao invés da própria entidade nos retornos e requests (para isso utilizar o ModelMapper)