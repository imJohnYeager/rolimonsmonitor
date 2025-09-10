# === STAGE DE BUILD: Compilação da aplicação ===
# Usa a imagem oficial do Maven para compilar o projeto.
FROM maven:3.8.8-openjdk-17-slim AS build

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código-fonte.
COPY pom.xml .
COPY src ./src

# Compila o projeto e empacota em um JAR executável, pulando os testes.
RUN mvn clean package -DskipTests

# === STAGE DE EXECUÇÃO: Ambiente de produção ===
# Usa uma imagem base mais leve do OpenJDK.
FROM openjdk:17-jre-slim

# Define o diretório de trabalho.
WORKDIR /app

# Instala o Chrome e suas dependências no contêiner.
# A instalação de um ChromeDriver separado não é necessária para o Selenium 4+,
# pois o Selenium Manager o gerencia automaticamente.
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    libnss3-dev \
    libgconf-2-4 \
    libasound2 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libfontconfig1 \
    libgdk-pixbuf2.0-0 \
    libgtk-3-0 \
    libnspr4 \
    libxcomposite1 \
    libxrandr2 \
    libxss1 \
    ca-certificates \
    fonts-liberation \
    lsb-release \
    xdg-utils \
    libu2f-udev \
    libvulkan1 \
    libu2f-udev \
    --no-install-recommends && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /etc/apt/trusted.gpg.d/google-archive.gpg && \
    sh -c 'echo "deb [arch=amd64] https://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list' && \
    apt-get update && apt-get install -y google-chrome-stable && \
    apt-get autoremove -y && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copia o arquivo JAR da etapa de build.
COPY --from=build /app/target/rolimonsmonitor-0.0.1-SNAPSHOT.jar .

# Define o ponto de entrada da aplicação.
# Adicionamos a flag --no-sandbox para compatibilidade com o ambiente do contêiner.
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "rolimonsmonitor-0.0.1-SNAPSHOT.jar"]