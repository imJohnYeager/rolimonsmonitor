# === STAGE DE BUILD: Compilação da aplicação ===
# Usa uma imagem oficial do Maven e OpenJDK, que já inclui as ferramentas de build.
FROM maven:3.9.6-openjdk-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de build do Maven para o contêiner
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src

# Garante que o script Maven seja executável
RUN chmod +x ./mvnw

# Compila o projeto e empacota em um JAR executável, pulando os testes.
RUN ./mvnw clean package -DskipTests

# === STAGE DE EXECUÇÃO: Ambiente de produção com Chrome ===
# Usa a imagem oficial do Selenium que já contém o Google Chrome e o JDK.
FROM selenium/standalone-chrome:latest

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR gerado na etapa de build
COPY --from=build /app/target/rolimonsmonitor-0.0.1-SNAPSHOT.jar .

# Define o ponto de entrada da aplicação
# A aplicação se conecta ao Chrome/ChromeDriver que já vem pré-configurado na imagem do Selenium.
ENTRYPOINT ["java", "-jar", "rolimonsmonitor-0.0.1-SNAPSHOT.jar"]