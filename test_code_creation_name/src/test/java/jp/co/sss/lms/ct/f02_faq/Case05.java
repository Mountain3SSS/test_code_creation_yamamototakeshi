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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
		String topPage = "http://localhost:8080/lms/";
		webDriver.manage().window().maximize();
		goTo(topPage);

		//現ページがログインページである点確認
		assertEquals(topPage, webDriver.getCurrentUrl());

		//エビデンス取得
		getEvidence(new Case05() {
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
		getEvidence(new Case05() {
		}, "1_ログイン情報");

		//ログイン処理と表示
		WebElement loginButtonElement = webDriver.findElement(By.className("btn-primary"));
		loginButtonElement.click();

		// 画面表示まで待機、その後ログイン成功の確認
		pageLoadTimeout(5);

		assertEquals(webDriver.getTitle(), "コース詳細 | LMS");

		// エラーメッセージが表示されているログイン画面でエビデンスを取得
		getEvidence(new Case05() {
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
		getEvidence(new Case05() {
		}, "3_ヘッダーの機能プルダウンを表示");

		//プルダウン表示を確認、その中からヘルプを押下
		webDriver.findElement(By.linkText("ヘルプ")).click();
		pageLoadTimeout(5);

		//押下後ヘルプ画面に遷移したことを確認、同画面のエビデンスを取得
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Case05() {
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

		//ページ読み込み待機
		pageLoadTimeout(5);

		//よくある質問リンクを選択
		WebElement frequentQa = webDriver.findElement(By.linkText("よくある質問"));
		frequentQa.click();

		//ページ読み込み待機
		pageLoadTimeout(5);

		//ウィンドウハンドルの配列取得
		Object[] allWindows = webDriver.getWindowHandles().toArray();
		//新しいページへ移動
		webDriver.switchTo().window(allWindows[1].toString());
		pageLoadTimeout(5);

		//現タブのリンクが想定のものかチェック
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		getEvidence(new Case05() {
		}, "4_よくある質問画面への遷移完了");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// TODO ここに追加
		// キーワードのテキストボックスへ「助成金」と入力する
		WebElement textBox = webDriver.findElement(By.id("form"));
		textBox.sendKeys("助成金");

		// 入力項目にてエビデンス取得
		getEvidence(new Case05() {
		}, "1_入力した検索キーワード");

		// 検索ボタンを押下する
		webDriver.findElement(By.cssSelector("input[type='submit'][value='検索']")).click();
		pageLoadTimeout(5);

		// 検索結果表示のためページを1ページ分下げる
		WebElement pagedown = webDriver.findElement(By.tagName("body"));
		pagedown.sendKeys(Keys.PAGE_DOWN);
		pageLoadTimeout(5);

		// 検索結果が表示されている状態で画像エビデンスを取得
		assertEquals("http://localhost:8080/lms/faq?keyword=%E5%8A%A9%E6%88%90%E9%87%91", webDriver.getCurrentUrl());
		getEvidence(new Case05() {
		}, "2_該当キーワードを含む検索結果");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加
		// ページを1ページ分上げる
		WebElement pagedown = webDriver.findElement(By.tagName("body"));
		pagedown.sendKeys(Keys.PAGE_UP);
		pageLoadTimeout(5);

		// クリアボタンを押下する
		webDriver.findElement(By.cssSelector("input[type='button'][value='クリア']")).click();

		// テキストボックス内が空白になっていることを確認しエビデンスを取得
		WebElement textBox = webDriver.findElement(By.id("form"));
		String value = textBox.getAttribute("value");
		assert value.isEmpty();

		getEvidence(new Case05() {
		});

	}

}
