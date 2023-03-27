# Getting Started

### Reference Documentation
Para rodar o teste de integração, execute: mvn clean test

Para rodar o projeto, execute: mvn spring-boot:run

Para acessar o Swagger, acesse: http://localhost:8080/swagger-ui/index.html

Para indexar os animais de todos os parceiros em banco de dados, execute no terminal: curl --location --request POST 'http://localhost:8080/v1/adoptions/data-loader/'

A indexação pode demorar um pouco mais de 1 minuto para finalizar. Para acompanhar o processo, execute (informando o identificador gerado no passo anterior): curl --location -g --request GET 'http://localhost:8080/v1/adoptions/data-loader/{id}'

Para consultar a primeira página dos animais indexados, execute: curl --location --request GET 'http://localhost:8080/v1/adoptions/search'

Para trocar o status de um animal para Adotado, por exemplo, execute (informando o identificador do animal): curl --location -g --request POST 'http://localhost:8080/v1/adoptions/change-status/{id}/status/Adotado'