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

public class RegisterTests extends ApplicationTest {

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
     * tests register method with an existing username
     */
    @Test
    public void registerWithExisting () {
        clickOn("#button_register");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#username", isVisible());
        verifyThat("#password", isVisible());
        clickOn("#username");
        write("irmak");
        verifyThat("#username", hasText("irmak"));
        clickOn("#password");
        write("1234");
        verifyThat("#password", hasText("1234"));
        clickOn("#button_submit");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Username already taken."));
    }

    /**
     * tests register method with a brand new username
     */
    @Test
    public void successfulRegister () {
        clickOn("#button_register");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#username", isVisible());
        verifyThat("#password", isVisible());
        clickOn("#username");
        double randNum = Math.random() * 10000;
        write("irmak_" + randNum);
        verifyThat("#username", hasText("irmak_" + randNum));
        clickOn("#password");
        write("4321");
        verifyThat("#password", hasText("4321"));
        clickOn("#button_submit");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Registration successful!"));
    }

    /**
     * tests register method with an empty field
     */
    @Test
    public void emptyFieldsRegister () {
        clickOn("#button_register");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#username", isVisible());
        clickOn("#username");
        write("irmak_3");
        verifyThat("#username", hasText("irmak_3"));
        clickOn("#button_submit");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#label", org.testfx.matcher.control.LabeledMatchers.hasText("Some fields are missing."));
    }

}