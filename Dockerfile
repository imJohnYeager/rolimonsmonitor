# === STAGE DE BUILD: Compilação da aplicação ===
# Usa uma imagem oficial do Maven e OpenJDK que é válida no Docker Hub.
FROM maven:3.9-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de build do Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src

# Garante que o script Maven seja executável
RUN chmod +x ./mvnw

# Compila o projeto e empacota em um JAR
RUN ./mvnw clean package -DskipTests

# === STAGE DE EXECUÇÃO: Ambiente de produção com Chrome ===
# Usa a imagem oficial do Selenium, que já tem o Chrome e o JDK instalados.
FROM selenium/standalone-chrome:latest

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build
COPY --from=build /app/target/rolimonsmonitor-0.0.1-SNAPSHOT.jar .

# Define o ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "rolimonsmonitor-0.0.1-SNAPSHOT.jar"]