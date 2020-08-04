package group24.client;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


class ClientApplicationTests extends ApplicationTest {

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

	@Test
	void contextLoads() {
	}

}
