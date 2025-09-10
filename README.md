# README (Versão em Português)

> **⚠️ Isenção de Responsabilidade:** Este projeto é fornecido como exemplo para fins educacionais e de estudo. Seu uso deve ser feito de forma facultativa, em conformidade com os termos de serviço das plataformas e APIs utilizadas. O desenvolvedor não se responsabiliza por qualquer uso indevido.

---

# 🤖 RolimonsMonitor

## 🚀 Sobre o Projeto
Este é um aplicativo Spring Boot ☕️, desenvolvido para monitorar anúncios de trocas na plataforma [Rolimons.com](https://www.rolimons.com/trades). O monitor verifica a página de trocas a cada 5 segundos ⏱️, filtra os anúncios de acordo com critérios pré-definidos e envia notificações detalhadas para um canal do Discord quando um anúncio relevante é encontrado.

O projeto utiliza o framework Spring para gerenciar a aplicação e o Selenium para automatizar a navegação e a extração de dados da página web.

## ✨ Funcionalidades
* **Monitoramento Contínuo**: A aplicação executa uma verificação a cada 5 segundos para encontrar novos anúncios de troca.
* **Web Scraping**: Utiliza Selenium para coletar informações dos anúncios diretamente da página de trocas do Rolimons.
* **Filtragem de Anúncios**: Processa os anúncios encontrados para validar se eles atendem aos critérios especificados em `FiltrosAnuncio`.
* **Notificações no Discord**: Envia mensagens formatadas para um canal do Discord contendo detalhes do anúncio, como usuário, valor dos itens, tags e o link direto para a troca.
* **Cache de Anúncios**: Mantém um cache local de anúncios já processados para evitar o envio de notificações duplicadas.

## 💻 Tecnologias Utilizadas
* **Spring Boot**: Framework para a construção da aplicação.
* **Selenium**: Biblioteca para automação de navegadores, usada para o web scraping.
* **Jsoup**: Biblioteca para parsear HTML (mencionado no `pom.xml`).
* **Maven**: Ferramenta de gerenciamento de dependências e construção.
* **Java 17**: Linguagem de programação.

## 📂 Estrutura do Projeto
O código-fonte principal está organizado nos seguintes pacotes:
* `com.johnyeager.rtrading.rolimonsmonitor`: Contém a classe principal da aplicação, `RolimonsMonitorApplication`.
* `com.johnyeager.rtrading.rolimonsmonitor.services`: Contém as classes de serviço que executam a lógica de negócio, como `RolimonsService` (monitoramento) e `DiscordService` (envio de notificações).
* `com.johnyeager.rtrading.rolimonsmonitor.model`: Contém as classes de modelo de dados, como `Anuncio` e `FiltrosAnuncio`.

## ⚙️ Configuração e Execução
1.  **Clonar o Repositório**:
    `git clone <URL_do_repositorio>`
2.  **Configurar o Discord**: Embora não esteja presente nos arquivos fornecidos, é necessário configurar um webhook do Discord para que a aplicação possa enviar as mensagens. A classe `DiscordNotifier` é responsável por essa funcionalidade.
3.  **Executar a Aplicação**: Navegue até o diretório do projeto e execute o comando Maven:
    `./mvnw spring-boot:run`

---

# README (English Version)

> **⚠️ Disclaimer:** This project is provided as an example for educational and study purposes. Its use should be optional, in compliance with the terms of service of the platforms and APIs used. The developer is not responsible for any misuse.

---

# 🤖 RolimonsMonitor

## 🚀 About the Project
This is a Spring Boot ☕️ application, developed to monitor trade listings on the [Rolimons.com](https://www.rolimons.com/trades) platform. The monitor checks the trades page every 5 seconds ⏱️, filters listings according to predefined criteria, and sends detailed notifications to a Discord channel when a relevant listing is found.

The project uses the Spring framework to manage the application and Selenium to automate navigation and data extraction from the web page.

## ✨ Features
* **Continuous Monitoring**: The application runs a check every 5 seconds to find new trade listings.
* **Web Scraping**: Uses Selenium to collect information from listings directly from the Rolimons trades page.
* **Listing Filtering**: Processes the found listings to validate if they meet the criteria specified in `FiltrosAnuncio`.
* **Discord Notifications**: Sends formatted messages to a Discord channel containing listing details, such as the user, item value, tags, and a direct link to the trade.
* **Listing Cache**: Maintains a local cache of already processed listings to prevent sending duplicate notifications.

## 💻 Technologies Used
* **Spring Boot**: Framework for building the application.
* **Selenium**: Library for browser automation, used for web scraping.
* **Jsoup**: Library for parsing HTML (mentioned in `pom.xml`).
* **Maven**: Dependency management and build tool.
* **Java 17**: Programming language.

## 📂 Project Structure
The main source code is organized into the following packages:
* `com.johnyeager.rtrading.rolimonsmonitor`: Contains the main application class, `RolimonsMonitorApplication`.
* `com.johnyeager.rtrading.rolimonsmonitor.services`: Contains the service classes that execute business logic, such as `RolimonsService` (monitoring) and `DiscordService` (notification sending).
* `com.johnyeager.rtrading.rolimonsmonitor.model`: Contains the data model classes, such as `Anuncio` and `FiltrosAnuncio`.

## ⚙️ Configuration and Execution
1.  **Clone the Repository**:
    `git clone <repository_URL>`
2.  **Configure Discord**: Although not present in the provided files, it is necessary to configure a Discord webhook for the application to be able to send messages. The `DiscordNotifier` class is responsible for this functionality.
3.  **Run the Application**: Navigate to the project directory and execute the Maven command:
    `./mvnw spring-boot:run`
