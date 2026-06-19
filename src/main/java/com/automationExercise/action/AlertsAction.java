package com.automationExercise.action;


import com.automationExercise.utils.WaitManager;

public class AlertsAction {
    private final WebDriver driver;
    private final WaitManager waitmanager;

    public AlertsAction(WebDriver driver)
    {
        this.driver = driver;
        this.waitmanager = new WaitManager(driver);
    }

    public void acceptAlert()
    {
        waitmanager.fluentWait().until( d    ->
                {
                        try
                            {
                                d .switchTo().alert().accept();
                                return true;
                            }
                        catch (Exception ex)
                        {
                            LogsManager.error("accept error", ex.getMessage());
                            return false;
                        }


                });
        driver.switchTo().alert().accept();
    }

    public void dismissAlert()
    {
        waitmanager.fluentWait().until( d    ->
        {
            try
            {
                d .switchTo().alert().dismiss();
                return true;
            }
            catch (Exception ex)
            {
                LogsManager.error("dismissAlert", ex.getMessage());

                return false;
            }


        });
        driver.switchTo().alert().accept();
    }

    public void getAlertText()
    {
        waitmanager.fluentWait().until( d    ->
        {
            try
            {
               String text= d .switchTo().alert().getText();
                return !text.isEmpty()?text:null;
            }
            catch (Exception ex)
            {
                LogsManager.error("failed to get Text", ex.getMessage());
                return false;
            }


        });
        driver.switchTo().alert().accept();
    }


    public void setAlertText( String text)
    {
        waitmanager.fluentWait().until( d    ->
        {
            try
            {
                d .switchTo().alert().sendKeys(text);
                return true;
            }
            catch (Exception ex)
            {
                LogsManager.error("failed to set Text ", ex.getMessage());
                return false;
            }


        });
        driver.switchTo().alert().accept();
    }


}
