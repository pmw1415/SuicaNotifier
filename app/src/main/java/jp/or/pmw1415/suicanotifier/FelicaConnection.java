package jp.or.pmw1415.suicanotifier;

import android.nfc.Tag;
import android.nfc.tech.NfcF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by pmw1415 on 2015/11/17.
 */
public class FelicaConnection {
	/*
	 * @note
	 *
	 * ■データ構造
	 * http://www.sony.co.jp/Products/felica/business/tech-support/data/card_usersmanual_2.0.pdf
	 * https://osdn.jp/projects/felicalib/wiki/suica
	 *
	 * [ReadWithoutEncryptionコマンド 要求データ構造]
	 *   0:			データ長
	 *   1:			コマンドコード
	 *   2-9:		IDm
	 *   10:		サービス数。以下2バイトのサービスコード個数。1-16
	 *   11-12:		サービスコードリスト。上記サービス数分続く。ここではサービス数=1
	 *   13:		ブロック数。最大値は製品ごとに異なる
	 *   14-15:		ブロックリストエレメント、ブロック番号。上記ブロック数分続く。
	 *
	 * [ReadWithoutEncryptionコマンド 応答データ構造]
	 *   0:			データ長
	 *   1:			応答コード
	 *   2-9:		カードID
	 *   10-11:		エラーコード。0=正常
	 *   12:		応答ブロック数。最新ブロックが先頭
	 *   13+n*16:	履歴データ。1データ16byteで応答ブロック数分繰り返し
	 *
	 * [履歴データ構造]
	 *   0:			端末種
	 *   1:			処理
	 *   2-3:		??
	 *   4-5:		日付([15:9]=year, [8:5]=month, [4:0]=day)
	 *   6:			入線区
	 *   7:			入駅順
	 *   8:			出線区
	 *   9:			出駅順
	 *   10-11:		残高(ここだけLittle endian)
	 *   12-14:		連番
	 *   15:		リージョン
	 */

	private NfcF nfc;

	public FelicaConnection() {
	}

	/**
	 * 履歴読み込みコマンド取得
	 *
	 * @param tag		タグ
	 * @param history	取得する履歴の数
	 * @return			受信データ。失敗時はnull
	 */
	public byte[] sendCmdReadWithoutEncryption(Tag tag, int history) throws Exception {
		byte[] res = null;
		nfc = NfcF.get(tag);

		byte[] felicaDm = new byte[]{0};
		felicaDm = tag.getId();

		try {
			byte[] req = createCmdReadWithoutEncryption(felicaDm, history);

			// カードに接続してリクエスト送信
			nfc.connect();
			res = nfc.transceive(req);
			nfc.close();

		} catch (Exception e) {
			throw e;
		}

		if (res == null) {
			throw new Exception("Send raw NFC-F command failed.");
		}

		return res;
	}

	/**
	 * 履歴読み込みFelicaコマンド取得
	 *
	 * - Sonyの「Felicaユーザマニュアル抜粋」の仕様から。
	 * - サービスコードは http://sourceforge.jp/projects/felicalib/wiki/suica の情報から。
	 * - 取得できる履歴数の上限は「製品により異なります」。
	 *
	 * @param idm カードのID
	 * @param history 取得する履歴数
	 * @return Felicaコマンド
	 * @throws IOException
	 */
	private byte[] createCmdReadWithoutEncryption(byte[] idm, int history) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream(100);

		//TODO カードに対応するデータ構築

		bout.write(0);			// データ長バイト(ダミー。最後に設定し直す)
		bout.write(0x06);		// 「Read Without Encryption」コマンドコード
		bout.write(idm);		// カードID(8byte)
		bout.write(1);			// サービス数(1固定)
		bout.write(0x0f);		// 履歴のサービスコード下位バイト
		bout.write(0x09);		// 履歴のサービスコード上位バイト
		bout.write(history);	// ブロック数
		for (int i = 0; i < history; i++) {
			bout.write(0x80);	// ブロックリストエレメント
			bout.write(i);		// ブロック番号
		}

		byte[] msg = bout.toByteArray();
		msg[0] = (byte)msg.length;	// データ長セット
		return msg;
	}

	/**
	 * Felica応答生データから残額を取り出す
	 *
	 * @param res Felica応答生データ
	 * @return 残額
	 * @throws Exception
	 */
	public int getRemain(byte[] res) throws Exception {
		int remain = 0;
		remain = (res[13 + 11] << 8) | res[13 + 10];
		return remain;
	}
}
