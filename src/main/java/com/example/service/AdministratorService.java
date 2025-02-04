package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}
	
	//Ex03 
	//指定されたメールアドレスが既にデータベースに登録されているかどうかをチェックするメソッド
	  //isEmailDuplicatedメソッド：「メールアドレスが重複しているかどうか」をチェックして、結果を true または false で返す
	  //メールアドレスが既に存在する場合（重複している場合）、true を返す。メールアドレスが存在しない場合（重複していない場合）、false を返す。

	//administratorRepository.existsByMailAddress(mailAddress) ：
	  //↑リポジトリ（データベース操作を担当するクラス）のメソッドを呼び出して、指定されたメールアドレスがすでにデータベースに存在するかどうかを確認する
	  //administratorRepository:AdministratorRepository クラスのインスタンス。データベースとやりとりをする

	//existsByMailAddress は AdministratorRepository クラスのメソッドで、指定されたメールアドレスがデータベースに存在するかを確認する
	  //メールアドレスがデータベースに存在すれば、true を返す（重複しているということ）。存在しなければfalseを返す
      //(mailAddress)は、確認したいメールアドレスを引数として渡します。

	public boolean isEmailDuplicated(String mailAddress){
		return administratorRepository.existsByMailAddress(mailAddress);
	}

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String password) {
		Administrator administrator = administratorRepository.findByMailAddressAndPassward(mailAddress, password);
		return administrator;
	}

	

}