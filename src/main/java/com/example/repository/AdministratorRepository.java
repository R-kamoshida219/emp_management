package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;


/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class AdministratorRepository {

	/**
	 * Administratorオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 主キーから管理者情報を取得します.
	 * 
	 * @param id ID
	 * @return 管理者情報
	 * @throws org.springframework.dao.DataAccessException 存在しない場合は例外を発生します
	 */
	public Administrator load(Integer id) {
		String sql = "select id,name,mail_address,password from administrators where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		return administrator;
	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullを返します
	 */
	public Administrator findByMailAddressAndPassward(String mailAddress, String password) {
		String sql = "select id,name,mail_address,password from administrators where mail_address= '" + mailAddress
				+ "' and password='" + password + "'";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if (administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);

		
	}

	/**
	 * 管理者情報を挿入します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		String sql = "insert into administrators(name,mail_address,password)values(:name,:mailAddress,:password);";
		template.update(sql, param);
	}

	/**
	 * メールアドレスから管理者情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 管理者情報 存在しない場合はnullを返します
	 */
	public Administrator findByMailAddress(String mailAddress) {
		String sql = "select id,name,mail_address,password from administrators where mail_address=:mailAddress";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if (administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}

	//Ex03
	//指定されたメールアドレスがデータベースにすでに存在しているかどうかをチェックするためのメソッドを定義
	//戻り値: boolean 型を返すので、「存在する」場合は true、「存在しない」場合は false を返す

	//SQL クエリ文字列 String sql = "SELECT COUNT(*) FROM administrators WHERE mail_address = :mail";
	  //データベースに対するSQLクエリを定義しています。具体的には、administrators テーブル内で、指定されたメールアドレス（mail_address）を持つレコードがいくつあるかをカウントします。
	
	//SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mailAddress);
	  //SQLクエリのプレースホルダー（:mail）に渡すパラメータの値を設定する部分

	//MapSqlParameterSource: このクラスは、SQLクエリのパラメータとその値を管理する
	  //addValue("mail", mailAddress):
	  // "mail" という名前のパラメータに、メソッドの引数として渡された mailAddress の値を設定。
	  //これにより、SQLの :mail プレースホルダーが実際のメールアドレスに置き換えられる。

	//int count = template.queryForObject(sql, param, Integer.class);
	  //NamedParameterJdbcTemplate を使って、実際にSQLクエリを実行し、結果を取得
	  //template.queryForObject(...): queryForObject メソッドを使って、SQLクエリを実行し、その結果をオブジェクトとして取得。
	  //sql: 実行するSQLクエリ。
      //param: SQLクエリに渡すパラメータ（上記で設定した mailAddress の値）。
      //Integer.class: 結果が Integer 型（カウント数）であることを指定しています。COUNT(*) はレコード数を返すため、返される結果は Integer 型になります。
	//戻り値: count には、指定したメールアドレスがデータベースに存在するレコードの数が格納されます。

	//return count > 0;
	  //count が 0 より大きければ（つまり、指定したメールアドレスが1件以上データベースに存在すれば）、true を返します。それ以外（count が 0）の場合は、false を返します。


	public boolean existsByMailAddress(String mailAddress){
		String sql = "SELECT COUNT(*)FROM administrators WHERE mail_address = :mail";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mail", mailAddress);
		int count = template.queryForObject(sql,param,Integer.class);  
		return count >0;

		//template.queryForObject(実行したいクエリ(sql),SQLに渡すパラメータ(param),結果を格納するクラス型.class);
	}
	}
