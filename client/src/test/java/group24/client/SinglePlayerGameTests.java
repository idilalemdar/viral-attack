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


public class SinglePlayerGameTests extends ApplicationTest {
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
     * tests if game starts and player is visible.
     */
    @Test
    public void startGameTest () {
        successfulLogin1();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("OK");
        clickOn("#button_start");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#doctor", isVisible());
        verifyThat("#level", hasText("1"));
    }

    /**
     * tests whether levels are changing correctly after using cheat.
     */
    @Test
    public void cheatLevelsTest () {
        successfulLogin2();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("OK");
        clickOn("#button_start");
        WaitForAsyncUtils.waitForFxEvents();
        press_cheat();
        verifyThat("#level", hasText("2"));
        release_cheat();
        press_cheat();
        verifyThat("#level", hasText("3"));
        release_cheat();
        press_cheat();
        verifyThat("#level", hasText("4"));

    }
    /**
     * helpers for the test method since one cannot see without logging in.
     */
    private void successfulLogin1 () {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#username");
        write("user1");
        clickOn("#password");
        write("1564");
        clickOn("#button_signin").clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Login successful!"));
    }

    private void successfulLogin2 () {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#username");
        write("Pelin");
        clickOn("#password");
        write("4321");
        clickOn("#button_signin").clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Login successful!"));
    }
    public void press_cheat(){
        press(KeyCode.CONTROL);
        press(KeyCode.SHIFT);
        press(KeyCode.DIGIT9);
    }
    public void release_cheat(){
        release(KeyCode.CONTROL);
        release(KeyCode.SHIFT);
        release(KeyCode.DIGIT9);
    }


}