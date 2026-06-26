# Selenium UI Automation Framework - Architecture

## Project Overview
A Maven-based Selenium WebDriver automation framework built with Java 17, designed for UI testing with comprehensive reporting and utilities.

---

## Directory Structure

```
Selenium_UI_AutomationFW/
│
├── pom.xml                          # Maven project configuration
├── .gitignore                       # Git ignore rules
├── .git/                            # Git repository
├── .idea/                           # IDE configuration
├── .mvn/                            # Maven wrapper
│
├── src/
│   ├── architecture.md              # This file - Framework architecture documentation
│   │
│   ├── main/
│   │   ├── java/
│   │   │   └── com/automationExercise/
│   │   │       ├── FileUtils.java
│   │   │       │
│   │   │       ├── action/                   # Selenium action classes
│   │   │       │   ├── AlertsAction.java
│   │   │       │   ├── BrowserActions.java
│   │   │       │   ├── ElementAction.java
│   │   │       │   └── FrameActions.java
│   │   │       │
│   │   │       ├── apis/                    # API-related utilities
│   │   │       │
│   │   │       ├── drivers/                 # WebDriver management
│   │   │       │
│   │   │       ├── listeners/               # Test listeners (e.g., retry logic)
│   │   │       │
│   │   │       ├── media/                   # Media handling (screenshots, videos)
│   │   │       │
│   │   │       ├── pages/                   # Page Object Model classes
│   │   │       │   ├── CartPage.java
│   │   │       │   ├── CheckoutPage.java
│   │   │       │   ├── PaymentPage.java
│   │   │       │   ├── ProductDetailsPage.java
│   │   │       │   ├── ProductsPage.java
│   │   │       │   ├── SignUpPage.java
│   │   │       │   ├── SignupLoginPage.java
│   │   │       │   └── components/          # Reusable page components
│   │   │       │
│   │   │       ├── report/                  # Reporting utilities
│   │   │       │
│   │   │       ├── utils/                   # General utility classes
│   │   │       │
│   │   │       └── validations/             # Assertion/validation classes
│   │   │
│   │   └── resources/                       # Configuration and properties
│   │       ├── META-INF/
│   │       ├── allure.properties            # Allure reporting config
│   │       ├── env.properties               # Environment configuration
│   │       ├── log4j2.properties            # Logging configuration
│   │       ├── seleniumGrid.properties      # Selenium Grid config
│   │       ├── vedio.properties             # Video recording config
│   │       ├── waits.properties             # Wait timeout config
│   │       └── webApp.properties            # Web application config
│   │
│   └── test/
│       ├── java/
│       │   └── com/automationExercices/
│       │       └── tests/                   # Test classes
│       │
│       └── resources/                       # Test resources
│           ├── report-example/              # Example test reports
│           └── test-data/                   # Test data files
│
├── test-output/                             # Test execution output
│
└── .gitignore                              # Git ignore configuration
```

---

## Module Descriptions

### 1. **action/** - Selenium Actions
Contains reusable Selenium WebDriver action classes:
- **AlertsAction.java**: Alert handling (accept, dismiss, send keys)
- **BrowserActions.java**: Browser operations (navigate, refresh, wait)
- **ElementAction.java**: Web element interactions (click, type, hover)
- **FrameActions.java**: Frame/iframe handling

### 2. **pages/** - Page Object Model
Implements the Page Object Model (POM) pattern:
- **Page Classes**: CartPage, CheckoutPage, PaymentPage, ProductDetailsPage, ProductsPage, SignUpPage, SignupLoginPage
- **components/**: Reusable UI components (headers, footers, navigation bars)

### 3. **drivers/** - WebDriver Management
Manages WebDriver initialization, configuration, and lifecycle

### 4. **listeners/** - Test Listeners
Implements TestNG listeners for:
- Test execution hooks (before/after)
- Retry logic for failed tests
- Custom reporting

### 5. **media/** - Media Handling
Handles:
- Screenshot capture
- Video recording during test execution

### 6. **report/** - Reporting
Integrates Allure reporting framework for:
- Test execution reports
- HTML reports
- Screenshots/videos in reports

### 7. **utils/** - General Utilities
Common utility functions:
- File operations
- Data manipulation
- Helper methods

### 8. **validations/** - Assertions
Custom assertion/validation methods for test verification

### 9. **apis/** - API Utilities
API-related utilities and helpers

---

## Configuration Files (src/main/resources/)

| File | Purpose |
|------|---------|
| `allure.properties` | Allure reporting configuration |
| `env.properties` | Environment-specific settings (dev, staging, prod) |
| `log4j2.properties` | Logging configuration |
| `seleniumGrid.properties` | Selenium Grid connection settings |
| `vedio.properties` | Video recording settings |
| `waits.properties` | Implicit/explicit wait timeout values |
| `webApp.properties` | Web application URL and credentials |

---

## Key Dependencies (Maven)

- **Selenium WebDriver**: Web browser automation
- **TestNG**: Test framework
- **Allure**: Test reporting framework
- **Log4j2**: Logging framework
- **AspectJ**: AOP support
- **Allure Environment Writer**: Environment details in reports

---

## Build & Execution

- **Build Tool**: Maven (Java 17)
- **Test Runner**: TestNG
- **Reporting**: Allure
- **Logging**: Log4j2

---

## Test Execution Flow

1. Initialize WebDriver (Chrome, Firefox, Edge, etc.)
2. Navigate to application under test
3. Use Page Objects and Actions to interact with UI
4. Assert/validate expected behavior
5. Capture screenshots/videos on failure
6. Generate Allure report

---

## Best Practices Implemented

✓ Page Object Model (POM)
✓ Separation of concerns (actions, pages, utilities)
✓ Centralized configuration management
✓ Comprehensive reporting with Allure
✓ Retry logic for flaky tests
✓ Media capture for debugging
✓ Logging and audit trail

---

## Maven Commands

### Build & Compilation

#### Clean Build
```bash
mvn clean install
```
Removes all compiled artifacts and rebuilds the project from scratch.

#### Compile Only
```bash
mvn compile
```
Compiles source code without running tests.

#### Install Dependencies
```bash
mvn clean install -DskipTests
```
Downloads dependencies and builds without executing tests.

#### Verify Project
```bash
mvn verify
```
Runs all quality checks and validation gates.

---

### Test Execution

#### Run All Tests
```bash
mvn clean test
```
Executes all test cases in the project.

#### Run Specific Test Class
```bash
mvn test -Dtest=LoginTest
```
Run a single test class (without .java extension).

#### Run Tests by Pattern
```bash
mvn test -Dtest=*SignUp*
```
Run multiple tests matching a pattern.

#### Run Specific Test Method
```bash
mvn test -Dtest=LoginTest#testValidLogin
```
Execute a single test method.

#### Skip Tests During Build
```bash
mvn clean install -DskipTests
```
Build without running any tests.

#### Run Tests in Parallel
```bash
mvn clean test -Dparallel.mode=classes -Dthread.count=3
```
Execute tests in parallel with 3 threads for faster execution.

#### Sequential Test Execution
```bash
mvn clean test -Dparallel.mode=none
```
Run tests one after another (default mode, useful for debugging).

---

### Reporting

#### Generate Allure Report
```bash
mvn allure:report
```
Generates an HTML Allure report from test results.

#### Open Allure Report
```bash
mvn allure:serve
```
Starts a local server and opens the Allure report in browser.

#### Generate Surefire Report
```bash
mvn surefire-report:report
```
Creates a Maven Surefire test report.

---

### Debugging & Troubleshooting

#### Run Tests with Debug Logging
```bash
mvn test -X
```
Enables debug logging for detailed execution information.

#### Run Single Test with Debug Output
```bash
mvn test -Dtest=LoginTest -X
```
Single test execution with verbose logging.

#### Fail at End (Run All Tests)
```bash
mvn clean test -fae
```
Continues running all tests even if some fail; reports all failures at the end.

#### Show Dependency Tree
```bash
mvn dependency:tree
```
Displays the project's dependency hierarchy.

#### Check for Dependency Issues
```bash
mvn dependency:analyze
```
Identifies unused dependencies and missing declarations.

---

### IDE & Development

#### Generate IDE Project Files
```bash
mvn eclipse:eclipse
```
For Eclipse IDE configuration.

```bash
mvn idea:idea
```
For IntelliJ IDEA configuration.

#### Update Project Dependencies
```bash
mvn dependency:resolve
```
Downloads and resolves all project dependencies.

#### Clean IDE Cache
```bash
mvn clean idea:clean
```
Removes IntelliJ IDEA artifacts.

---

### Performance & Optimization

#### Build with Offline Mode
```bash
mvn clean install -o
```
Builds using only locally cached dependencies (no network calls).

#### Skip Documentation
```bash
mvn clean test -DskipJavadoc
```
Faster build by skipping Javadoc generation.

#### Resume from Failed Module
```bash
mvn clean install -rf :module-name
```
Resume build from a specific module after failure.

---

### Advanced Options

#### Change Java Version Temporarily
```bash
mvn -Dmaven.compiler.source=17 -Dmaven.compiler.target=17 clean install
```
Override Java version from command line.

#### Set Custom Working Directory
```bash
mvn -f path/to/pom.xml clean install
```
Build using a pom.xml from different location.

#### Execute with Custom JVM Memory
```bash
MAVEN_OPTS="-Xmx2048m" mvn clean test
```
Allocate more memory to Maven JVM (useful for large projects).

#### Skip Module from Build
```bash
mvn clean install -pl \!module-to-skip
```
Build all modules except the specified one.

---

### Test Filtering & Organization

#### Run Tests by Tag/Group (TestNG)
```bash
mvn test -Dgroups=smoke,regression
```
Execute tests belonging to specific groups.

#### Exclude Tests by Tag
```bash
mvn test -DexcludedGroups=slowTests
```
Skip tests tagged with specified groups.

#### Run Tests Matching Suite Name
```bash
mvn test -Dsuites=src/test/resources/testng.xml
```
Execute tests from a specific TestNG suite file.

---

### CI/CD & Continuous Integration

#### Build for CI Pipeline
```bash
mvn clean verify -B -e
```
- `-B`: Batch mode (no interactive input)
- `-e`: Show errors and full stack traces

#### Generate Test Report for CI
```bash
mvn clean test -Dmaven.test.failure.ignore=true
```
Continues build even if tests fail (useful for CI to capture results).

#### Deploy to Repository
```bash
mvn clean deploy
```
Builds and deploys artifacts to configured repository.

---

### Common Workflow Examples

#### Local Development (Quick Test)
```bash
# Fast build with specific test
mvn test -Dtest=LoginTest -DskipITs
```

#### Pre-Commit Validation
```bash
# Clean build with all checks
mvn clean verify
```

#### Full Regression Before Release
```bash
# Parallel execution for speed
mvn clean test -Dparallel.mode=classes -Dthread.count=4 && mvn allure:report
```

#### Troubleshoot Flaky Test
```bash
# Run single test multiple times
mvn test -Dtest=CartTest -Dparallel.mode=none -X
```

---

## Running Tests from IDE

### IntelliJ IDEA
- Right-click test class → `Run 'ClassName'`
- Right-click test method → `Run 'methodName'`
- Use Run Configuration for custom settings
- View results in Test Runner panel

### Eclipse
- Right-click test class → `Run As` → `JUnit Test`
- Right-click test package → `Run As` → `TestNG Test`
- View results in TestNG Results panel

---

## Environment Configuration

### Load Environment from Properties
Tests automatically load configuration from:
- `src/main/resources/env.properties` - Environment settings
- `src/main/resources/webApp.properties` - Application URLs
- `src/main/resources/waits.properties` - Timeout settings

### Override Properties at Runtime
```bash
mvn test -Denv=staging -Dbrowser=firefox
```

### Example env.properties
```properties
environment=staging
baseUrl=https://staging.automationexercise.com
headless=false
implicitWait=10
explicitWait=15
```

---

## Troubleshooting Common Issues

### Issue: Tests Pass Locally but Fail in CI
- Check Java version: `java -version`
- Verify dependencies: `mvn dependency:tree`
- Run in offline mode: `mvn clean test -o`

### Issue: Out of Memory During Build
```bash
MAVEN_OPTS="-Xmx4096m -XX:MaxPermSize=1024m" mvn clean test
```

### Issue: Flaky Tests
```bash
# Run test multiple times
for i in {1..5}; do mvn test -Dtest=FlakeyTest || break; done
```

### Issue: Dependency Conflicts
```bash
mvn dependency:tree | grep "duplicate"
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-shade-plugin
```

---

## Quick Reference Cheatsheet

| Command | Purpose |
|---------|---------|
| `mvn clean install` | Full build with tests |
| `mvn test` | Run all tests |
| `mvn test -Dtest=LoginTest` | Run specific test |
| `mvn clean test -DskipTests` | Build without tests |
| `mvn clean test -Dparallel.mode=classes -Dthread.count=3` | Parallel test run |
| `mvn allure:serve` | View Allure report |
| `mvn verify -B` | CI/CD validation build |
| `mvn dependency:tree` | View dependencies |
| `mvn -X clean test` | Build with debug logging |
| `MAVEN_OPTS="-Xmx2048m" mvn test` | Run with custom memory |

---

## Technology Stack Details

### Core Technologies
- **Selenium WebDriver 4.x**: Browser automation
- **Java 17**: Programming language
- **Maven**: Build and dependency management
- **TestNG**: Test framework and runner

### Reporting & Logging
- **Allure**: Advanced test reporting
- **Log4j2**: Application logging
- **Allure Environment Writer**: Environment metadata in reports

### Automation Features
- **AspectJ**: AOP for cross-cutting concerns
- **Listeners**: Test lifecycle management
- **Retry Logic**: Automatic flaky test handling

### Supported Browsers
- Chrome
- Firefox
- Edge
- Safari
- Internet Explorer (legacy)

---

## Project Configuration Structure

### pom.xml Hierarchy
```
pom.xml (root)
├── Properties
│   ├── maven.compiler.source=17
│   ├── maven.compiler.target=17
│   └── Allure/AspectJ versions
├── Dependency Management
│   └── Allure BOM
└── Dependencies
    ├── Selenium WebDriver
    ├── TestNG
    ├── Allure modules
    ├── Log4j2
    └── Additional utilities
```

---

## Continuous Integration Setup

### GitHub Actions Example
```yaml
name: Run Automation Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Java
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Run tests
        run: mvn clean verify -B
      - name: Generate Allure report
        run: mvn allure:report
```

---

## Next Steps & Maintenance

### Regular Updates
- Update Selenium WebDriver monthly
- Review and update property values
- Maintain test data files
- Archive old test reports

### Code Quality
- Review test coverage metrics
- Refactor duplicate code
- Update documentation
- Run dependency checks regularly

### Performance Tuning
- Monitor test execution time
- Adjust thread count for parallel runs
- Optimize wait times
- Profile slow tests
