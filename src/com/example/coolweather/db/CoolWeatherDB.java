package com.example.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

import android.R.integer;
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
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		
		return coolWeatherDB;
	}
	
	public void saveProvince(Province province){
		if(province != null){
			String saveProvince = "insert into Province (province_name, province_code) values(?, ?)";
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
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				
				list.add(province);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	public void saveCity(City city){
		if(city != null){
			String saveCity = "insert into City (city_name, city_code, province_id) values(?, ?, ?)";
			db.execSQL(saveCity, new Object[] {city.getCityName(), city.getCityCode(), city.getProvinceId()});
		}
	}
	
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.rawQuery("select * from City where province_id = ?", new String[]{String.valueOf(provinceId)});
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
				
				list.add(city);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
	
	public void saveCounty(County county){
		if(county != null){
			String saveCounty = "insert into County (county_name, county_code, city_id) values(?, ?, ?)";
			db.execSQL(saveCounty, new Object[] {county.getCountyName(), county.getCountyCode(), county.getCityId()});
		}
	}
	
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.rawQuery("select * from County where city_id = ?", new String[]{String.valueOf(cityId)});
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
				
				list.add(county);
			}while(cursor.moveToNext());
		}
		
		return list;
	}
}
