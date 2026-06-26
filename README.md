# Selenium_UI_AutomationFW

Java 17 + Selenium + TestNG automation framework for the Automation Exercise application.

## Architecture

The framework is organized into clear layers:

- `src/main/java`
  - `drivers` - browser creation and WebDriver lifecycle
  - `action` - reusable browser and element actions
  - `pages` - page objects for application screens
  - `pages/components` - reusable UI fragments such as the navigation bar
  - `validations` - soft and hard assertion wrappers
  - `utils` - waits, readers, logging, and helpers
  - `listeners` - TestNG lifecycle hooks, screenshots, recordings, and reporting
  - `report` / `media` - Allure, screenshots, and video capture
- `src/test/java`
  - test classes that drive user flows through page objects
- `src/test/resources`
  - suite XML, JSON test data, downloads, and report assets

## How the framework works

1. A test extends `BaseTest`.
2. `BaseTest` exposes the shared `DriverManager`.
3. `DriverManager` reads browser configuration and creates the correct driver through a browser factory.
4. Tests interact with page objects and components instead of raw Selenium calls.
5. `ElementAction` centralizes clicks, typing, hovering, scrolling, and waits.
6. `TestNGListeners` handles setup, cleanup, screenshots, recordings, and Allure report generation.
7. Test data is read from JSON files using `JsonReader`.
8. Assertions are handled through:
   - `validation()` for soft assertions
   - `verification()` for hard assertions

## Execution flow

`Test -> BaseTest -> DriverManager -> Browser Factory -> WebDriver -> Page Objects -> Actions/Validations -> TestNG Listeners`

## Design patterns used

### Page Object Model
Each page is represented by a class in `pages/`. Locators and actions stay inside the page class so tests remain clean and readable.

### Component Object Model
Reusable UI parts, such as `NavigationBarComponent`, are separated into component classes and shared across pages.

### Factory Method
`Browsers` selects the correct concrete driver factory (`ChromeFactory`, `EdgeFactory`, or `FirefoxFactory`) based on configuration.

### Facade
`DriverManager` acts as a single entry point for browser, element, frame, alert, and assertion services.

### ThreadLocal WebDriver
Each test thread gets its own WebDriver instance, which supports safe parallel execution.

### Listener-based lifecycle management
`TestNGListeners` centralizes suite setup, teardown, screenshots, recordings, and reporting.

## Key packages

| Package | Responsibility |
|---|---|
| `drivers` | WebDriver creation and management |
| `action` | Shared browser and element actions |
| `pages` | Page objects |
| `pages/components` | Reusable UI fragments |
| `utils` | Waits, readers, logs, helpers |
| `validations` | Assertion wrappers |
| `listeners` | TestNG hooks and reporting |

## Test data

JSON test data lives in:

`src/test/resources/test-data/`

Examples:

- `products_Test_data.json`
- `Cart_Test_data.json`
- `Checkout_Test_data.json`

## Running tests

Tests are executed through Maven and the TestNG suite:

`src/test/resources/suites/ecommerce-automation-suite.xml`

Typical command:

```bash
mvn test
```

## Reporting

The framework generates:

- screenshots
- video recordings
- logs
- Allure reports

Outputs are written under `test-output/`.

## Browser configuration

Browser and execution settings are loaded from properties files by `PropertyReader`.

Common settings include:

- browser
- execution type
- default wait
- base URL

## Notes

- The framework is built for maintainable UI automation with reusable actions and page objects.
- Assertions, waits, and reporting are centralized to reduce duplication across tests.
