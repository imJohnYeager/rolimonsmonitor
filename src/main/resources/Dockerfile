# === STAGE DE BUILD: Compilação da aplicação ===
# Usa uma imagem oficial do Maven e OpenJDK para compilar a aplicação
FROM maven:3.9.6-openjdk-17-slim AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código-fonte
COPY pom.xml .
COPY src ./src

# Compila o projeto e empacota em um JAR executável, pulando os testes
RUN mvn clean package -DskipTests

# === STAGE DE EXECUÇÃO: Ambiente de produção ===
# Usa uma imagem mais leve do OpenJDK para o ambiente de execução
FROM openjdk:17-jre-slim

# Define o diretório de trabalho
WORKDIR /app

# Instala o Chrome e todas as suas dependências no contêiner.
# Selenium Manager, incluído no Selenium 4, gerencia o ChromeDriver automaticamente.
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    ca-certificates \
    fonts-liberation \
    libappindicator3-1 \
    libasound2 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libcups2 \
    libcurl4-openssl-dev \
    libgconf-2-4 \
    libgdk-pixbuf2.0-0 \
    libglib2.0-0 \
    libgtk-3-0 \
    libnss3 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxi6 \
    libxrandr2 \
    libxrender1 \
    libxss1 \
    libxtst6 \
    lsb-release \
    --no-install-recommends && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /etc/apt/trusted.gpg.d/google-archive.gpg && \
    sh -c 'echo "deb [arch=amd64] https://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list' && \
    apt-get update && apt-get install -y google-chrome-stable && \
    apt-get autoremove -y && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copia o arquivo JAR da etapa de build para o diretório de execução
COPY --from=build /app/target/rolimonsmonitor-0.0.1-SNAPSHOT.jar .

# Define o ponto de entrada da aplicação, executando o JAR
# As opções --no-sandbox e --disable-dev-shm-usage são importantes
# para que o Chrome funcione corretamente em um ambiente de contêiner.
ENV JAVA_TOOL_OPTIONS="-Dwebdriver.chrome.whitelistedIps= -Dwebdriver.chrome.args=--no-sandbox,--disable-dev-shm-usage"
ENTRYPOINT ["java", "-jar", "rolimonsmonitor-0.0.1-SNAPSHOT.jar"]