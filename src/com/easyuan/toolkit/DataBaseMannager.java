package com.easyuan.toolkit;

import java.util.List;

import android.content.Context;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.db.sqlite.DbModel;

public class DataBaseMannager {
	private FinalDb mFinalDB;

	public DataBaseMannager(Context context) {
		mFinalDB = FinalDb.create(context);
	}
	
	public DataBaseMannager(Context context, String dbName) {
		mFinalDB = FinalDb.create(context, dbName);
	}

	public void delete(Object entity) {
		mFinalDB.delete(entity);
	}

	
	public <T> void save(Object entity) {
			mFinalDB.save(entity);
	}

	public void update(Object entity) {
		mFinalDB.update(entity);
	}

	public <T> List<T> findAll(Class<T> clazz, String orderBy) {
		return mFinalDB.findAll(clazz, orderBy);
	}

	public <T> List<T> findAll(Class<T> clazz) {
		return mFinalDB.findAll(clazz);
	}

	public <T> T findById(Object id, Class<T> clazz) {
		return mFinalDB.findById(id, clazz);
	}

	public List<DbModel> findDbModelListBySQL(String strSQL) {
		return mFinalDB.findDbModelListBySQL(strSQL);
	}
	
	

	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere,
			String orderBy) {
		return mFinalDB.findAllByWhere(clazz, strWhere, orderBy);
	}

	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere) {
		return mFinalDB.findAllByWhere(clazz, strWhere);
	}

	public void update(Object entity, String strWhere) {
		mFinalDB.update(entity, strWhere);
	}

}
