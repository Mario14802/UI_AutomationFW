import com.automationExercise.drivers.GUIDriver;
import com.automationExercise.drivers.WebDriverProvider;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {

    protected GUIDriver driver;
    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
