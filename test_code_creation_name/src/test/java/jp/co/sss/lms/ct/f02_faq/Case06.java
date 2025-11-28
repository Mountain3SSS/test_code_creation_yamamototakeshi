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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		getEvidence(new Case06() {
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
		getEvidence(new Case06() {
		}, "1_ログイン情報");

		//ログイン処理と表示
		WebElement loginButtonElement = webDriver.findElement(By.className("btn-primary"));
		loginButtonElement.click();

		// 画面表示まで待機、その後ログイン成功の確認
		pageLoadTimeout(5);

		assertEquals(webDriver.getTitle(), "コース詳細 | LMS");

		// エラーメッセージが表示されているログイン画面でエビデンスを取得
		getEvidence(new Case06() {
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
		getEvidence(new Case06() {
		}, "3_ヘッダーの機能プルダウンを表示");

		//プルダウン表示を確認、その中からヘルプを押下
		webDriver.findElement(By.linkText("ヘルプ")).click();
		pageLoadTimeout(5);

		//押下後ヘルプ画面に遷移したことを確認、同画面のエビデンスを取得
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Case06() {
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
		pageLoadTimeout(10);

		//現タブのリンクが想定のものかチェック
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		getEvidence(new Case06() {
		}, "4_よくある質問画面への遷移完了");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// TODO ここに追加
		// カテゴリ検索欄の「【研修関係】」を押下する
		webDriver.findElement(By.linkText("【研修関係】")).click();

		// 画面下部検索結果に選択したカテゴリに該当する検索結果が表示されることを確認しエビデンスを取得
		webDriver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
		assertEquals("http://localhost:8080/lms/faq?frequentlyAskedQuestionCategoryId=1", webDriver.getCurrentUrl());
		getEvidence(new Case06() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// TODO ここに追加
		webDriver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);

		//対象展開前の状態のエビデンスを取得
		getEvidence(new Case06() {
		}, "検索結果質問クリック前");

		//対象の折り畳みを展開
		webDriver.findElement(By.className("sorting_1")).click();
		pageLoadTimeout(5);

		//確認対象が正しいことを確認する。その後対象が展開された状態でエビデンスを取得 
		WebElement answer = webDriver.findElement(By.id("answer-h[${status.index}]"));
		String answerText = "A. 受講者の退職や解雇等、やむを得ない事情による途中終了に関してなど、事情をお伺いした上で、協議という形を取らせて頂きます。 弊社営業担当までご相談下さい。";
		assertEquals(answerText, answer.getText());

		//
		getEvidence(new Case06() {
		});
	}

}
