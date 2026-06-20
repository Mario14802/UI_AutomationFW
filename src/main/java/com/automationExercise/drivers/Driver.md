# DriverManager Execution Flow

## 1. Test Initialization

The test starts by creating an instance of `DriverManager`.

```java
DriverManager driver = new DriverManager();
```

The `DriverManager` constructor is responsible for initializing the WebDriver and preparing all framework services.

---

## 2. Read Browser Configuration

`DriverManager` reads the browser name from the properties file.

Example:

```properties
browser=chrome
```

```java
String browser = PropertyReader.getProperty("browser");
```

This allows the framework to determine which browser should be used for the test execution.

---

## 3. Convert Browser Name to Enum

The browser string is converted into a `Browsers` enum value.

```java
Browsers browserType =
        Browsers.valueOf(browser.toUpperCase());
```

Example:

```java
Browsers.CHROME
```

Using an enum provides type safety and eliminates hardcoded string comparisons throughout the framework.

---

## 4. Select the Appropriate Factory

The `Browsers` enum acts as a factory selector.

```java
AbstractDriver abstractDriver =
        browserType.getDriverFactory();
```

Examples:

```java
Browsers.CHROME  -> new ChromeFactory()
Browsers.FIREFOX -> new FirefoxFactory()
```

The enum determines which concrete factory should be responsible for creating the driver.

---

## 5. Polymorphic Driver Creation

The returned object is stored as an `AbstractDriver` reference.

```java
AbstractDriver abstractDriver =
        browserType.getDriverFactory();
```

Although the reference type is `AbstractDriver`, the actual object is a concrete implementation such as:

```java
ChromeFactory
```

or

```java
FirefoxFactory
```

When:

```java
abstractDriver.createDriver();
```

is executed, Java uses runtime polymorphism to invoke the correct implementation.

---

## 6. Determine Execution Type

Inside the concrete factory, the framework checks the execution mode.

Example:

```properties
executionType=local
```

Possible values:

* local
* localHeadless
* remote

Based on the execution type, the factory creates the appropriate Selenium driver.

Examples:

```java
new ChromeDriver(...)
```

or

```java
new RemoteWebDriver(...)
```

---

## 7. Protect the Driver

The created driver is wrapped using Selenium's `ThreadGuard`.

```java
WebDriver driver =
        ThreadGuard.protect(
                abstractDriver.createDriver()
        );
```

Purpose:

* Detect cross-thread WebDriver usage.
* Fail immediately if a driver is accessed from a thread different from the one that created it.
* Help diagnose parallel execution issues.

---

## 8. Store Driver in ThreadLocal

The protected driver is stored in a `ThreadLocal`.

```java
driverThreadLocal.set(driver);
```

This ensures that each test thread receives its own independent WebDriver instance.

Conceptually:

```text
Thread 1 -> ChromeDriver #1
Thread 2 -> ChromeDriver #2
Thread 3 -> ChromeDriver #3
```

This prevents interference between parallel test executions.

---

## 9. DriverManager as a Framework Facade

After initialization, `DriverManager` acts as the single entry point to the framework.

Instead of directly interacting with Selenium's `WebDriver`, tests interact with framework services.

Examples:

```java
driver.element()
driver.browser()
driver.frame()
driver.alert()
driver.validation()
driver.verification()
```

These methods provide access to specialized action and validation classes.

---

## 10. Driver Retrieval

Whenever an action class requires the driver, it retrieves it from the current thread.

```java
public WebDriver get() {
    return driverThreadLocal.get();
}
```

The correct driver instance is returned automatically based on the executing thread.

---

# Overall Sequence

```text
Test
 │
 ▼
Create DriverManager
 │
 ▼
Read browser from properties
 │
 ▼
Convert to Browsers enum
 │
 ▼
Get corresponding factory
 │
 ▼
AbstractDriver reference
 │
 ▼
Polymorphic createDriver()
 │
 ▼
Concrete Factory
(ChromeFactory / FirefoxFactory)
 │
 ▼
Check execution type
 │
 ▼
Create Selenium WebDriver
 │
 ▼
Wrap with ThreadGuard
 │
 ▼
Store in ThreadLocal
 │
 ▼
Expose framework services
(element, browser, frame, alert,
 validation, verification)
 │
 ▼
Test Execution
```

# Design Patterns Used

### Factory Method Pattern

Responsible for selecting and creating the correct browser driver.

```text
Browsers Enum
        ↓
Concrete Factory
        ↓
createDriver()
```

### Polymorphism

Allows the framework to call the correct factory implementation through an `AbstractDriver` reference.

### ThreadLocal Pattern

Provides one WebDriver instance per test thread.

### Facade Pattern

`DriverManager` serves as a unified entry point to actions, validations, and driver management.
