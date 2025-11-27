package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//トップページへ遷移
		String topPage = "http://localhost:8080/lms/";
		goTo(topPage);

		//エビデンス取得
		getEvidence(new Case01() {
		}, "Case01-01");

		//現ページがログインページである点確認
		assertEquals(webDriver.getCurrentUrl(), topPage);
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// TODO ここに追加

		//ログイン情報投入先を指定
		WebElement user = webDriver.findElement(By.id("loginId"));
		WebElement password = webDriver.findElement(By.id("password"));

		//上記DBに登録されていないユーザー情報を投入
		user.sendKeys("test");
		password.sendKeys("test");

		// 上記情報が入力されているログイン画面でエビデンスを取得
		getEvidence(new Case02() {
		}, "1_ログインするのに利用した情報");

		//ログイン処理
		WebElement loginButtonElement = webDriver.findElement(By.className("btn-primary"));
		loginButtonElement.click();

		// ログイン失敗の確認
		WebElement errorMessageElement = webDriver.findElement(By.className("help-inline"));
		assertEquals(errorMessageElement.getText(), "* ログインに失敗しました。");

		// エラーメッセージが表示されているログイン画面でエビデンスを取得
		getEvidence(new Case02() {
		}, "2_ログイン失敗");

	}
}
