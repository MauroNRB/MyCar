# MyCar — Aplicativo Companheiro Automotivo Android

**MyCar** é um aplicativo de acompanhamento e gerenciamento pessoal de veículos desenvolvido em **Java para Android**. O projeto foi estruturado para demonstrar o uso de interfaces customizadas em XML, persistência de dados local com SQLite, consumo de localização GPS em tempo real, leitura do acelerômetro e automação de compilação via GitHub Actions.

---

## 🚘 Recursos e Funcionalidades

1. **Dashboard Automotivo**: Painel principal com resumo do veículo cadastrado, odômetro atual, velocidade GPS ao vivo, distância percorrida no dia e atalhos de navegação rápida.
2. **Gerenciamento do Veículo**: Cadastro e edição completa das informações do veículo (Nome/Apelido, Marca, Modelo, Ano de Fabricação/Modelo, Placa, Tipo de Combustível, Motorização e Odômetro).
3. **Controle de Quilometragem**: Registro manual de leituras do odômetro com data, hora e observações. Exibição de histórico cronológico com cálculo automático da diferença percorrida entre registros.
4. **Gestão de Manutenções**: Cadastro de revisões realizadas e agendamento de manutenções futuras por data e quilometragem com alertas de status (*Em dia*, *Próxima manutenção*, *Manutenção vencida*). Suporte a filtros por categoria (Óleo, Pneus, Freios, Filtros, Bateria, Revisão, Outros) e cálculo de custos.
5. **Velocímetro Customizado (`SpeedometerView`)**: Componente gráfico exclusivo desenhado dinamicamente com as APIs `Canvas` e `Paint` do Android. Possui escala semicircular de 0 a 240 km/h, marcadores principais e secundários, agulha animada, valor central destacado e arco de alerta vermelho para altas velocidades.
6. **Teste de Aceleração 0–100 km/h**: Tela com máquina de estados (*Aguardando GPS*, *Pronto para iniciar*, *Medindo aceleração*, *Teste concluído*, *Medição inválida*). Cronometra o tempo de aceleração a partir do movimento real do veículo via GPS e salva os resultados no banco de dados.
7. **Rastreamento de Viagens**: Permite iniciar e finalizar trajetos, acumulando a distância percorrida com filtro de precisão GPS e registrando dados no histórico.
8. **Diagnóstico de Sensores e Gráfico Vivo (`AccelerationGraphView`)**: Leitura ao vivo dos eixos X, Y e Z do acelerômetro e cálculo da intensidade do movimento. Desenho em tempo real da aceleração através de um componente customizado.
9. **Configurações e Privacidade**: Explicação transparente sobre o uso dos dados de localização e gerenciamento de permissões do sistema.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java (Compatível com JDK 17).
- **IDE Recomendada**: Android Studio Ladybug / Jellyfish ou superior.
- **Min SDK**: API 26 (Android 8.0 Oreo).
- **Target SDK**: API 34 (Android 14).
- **Interface**: Layouts XML nativos, `AppCompatActivity`, `Fragment`, `MaterialCardView`, `BottomNavigationView`.
- **Componentes Customizados**: Extended `View` em Java (`SpeedometerView` e `AccelerationGraphView`) utilizando `Canvas`, `Paint`, `Path` e `ValueAnimator`.
- **Banco de Dados**: SQLite nativo via `SQLiteOpenHelper`.
- **Localização**: Google Play Services Location API (`FusedLocationProviderClient`) com fallback para o `LocationManager` nativo.
- **Sensores**: Android `SensorManager` (Acelerômetro).
- **Gerenciador de Dependências**: Gradle (Groovy DSL) com Gradle Wrapper 9.4.
- **CI/CD**: GitHub Actions (`.github/workflows/android.yml`).

---

## 📁 Estrutura do Projeto

```
MyCar/
├── .github/
│   └── workflows/
│       └── android.yml                # Workflow do GitHub Actions para compilação do APK
├── app/
│   ├── build.gradle                   # Configuração do módulo Android (Groovy)
│   └── src/
│       ├── main/
│       │   ├── java/com/mycar/app/
│       │   │   ├── activities/        # MainActivity e controladores principais
│       │   │   ├── adapters/          # Adapters para RecyclerView (Quilometragem, Manutenção, etc.)
│       │   │   ├── database/          # DatabaseHelper (SQLiteOpenHelper e operações CRUD)
│       │   │   ├── fragments/         # Telas da aplicação (Dashboard, Veículo, Velocímetro, etc.)
│       │   │   ├── location/          # LocationHelper (Integração com GPS)
│       │   │   ├── models/            # Classes de modelo de dados (Vehicle, MaintenanceRecord, etc.)
│       │   │   ├── sensors/           # SensorHelper (Acelerômetro)
│       │   │   ├── utils/             # FormatUtils (Datas, moedas, distâncias)
│       │   │   └── views/             # Views customizadas (SpeedometerView, AccelerationGraphView)
│       │   ├── res/
│       │   │   ├── drawable/          # Vetores e formas customizadas
│       │   │   ├── layout/            # Layouts XML responsivos
│       │   │   ├── menu/              # Menu da BottomNavigationView
│       │   │   └── values/            # strings.xml (pt-BR), colors.xml, themes.xml, dimens.xml
│       │   └── AndroidManifest.xml    # Permissões e declaração de componentes
│       └── test/java/com/mycar/app/   # Testes unitários JUnit
├── build.gradle                       # Gradle do projeto raiz
├── gradle.properties                  # Configurações de build e AndroidX
├── settings.gradle                    # Configuração de repositórios do projeto
└── README.md                          # Documentação completa em Português
```

---

## 🚀 Como Abrir e Compilar o Projeto no Android Studio

1. Abra o **Android Studio**.
2. Clique em **Open** e selecione a pasta raiz do projeto `MyCar`.
3. Aguarde o Android Studio realizar o **Gradle Sync**.
4. Certifique-se de estar utilizando um JDK 17 configurado no Android Studio (*Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK*).

---

## 📱 Execução em Dispositivo Físico Android

Para testar as funcionalidades de GPS e Acelerômetro, recomenda-se a execução em um smartphone real:

1. No smartphone, ative a **Depuração USB** nas *Opções do Desenvolvedor*.
2. Conecte o dispositivo via cabo USB.
3. No Android Studio, selecione o seu dispositivo na barra superior.
4. Clique no botão **Run (Shift + F10)**.

### Permissão de Localização
Ao abrir o aplicativo pela primeira vez, uma caixa de diálogo solicitará permissão de **Localização Precisa**. A permissão é necessária para o funcionamento do velocímetro e dos testes de aceleração.

---

## 🧪 Testes Unitários

O projeto conta com testes unitários JUnit cobrindo validações de regras de negócio, conversão de unidades e status de manutenções:

### Como rodar os testes localmente via terminal:
```bash
./gradlew test
```

### Casos de teste implementados:
- `OdometerValidationTest`: Valida rejeição de odômetro negativo ou menor que o registro anterior.
- `SpeedConversionTest`: Valida a conversão correta de metros por segundo (m/s) para km/h (fator 3.6).
- `DistanceCalculationTest`: Valida o cálculo de conversão de metros para quilômetros.
- `MaintenanceStatusTest`: Valida o cálculo de manutenções vencidas com base na quilometragem do veículo.
- `AccelerationTimingTest`: Valida a precisão da cronometragem do tempo transcorrido no teste 0–100 km/h.

---

## ⚙️ Gerando o APK

### Localmente no terminal:
Para gerar o APK de depuração (debug):
```bash
./gradlew assembleDebug
```
O arquivo APK será gerado no caminho:
`app/build/outputs/apk/debug/app-debug.apk`

---

## 🤖 GitHub Actions e APK Automático

A cada atualização no repositório GitHub (`push` ou `pull request` na branch principal), o workflow `.github/workflows/android.yml` realiza automaticamente:
1. Configuração do ambiente Ubuntu com JDK 17.
2. Execução dos testes unitários com `./gradlew test`.
3. Compilação do aplicativo com `./gradlew assembleDebug`.
4. Disponibilização do arquivo **`app-debug.apk`** como artefato para download na aba **Actions** do GitHub.

---

## ⚠️ Limitações Conhecidas e Considerações de Segurança

- **Precisão do GPS**: A frequência de atualização do GPS em smartphones convencionais varia entre 1 Hz (uma leitura por segundo) e 5 Hz. Flutuações de sinal e sombras de edifícios podem afetar momentaneamente a leitura da velocidade instantânea.
- **Teste 0–100 km/h**: O teste depende da amostragem do GPS. Recomenda-se realizar o teste apenas em condições ideais de sinal aberto.
- **AVISO DE SEGURANÇA**: O teste de aceleração e a interação com o aplicativo devem ser operados por um passageiro ou em pista fechada e controlada. **Nunca manuseie o celular enquanto estiver dirigindo.**

---

## 🔒 Privacidade de Dados

O MyCar é um aplicativo **100% offline e local**. Todos os seus dados de veículos, registros de quilometragem, manutenções, viagens e leituras de sensores são salvos exclusivamente no banco de dados SQLite interno do seu dispositivo e **nunca** são transmitidos para servidores externos.
