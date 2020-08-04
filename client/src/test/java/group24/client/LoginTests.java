package group24.client;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class LoginTests extends ApplicationTest {

    @Before
    public void setup () throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(GameApplication.class);
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }


    /**
     * tests the behavior of the application
     * when one of the login fields is empty.
     */
    @Test
    public void emptyFieldsLogin () {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#username", isVisible());
        clickOn("#username");
        write("sseda");
        verifyThat("#username", hasText("sseda"));
        clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Some fields are missing."));
    }

    /**
     * tests login method with correct credentials
     */
    @Test
    public void successfulLogin () {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#password", isVisible());
        verifyThat("#username", isVisible());
        clickOn("#username");
        write("sseda");
        verifyThat("#username", hasText("sseda"));
        clickOn("#password");
        write("1234");
        verifyThat("#password", hasText("1234"));
        clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Login successful!"));
    }

    /**
     * tests login method with wrong credentials
     */
    @Test
    public void failLogin () {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#password", isVisible());
        verifyThat("#username", isVisible());
        clickOn("#username");
        write("Deniz");
        verifyThat("#username", hasText("Deniz"));
        clickOn("#password");
        write("4321");
        verifyThat("#password", hasText("4321"));
        clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Wrong credentials provided. Try again."));
    }
    /**
     * tests whether one can login twice.
     */
    @Test
    public void twiceLogin(){
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#username");
        write("sseda");
        clickOn("#password");
        write("1234");
        clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("User has already logged in."));
    }

}