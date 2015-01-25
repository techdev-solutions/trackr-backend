package de.techdev.trackr.core.web.app;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties="auth.module=openid")
public class OpenidLoginControllerTest extends LoginControllerTest{

	public OpenidLoginControllerTest() {
		super("openid");
	}
}
