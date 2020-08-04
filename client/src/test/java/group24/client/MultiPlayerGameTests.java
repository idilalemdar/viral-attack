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
import static org.testfx.util.NodeQueryUtils.isVisible;


public class MultiPlayerGameTests extends ApplicationTest {
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
     * tests whether the game play waits for a match at level 5
     */
    @Test
    public void waitingTest () {
        successfulLogin();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("OK");
        clickOn("#button_start");
        WaitForAsyncUtils.waitForFxEvents();
        pressReleaseCheat();
        pressReleaseCheat();
        pressReleaseCheat();
        pressReleaseCheat();
        verifyThat("#waiting", isVisible());
    }

    /**
     * helper for the test method since one cannot see without logging in.
     */
    private void successfulLogin() {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#username");
        write("idil");
        clickOn("#password");
        write("2562");
        clickOn("#button_signin").clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Login successful!"));
    }
    /**
     * presses and releases cheat keys.
     */
    public void pressReleaseCheat(){
        press(KeyCode.CONTROL);
        press(KeyCode.SHIFT);
        press(KeyCode.DIGIT9);
        release(KeyCode.CONTROL);
        release(KeyCode.SHIFT);
        release(KeyCode.DIGIT9);
    }

}