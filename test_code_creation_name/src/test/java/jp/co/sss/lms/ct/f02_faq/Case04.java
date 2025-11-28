package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		String topPage = "http://localhost:8080/lms/";
		webDriver.manage().window().maximize();
		goTo(topPage);

		//現ページがログインページである点確認
		assertEquals(topPage, webDriver.getCurrentUrl());

		//エビデンス取得
		getEvidence(new Case04() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//初回ログイン済みの受講生ユーザーでログイン
		WebElement user = webDriver.findElement(By.id("loginId"));
		WebElement password = webDriver.findElement(By.id("password"));
		user.sendKeys("StudentAA01");
		password.sendKeys("3SSSSystems");

		// 上記情報が入力されているログイン画面でエビデンスを取得
		getEvidence(new Case04() {
		}, "1_ログイン情報");

		//ログイン処理と表示
		WebElement loginButtonElement = webDriver.findElement(By.className("btn-primary"));
		loginButtonElement.click();

		// 画面表示まで待機、その後ログイン成功の確認
		pageLoadTimeout(5);

		assertEquals(webDriver.getTitle(), "コース詳細 | LMS");

		// エラーメッセージが表示されているログイン画面でエビデンスを取得
		getEvidence(new Case04() {
		}, "2_ログイン成功");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		// 上部メニューの機能にカーソルを合わせ、プルダウンを表示。
		WebElement dropdown = webDriver.findElement(By.cssSelector("a.dropdown-toggle"));
		dropdown.click();
		dropdown.isSelected();

		// プルダウン選択中のエビデンスを取得
		getEvidence(new Case04() {
		}, "3_ヘッダーの機能プルダウンを表示");

		//プルダウン表示を確認、その中からヘルプを押下
		webDriver.findElement(By.linkText("ヘルプ")).click();
		pageLoadTimeout(5);

		//押下後ヘルプ画面に遷移したことを確認、同画面のエビデンスを取得
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Case04() {
		}, "4_ヘルプ画面への遷移完了");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		//ページを一ページ分下へ
		WebElement pagedown = webDriver.findElement(By.tagName("body"));
		pagedown.sendKeys(Keys.PAGE_DOWN);

		//よくある質問リンクを選択
		WebElement frequentQa = webDriver.findElement(By.linkText("よくある質問"));
		frequentQa.click();

		//ページ読み込み待機
		pageLoadTimeout(5);

		//ウィンドウハンドルの配列取得
		Object windowHandles[] = webDriver.getWindowHandles().toArray();
		//新しいページへ移動
		webDriver.switchTo().window((String) windowHandles[1]);
		pageLoadTimeout(5);

		//現タブのリンクが想定のものかチェック
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		getEvidence(new Case04() {
		}, "4_よくある質問画面への遷移完了");

	}

}
