package com.jk.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.jk.pojo.TUser;
import com.jk.provider.TUserProvider;

public interface TUserMapper {

	// **************引用动态SQL*********************************

	@InsertProvider(type = TUserProvider.class, method = "insertTUser")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertTUser2(TUser user);

	@UpdateProvider(type = TUserProvider.class, method = "updateTUser")
	int updateTUser2(TUser user);

	@DeleteProvider(type = TUserProvider.class, method = "deleteTUser")
	int deleteTUser2(int id);

	@SelectProvider(type = TUserProvider.class, method = "selectTUserById")
	TUser selectTUserById2(int id);

	@SelectProvider(type = TUserProvider.class, method = "selectTUserById3")
	List<TUser> selectTUserById3();

}