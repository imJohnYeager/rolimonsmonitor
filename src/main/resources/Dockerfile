# Use a imagem oficial do OpenJDK com Java 17 como base para a etapa de construção.
# flyctl usa buildpacks, mas para builds personalizados, um Dockerfile é a melhor abordagem.
FROM openjdk:17-jdk-slim AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de build do Maven para o container.
# Isso permite que o Docker utilize o cache de camadas do Maven, agilizando futuras builds.
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Garante que o script Maven seja executável
RUN chmod +x ./mvnw

# Compila o projeto e empacota em um JAR executável.
# O spring-boot-maven-plugin cria um arquivo JAR que é diretamente executável com java -jar
RUN ./mvnw clean package -DskipTests

# A etapa final do build usa uma imagem mais leve para a execução da aplicação
FROM openjdk:17-jre-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build para o diretório de execução
COPY --from=build /app/target/rolimonsmonitor-0.0.1-SNAPSHOT.jar .

# Define o ponto de entrada da aplicação, executando o JAR
ENTRYPOINT ["java", "-jar", "rolimonsmonitor-0.0.1-SNAPSHOT.jar"]