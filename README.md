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
