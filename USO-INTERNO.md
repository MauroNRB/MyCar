A melhor forma de pedir isso ao Gemini é fornecer um prompt bem detalhado para ele criar o projeto completo no Android Studio, com Java (sem Kotlin), interface em português e todos os requisitos da faculdade integrados.

O MyCar será um aplicativo para acompanhar o carro, registrar quilometragem, controlar manutenções, visualizar a velocidade em tempo real e medir a aceleração de 0 a 100 km/h usando o GPS e os sensores do celular.

## Prompt em inglês para o Gemini

Copie o prompt abaixo e cole no Gemini. Ele está escrito em inglês para orientar melhor a geração do código, mas exige que todas as interfaces do aplicativo estejam em português brasileiro.

Escrita

# Project: MyCar — Android Automotive Companion App

Act as a senior Android developer and software architect. Create a complete, functional Android application called **MyCar** using **Android Studio and Java only**.

The application is a college project that must demonstrate custom UI development, database persistence, geolocation, device sensors, clean code organization, and automated APK generation using GitHub Actions.

## 1\. Mandatory technology requirements

- IDE: Android Studio.
- Programming language: **Java only. Do not use Kotlin anywhere in the project.**
- UI: Android XML layouts and Java Activities, Fragments, and custom Views.
- Minimum SDK: API 26 (Android 8.0), unless a dependency requires a higher version.
- Database: SQLite using Android's built-in `SQLiteOpenHelper`.
- Location: Google Play Services Location API or Android's native location APIs.
- Sensors: Android SensorManager and available device sensors.
- Architecture: maintainable, modular, and easy for college students to understand.
- Build system: Gradle with compatible Android Gradle Plugin versions.
- Package name: `com.mycar.app`.
- App name: `MyCar`.
- Language of all user-facing content: **Brazilian Portuguese (pt-BR)**.
- Units: kilometers, kilometers per hour, liters, Brazilian Real (R$), and date format DD/MM/YYYY.

Do not generate Kotlin files, Jetpack Compose, or Kotlin DSL build scripts. Use Java source files, XML layouts, and Groovy Gradle scripts.

## 2\. App concept

MyCar is a personal vehicle management application that allows users to:

1. Register and manage vehicle information.
2. Record the current odometer reading and track kilometers driven.
3. Schedule and record maintenance services.
4. Monitor current speed using the phone's GPS.
5. Measure vehicle acceleration from 0 to 100 km/h.
6. View trip information and GPS coordinates.
7. Use the phone's accelerometer as an additional source of motion data.
8. Review saved records, statistics, and maintenance history.

The application must work without requiring a paid backend or subscription. All essential data must be stored locally using SQLite.

## 3\. Design and visual identity

Create a modern automotive dashboard inspired by premium car instrument clusters.

Visual style:

- Dark background with charcoal and black surfaces.
- Primary accent color: electric blue.
- Secondary accent color: green for successful operations.
- Orange and red for warnings and maintenance alerts.
- High-contrast typography and large numerical indicators.
- Rounded cards, subtle shadows, clear spacing, and consistent margins.
- Responsive layouts suitable for different Android screen sizes.
- Support portrait orientation and maintain usability on smaller phones.

Use native Android XML layouts. Implement reusable components and a fully custom-drawn speedometer using a Java class extending `View`.

All visible text, buttons, menus, dialogs, validation messages, notifications, labels, and error messages must be in Brazilian Portuguese.

Examples of UI labels:

- Início
- Meu veículo
- Quilometragem
- Manutenções
- Velocímetro
- Aceleração 0–100
- Histórico
- Configurações
- Salvar registro
- Registrar manutenção
- Iniciar medição
- Parar medição
- Permitir localização

## 4\. Main dashboard

Create a home screen with a vehicle dashboard.

Display:

- Registered vehicle name and model.
- Current odometer reading.
- Current GPS speed in km/h.
- Distance traveled today, when tracking data is available.
- Next scheduled maintenance.
- Number of pending maintenance items.
- Quick-action buttons for mileage, maintenance, speedometer, and acceleration tests.

If no vehicle has been registered, display an onboarding message and a button to register the first vehicle.

Update live values efficiently and avoid unnecessary database operations.

## 5\. Vehicle registration

Create a vehicle management screen with the following fields:

- Vehicle name or nickname.
- Manufacturer.
- Model.
- Manufacturing year.
- Model year.
- License plate, optional.
- Fuel type.
- Current odometer reading in kilometers.
- Engine information, optional.

Allow the user to create, edit, and view the registered vehicle.

Persist all information in SQLite. Validate mandatory fields and prevent negative mileage values.

The app must support at least one vehicle and use a database structure that can be extended to multiple vehicles later.

## 6\. Mileage management

Implement a mileage tracking feature.

Users must be able to:

- Enter the current odometer reading manually.
- Record the date and time of each reading.
- Add an optional note.
- View a chronological list of mileage records.
- See the total kilometers accumulated between recorded readings.
- See the difference between the previous reading and the new reading.
- View a simple custom graph of mileage history.

Reject an odometer reading lower than the previous accepted reading, unless the user explicitly edits a historical record with appropriate validation.

Do not automatically treat GPS distance as the actual odometer reading. Keep manual odometer readings separate from GPS-estimated distance.

## 7\. Maintenance management

Create a complete maintenance module.

Users can register maintenance records containing:

- Service title.
- Category, such as oil change, tires, brakes, filters, battery, inspection, or other.
- Date performed.
- Odometer reading at the time of service.
- Cost in Brazilian Real.
- Workshop or service provider, optional.
- Notes, optional.

Users can also schedule future maintenance based on:

- Target date.
- Target odometer reading.
- Service category.
- Reminder notes.

Show maintenance status:

- Em dia
- Próxima manutenção
- Manutenção vencida

Calculate mileage-based reminders only when a valid current odometer reading exists.

Allow editing and deleting records with a confirmation dialog. Display maintenance history sorted by date, with the option to filter by category.

Store maintenance records in SQLite.

## 8\. Custom speedometer View — mandatory custom component

Develop a real custom graphical component in Java:

`SpeedometerView.java`

The class must extend Android's `View` and draw the instrument using the Android Canvas and Paint APIs.

Requirements:

- Semicircular or circular speedometer.
- Scale from 0 to 240 km/h.
- Major and minor tick marks.
- Numerical labels.
- Animated needle.
- Large central speed value.
- Unit label: km/h.
- Smooth transitions between speed values.
- Adaptive sizing based on the available view dimensions.
- Dark dashboard background and blue accent arc.
- Red visual warning at high speeds.
- Correct handling of invalid or unavailable GPS speed.
- Customizable attributes where practical.

Expose a Java method such as `setSpeed(float speedKmh)` to update the display.

Do not use a static image to simulate the speedometer. The graphics must actually be drawn programmatically.

Integrate this custom View into an XML layout and display live GPS speed.

Use the GPS-reported speed when available. Convert meters per second to kilometers per hour using the correct conversion factor of 3.6.

When speed is unavailable, show an appropriate Portuguese message instead of presenting an invented speed.

## 9\. GPS and geolocation

Implement location tracking using the Android location APIs.

Features:

- Request location permission at runtime.
- Explain why location permission is needed before requesting it.
- Retrieve latitude, longitude, speed, accuracy, and timestamps when available.
- Display the current speed on the custom speedometer.
- Show the current coordinates on a diagnostic screen.
- Allow the user to start and stop a trip.
- Calculate GPS-estimated trip distance from consecutive valid location points.
- Save trip start time, end time, estimated distance, and available GPS information in SQLite.
- Display trip history.

Improve distance calculation by rejecting clearly invalid location points and handling poor GPS accuracy.

Handle permission denial, GPS disabled, unavailable location, and device limitations gracefully.

Use location updates only when required, and stop them when the relevant activity or trip tracking is stopped.

Request background location permission only if a genuinely necessary background tracking feature is implemented. Do not require background location for ordinary foreground use.

Include clear privacy messaging explaining what location data is stored and why.

## 10\. Acceleration test: 0–100 km/h

Create a dedicated screen named `Teste de Aceleração`.

Features:

- Large numerical speed indicator.
- Current measurement state.
- Start and stop controls.
- Measured elapsed time in seconds.
- Recorded starting and finishing timestamps.
- GPS speed and accuracy checks.
- A history of previous tests.
- Save completed results to SQLite.

Measurement logic:

1. Wait for a valid GPS signal and adequate accuracy.
2. Require the vehicle to be effectively stationary before arming the test.
3. Use GPS speed measurements rather than touchscreen animations or simulated values.
4. Start timing when measured speed crosses a configurable threshold close to zero.
5. Record elapsed time when speed reaches or crosses 100 km/h.
6. Interpolate between valid consecutive GPS speed samples when appropriate, documenting the limitations.
7. Reject invalid measurements, GPS outages, poor accuracy, and implausible speed jumps.
8. Allow cancellation and manual stopping.
9. Save only valid completed measurements as successful results.
10. Display a warning that GPS sampling frequency and accuracy affect the result.

Use a clear state machine, for example:

- Aguardando GPS
- Pronto para iniciar
- Medindo aceleração
- Teste concluído
- Medição inválida

Do not fabricate a 0–100 result when the GPS has not recorded sufficient data.

Add a prominent safety warning in Portuguese: this feature must only be used on a closed, controlled track by a qualified driver, with a passenger operating the phone or another safe setup. Never encourage the driver to interact with the phone while driving.

Do not require the user to exceed speed limits on public roads.

If the device cannot provide reliable GPS speed measurements, explain this limitation in the UI.

## 11\. Device sensor integration — mandatory

Use Android's `SensorManager` and the accelerometer when available.

Implement a Java class responsible for:

- Detecting whether an accelerometer exists.
- Registering and unregistering the sensor listener appropriately.
- Reading X, Y, and Z acceleration values.
- Displaying readings on a diagnostic screen.
- Calculating a simple acceleration magnitude or filtered movement indicator.
- Showing clear sensor availability status.

Optionally integrate the gyroscope or magnetometer when available.

Use sensor data to display a live movement visualization or a custom acceleration graph.

Do not claim that the phone's accelerometer directly measures the vehicle's true longitudinal acceleration without accounting for phone orientation, gravity, vibration, and sensor calibration.

Do not use raw accelerometer readings as a replacement for GPS speed in the 0–100 km/h test.

Handle missing sensors gracefully.

## 12\. Additional custom visualization

Implement at least one additional custom-drawn graphical component, preferably:

`AccelerationGraphView.java`

It should:

- Extend Android `View`.
- Draw a grid, axes, labels, and a moving data line using Canvas and Paint.
- Display recent sensor readings or acceleration test samples.
- Update efficiently without blocking the UI thread.
- Display an empty-state message when no data exists.

The graph must use real recorded data and must not be a static illustration.

## 13\. SQLite database

Implement a database helper class:

`DatabaseHelper.java`

Use SQLiteOpenHelper with appropriate schema creation, versioning, and upgrade handling.

Create tables for:

- vehicles
- mileage\_records
- maintenance\_records
- maintenance\_reminders
- trips
- acceleration\_tests
- sensor\_records, if needed

Use primary keys, foreign keys where appropriate, indexes when useful, and transactions for related operations.

Requirements:

- CRUD operations for the relevant entities.
- Parameterized SQL statements.
- Cursor cleanup and proper resource handling.
- Validation before inserts and updates.
- Consistent date and timestamp formats.
- No hardcoded fake history presented as actual user data.
- Database queries must be separated from UI code as much as practical.

Create Java model classes and repository or data-access classes where appropriate.

## 14\. Navigation and screens

Implement the following screens:

1. Dashboard
2. Vehicle registration and editing
3. Mileage registration
4. Mileage history
5. Maintenance list
6. Add/edit maintenance
7. Speedometer
8. Acceleration test
9. Trip tracking and trip history
10. Sensor diagnostics and live graph
11. Settings and privacy information

Use a clear navigation structure with a bottom navigation bar or another consistent native Android navigation pattern.

All screens must have functional navigation, working buttons, appropriate empty states, loading states where necessary, and error handling.

## 15\. Permissions and Android lifecycle

Implement permissions and lifecycle management correctly.

Handle:

- Location permission.
- Fine and coarse location access as appropriate.
- Sensor availability.
- Activity recreation.
- Sensor listener registration and cleanup.
- GPS update subscription and removal.
- Screen rotation where applicable.
- App backgrounding and resuming.
- Permission denial and permission revocation.

Use Android-compatible APIs for the selected minimum SDK.

Avoid memory leaks, unnecessary GPS usage, and continuous sensor updates when the corresponding feature is inactive.

## 16\. Code organization

Use a clear Java project structure, for example:

`com.mycar.app`

- `activities`
- `fragments`
- `adapters`
- `database`
- `models`
- `repositories`
- `sensors`
- `location`
- `views`
- `utils`

Create separate Java classes for business logic and custom Views.

Include:

- `MainActivity.java`
- `DatabaseHelper.java`
- `SpeedometerView.java`
- `AccelerationGraphView.java`
- `LocationManagerHelper.java` or an equivalent location service class
- `SensorManagerHelper.java`
- Appropriate model classes and adapters.

Keep code understandable for university students. Add useful comments explaining sensor processing, GPS conversion, database operations, and acceleration timing.

Do not leave core features as TODO comments or empty methods.

## 17\. Localization

Use `res/values/strings.xml` for user-facing strings.

Set the app's default language to Brazilian Portuguese.

Examples:

- Velocidade atual
- Quilometragem atual
- Registrar quilometragem
- Histórico de manutenções
- Próxima revisão
- Custo da manutenção
- Distância percorrida
- Iniciar viagem
- Finalizar viagem
- Aguardando sinal GPS
- Sensor disponível
- Sensor indisponível
- Nenhum registro encontrado
- Permissão de localização necessária
- Salvar
- Cancelar
- Editar
- Excluir

Avoid hardcoding visible UI strings directly into Java files.

## 18\. Testing and reliability

Create unit tests for important business logic, including:

- Odometer validation.
- Distance calculations.
- Speed conversion from m/s to km/h.
- Acceleration elapsed-time calculations.
- Maintenance reminder status.
- Database CRUD operations where practical.

Use dependency injection or simple interfaces where it meaningfully improves testability, without adding unnecessary complexity.

Document manual test procedures for GPS, accelerometer readings, permissions, database persistence, and the custom Views.

## 19\. GitHub Actions and APK generation — mandatory

Create a complete GitHub Actions workflow at:

`.github/workflows/android.yml`

The workflow must:

- Run on pushes and pull requests.
- Check out the repository.
- Configure the appropriate JDK.
- Set up the Android SDK when necessary.
- Run Gradle tests.
- Build the debug APK.
- Upload the APK as a downloadable GitHub Actions artifact.
- Use a supported Gradle and Android Gradle Plugin configuration.
- Fail when compilation or tests fail.

Use a Gradle wrapper committed to the repository.

The expected debug APK path is:

`app/build/outputs/apk/debug/app-debug.apk`

Do not require signing secrets for the debug APK. Document how to create a signed release APK separately.

## 20\. Documentation

Generate a comprehensive `README.md` in Portuguese containing:

- Project description.
- Features.
- Technologies used.
- Requirements for Android Studio.
- How to open and compile the project.
- How to run the app on a physical Android device.
- How to grant location permissions.
- How to test GPS and accelerometer functionality.
- How to run unit tests.
- How to build the APK locally.
- How to download the APK generated by GitHub Actions.
- Project directory structure.
- Known limitations of GPS speed and 0–100 km/h measurements.
- Privacy considerations.

## 21\. Deliverables and execution instructions

Generate a complete Android Studio project, not merely a prototype, a design mockup, or isolated code snippets.

Provide all essential files, including:

- Java source files.
- XML layouts.
- XML drawables and themes.
- Manifest and permissions.
- Strings and other resources.
- Database implementation.
- Gradle configuration using Groovy.
- Gradle wrapper.
- GitHub Actions workflow.
- Tests.
- README documentation.

Ensure all referenced classes, layouts, resources, IDs, and manifest entries exist and match their actual names.

Before finishing:

1. Review the project for compilation errors.
2. Verify that no Kotlin source files or Kotlin DSL scripts are used.
3. Verify that all required features have functional implementations.
4. Verify that database records persist after closing and reopening the app.
5. Verify that the custom speedometer and graph display real data.
6. Verify that location and sensor listeners are correctly managed.
7. Verify that the GitHub Actions workflow builds and uploads the debug APK.

If you cannot create the entire project in one response, generate it in clearly numbered stages. Start with a complete compilable project skeleton and core database implementation, then implement each feature in subsequent stages. Maintain consistent package names, resource IDs, class names, and dependencies across every stage.

Do not claim that the project was compiled, tested, or that an APK was generated unless you actually performed those operations.

**Final objective:** deliver a functional, well-organized Android application called MyCar, written entirely in Java, with a Brazilian Portuguese interface, ready to open in Android Studio and suitable for demonstrating every requirement of the college assignment.

## Como usar o prompt

1. Abra o Gemini e envie o prompt completo.
2. Peça que ele gere os arquivos do projeto em etapas, caso não consiga entregar tudo de uma vez.
3. Abra o projeto no Android Studio e aguarde a sincronização do Gradle.
4. Execute primeiro em um celular Android real, porque o GPS e os sensores são importantes para a avaliação.
5. Publique o projeto no GitHub para que o workflow compile o aplicativo e disponibilize o APK como artefato.

## Como o MyCar atende aos critérios da faculdade

| Critério | Implementação prevista |
| --- | --- |
| Funcionamento geral — 2,0 | Dashboard, veículo, quilometragem e manutenção |
| View customizada — 1,0 | Velocímetro desenhado com `Canvas` e `Paint` |
| Banco de dados — 1,0 | SQLite para registros e histórico |
| Geolocalização — 1,0 | GPS, velocidade, coordenadas e trajetos |
| Sensores — 1,0 | Acelerômetro e gráfico de movimento |
| Organização e documentação — 1,0 | Classes Java separadas e README |
| Apresentação — 1,0 | Interface automotiva e demonstração funcional |
| GitHub Actions — 2,0 | Compilação automática e APK para download |

## Três cuidados importantes

- Velocímetro: use um celular real para testar. A velocidade depende da qualidade do sinal GPS e pode ficar indisponível ou variar em baixa velocidade.
- Teste de 0 a 100 km/h: não é possível garantir precisão de nível profissional apenas com o GPS do celular. Faça os testes somente em pista fechada e em condições seguras.
- APK automático: o GitHub Actions pode compilar e disponibilizar o APK, mas você ainda precisará verificar se o projeto gerado pelo Gemini compila corretamente no Android Studio.

Minha recomendação: priorize primeiro o cadastro do veículo, a quilometragem, as manutenções, o velocímetro personalizado e a persistência SQLite. Depois implemente o teste de aceleração e o gráfico dos sensores. Assim, você terá uma versão funcional para demonstrar mesmo que precise de mais tempo para aperfeiçoar as medições de GPS.