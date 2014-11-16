package com.example.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	private static CoolWeatherDB coolWeatherDB;
	
	private static final String DB_NAME = "cool_weather";
	private static final int VERSION = 1;
	
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper helper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = helper.getWritableDatabase();
	}
	
	public synchronized CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		
		return coolWeatherDB;
	}
	
	public void saveProvince(Province province){
		if(province != null){
			String saveProvince = "insert into Province (provinceName, provinceCode) values(?, ?)";
			db.execSQL(saveProvince, new String[] {province.getProvinceName(), province.getProvinceCode()});
		}
	}
	
	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.rawQuery("select * from Province", null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
				
				list.add(province);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	public void saveCity(City city){
		if(city != null){
			String saveCity = "insert into City (cityName, cityCode, provinceId) values(?, ?, ?)";
			db.execSQL(saveCity, new Object[] {city.getCityName(), city.getCityCode(), city.getProvinceId()});
		}
	}
	
	public List<City> loadCities(){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.rawQuery("select * from City", null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("provinceId")));
				
				list.add(city);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	public void saveCounty(County county){
		if(county != null){
			String saveCounty = "insert into County (countyName, countyCode, cityId) values(?, ?, ?)";
			db.execSQL(saveCounty, new Object[] {county.getCountyName(), county.getCountyCode(), county.getCityId()});
		}
	}
	
	public List<County> loadCounties(){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.rawQuery("select * from County", null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("countyName")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("countyCode")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("cityId")));
				
				list.add(county);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
}
