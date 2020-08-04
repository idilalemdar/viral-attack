package group24.client;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class ScoreboardTests extends ApplicationTest {
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
     * tests scoreboard methods for last month and last week
     */
    @Test
    public void scoreBoardTest () {
        successfulLogin();
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("OK");
        clickOn("#button_scoreboard");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#table", isVisible());
        clickOn("#button_month");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#table", TableViewMatchers.hasTableCell("sseda"));
        clickOn("#button_week");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#table", TableViewMatchers.hasTableCell("Pelin"));
    }

    /**
     * helper for the test method since one cannot see without logging in.
     */
    public void successfulLogin () {
        clickOn("#button_login");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#username");
        write("ertan");
        clickOn("#password");
        write("1234");
        clickOn("#button_signin").clickOn("#button_signin");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Login successful!"));
    }

}