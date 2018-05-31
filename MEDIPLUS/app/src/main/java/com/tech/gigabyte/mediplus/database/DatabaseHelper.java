package com.tech.gigabyte.mediplus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tech.gigabyte.mediplus.model.Alarm;
import com.tech.gigabyte.mediplus.model.MedicineData;
import com.tech.gigabyte.mediplus.model.PharmacyModel;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 4/7/2017.
 * Database Class - MEDIPLUS DATABASE For [C-(CREATE)-U-(UPGRADE)-R-(READ)-D-(DELETE)]
 */

public class DatabaseHelper {

    private static DatabaseHelper DBHelper;
    private static Helper helper;
    private Context m_con;
    private ArrayList<MedicineData> M_List;
    private ArrayList<String> names;
    private ArrayList<MedicineData> Main_List;
    private ArrayList<PharmacyModel> Pharmacy_List;


    private DatabaseHelper(Context context) {
        this.m_con = context;
        helper = Helper.getHelper(context);
        M_List = new ArrayList<>();
        Main_List = new ArrayList<>();
        names = new ArrayList<>();
        Pharmacy_List = new ArrayList<>();
    }

    public static DatabaseHelper getInstance(Context context) {
        if (DBHelper == null) {
            DBHelper = new DatabaseHelper(context);
        }
        return DBHelper;
    }

    /*+++++++++++++++++++++++++++++++++++++++++++[Inserting Into Table]++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public long insert(String name, String description, String price, String cat, String type, String instructions) throws SQLException {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.COL_NAME, name);
        contentValues.put(Helper.COL_DESCRIPTION, description);
        contentValues.put(Helper.COL_PRICE, price);
        contentValues.put(Helper.COL_CATEGORY, cat);
        contentValues.put(Helper.COL_TYPE, type);
        contentValues.put(Helper.COL_INSTRUCTIONS, instructions);
        long res = sqLiteDatabase.insertOrThrow(Helper.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return res;
    }
    /*------------------------------------------------------------------------------------------------------------------------------------*/

    /*++++++++++++++++++++++++++++++++++++++++++++[Updating Edit DRUG-DATA]+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public long update(String name, String desc, String price, String catogory, String type, String instructions, int id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.COL_NAME, name);
        contentValues.put(Helper.COL_DESCRIPTION, desc);
        contentValues.put(Helper.COL_PRICE, price);
        contentValues.put(Helper.COL_CATEGORY, catogory);
        contentValues.put(Helper.COL_TYPE, type);
        contentValues.put(Helper.COL_INSTRUCTIONS, instructions);
        long updateresult = sqLiteDatabase.update(Helper.TABLE_NAME, contentValues, Helper.COL_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return updateresult;
    }
    /*------------------------------------------------------------------------------------------------------------------------------------*/

    public int DB(String name) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String[] columns = {Helper.COL_ID};
        int drug_id = 0;
        Cursor cursor = sqLiteDatabase.query(Helper.TABLE_NAME, columns, Helper.COL_NAME + "=?", new String[]{name}, null, null, null);
        while (cursor.moveToNext()) {
            drug_id = cursor.getInt(cursor.getColumnIndex(Helper.COL_ID));
        }
        sqLiteDatabase.close();
        return drug_id;
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++[Searching Saved DRUG-DATA]++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public ArrayList<MedicineData> getAllDrugNames() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ArrayList<MedicineData> search = new ArrayList<>();
        String[] columns = {Helper.COL_NAME, Helper.COL_DESCRIPTION, Helper.COL_PRICE, Helper.COL_CATEGORY, Helper.COL_TYPE, Helper.COL_INSTRUCTIONS, Helper.COL_ID};
        Cursor cursor = sqLiteDatabase.query(Helper.TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Helper.COL_NAME));
            String desc = cursor.getString(cursor.getColumnIndex(Helper.COL_DESCRIPTION));
            String price = cursor.getString(cursor.getColumnIndex(Helper.COL_PRICE));
            int id = cursor.getInt(cursor.getColumnIndex(Helper.COL_ID));
            String category = cursor.getString(cursor.getColumnIndex(Helper.COL_CATEGORY));
            String type = cursor.getString(cursor.getColumnIndex(Helper.COL_TYPE));
            String instruction = cursor.getString(cursor.getColumnIndex(Helper.COL_INSTRUCTIONS));
            MedicineData m_data = new MedicineData(name, desc, price, type, instruction, id, category);
            search.add(m_data);
        }
        sqLiteDatabase.close();
        return search;
    }

    /*-------------------------------------------------------------------------------------------------------------------------------------------*/

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++[AutoComplete Search]+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public ArrayList<String> search() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String[] columns = {Helper.COL_NAME};
        Cursor cursor = sqLiteDatabase.query(Helper.TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Helper.COL_NAME));
            names.add(name);
        }
        sqLiteDatabase.close();
        return names;
    }
    /*------------------------------------------------------------------------------------------------------------------------------------*/

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++[Display DRUG-DATA/Details From Search]++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public ArrayList<MedicineData> DrugDetails(String name) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String[] columns = {Helper.COL_ID, Helper.COL_NAME, Helper.COL_DESCRIPTION, Helper.COL_PRICE, Helper.COL_CATEGORY, Helper.COL_TYPE, Helper.COL_INSTRUCTIONS};
        Cursor cursor = sqLiteDatabase.query(Helper.TABLE_NAME, columns, Helper.COL_NAME + "=?", new String[]{name}, null, null, null);
        while (cursor.moveToNext()) {
            int sid = cursor.getInt(cursor.getColumnIndex(Helper.COL_ID));
            String s_name = cursor.getString(cursor.getColumnIndex(Helper.COL_NAME));
            String s_desc = cursor.getString(cursor.getColumnIndex(Helper.COL_DESCRIPTION));
            String s_price = cursor.getString(cursor.getColumnIndex(Helper.COL_PRICE));
            String s_category = cursor.getString(cursor.getColumnIndex(Helper.COL_CATEGORY));
            String s_type = cursor.getString(cursor.getColumnIndex(Helper.COL_TYPE));
            String s_inst = cursor.getString(cursor.getColumnIndex(Helper.COL_INSTRUCTIONS));
            MedicineData medicineData = new MedicineData(s_name, s_desc, s_price, s_type, s_inst, sid, s_category);
            M_List.add(medicineData);
        }
        sqLiteDatabase.close();
        return M_List;
    }

    public long deletedrugfromTable(int id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long delres = sqLiteDatabase.delete(Helper.TABLE_NAME, Helper.COL_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return delres;
    }
    /*-----------------------------------------------------------------------------------------------------------------------------------------------------*/

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++[Alarm Table]++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public long al_table(String al_name, String al_messasge, String date, String time, String Pi_no, String finished) {
        SQLiteDatabase dbs = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.AL_NAME, al_name);
        contentValues.put(Helper.AL_MESSAGE, al_messasge);
        contentValues.put(Helper.AL_DATE, date);
        contentValues.put(Helper.AL_TIME, time);
        contentValues.put(Helper.AL_PENDING_NO, Pi_no);
        contentValues.put(Helper.FINISHED, finished);
        return dbs.insert(Helper.AL_TABLE, null, contentValues);
    }

    public ArrayList<Alarm> AlarmInfo() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ArrayList<Alarm> al_info = new ArrayList<>();
        String[] columns = {Helper.AL_NAME, Helper.AL_MESSAGE, Helper.AL_DATE, Helper.AL_TIME, Helper.AL_PENDING_NO, Helper.AL_COL_ID, Helper.FINISHED};
        Cursor cursor = sqLiteDatabase.query(Helper.AL_TABLE, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String al_name = cursor.getString(cursor.getColumnIndex(Helper.AL_NAME));
            String al_msg = cursor.getString(cursor.getColumnIndex(Helper.AL_MESSAGE));
            String al_date = cursor.getString(cursor.getColumnIndex(Helper.AL_DATE));
            String al_time = cursor.getString(cursor.getColumnIndex(Helper.AL_TIME));
            String al_pi_no = cursor.getString(cursor.getColumnIndex(Helper.AL_PENDING_NO));
            String al_fin = cursor.getString(cursor.getColumnIndex(Helper.FINISHED));
            int al_id = cursor.getInt(cursor.getColumnIndex(Helper.AL_COL_ID));
            Alarm alarmInformation = new Alarm(al_name, al_msg, al_date, al_time, al_pi_no, al_fin, al_id);
            al_info.add(alarmInformation);
        }
        sqLiteDatabase.close();
        return al_info;
    }

    public long al_del(int id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long a_del = sqLiteDatabase.delete(Helper.AL_TABLE, Helper.AL_COL_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return a_del;
    }
    /*-----------------------------------------------------------------------------------------------------------------------------------------------------*/

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++[PHARMACY TABLE]++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

    public long insertInPharmacy(String name, String number, String address) throws SQLException {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.PHARMACY_NAME, name);
        contentValues.put(Helper.PHARMACY_PHONE, number);
        contentValues.put(Helper.PHARMACY_ADDRESS, address);
        long insresult = sqLiteDatabase.insert(Helper.PHARMACY_TABLE, null, contentValues);
        sqLiteDatabase.close();
        return insresult;
    }

    public ArrayList<PharmacyModel> p_get() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String[] columns = {Helper.PHARMACY_NAME, Helper.PHARMACY_PHONE, Helper.PHARMACY_ADDRESS, Helper.PHARMACY_COL_ID};
        Cursor cursor = sqLiteDatabase.query(Helper.PHARMACY_TABLE, columns, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Helper.PHARMACY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(Helper.PHARMACY_PHONE));
            String address = cursor.getString(cursor.getColumnIndex(Helper.PHARMACY_ADDRESS));
            int p_id = cursor.getInt(cursor.getColumnIndex(Helper.PHARMACY_COL_ID));
            PharmacyModel pharmacyModel = new PharmacyModel(p_id, name, number, address);
            Pharmacy_List.add(pharmacyModel);
        }
        sqLiteDatabase.close();
        return Pharmacy_List;
    }

    public long deletepharmadetail(int id) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long delete = sqLiteDatabase.delete(Helper.PHARMACY_TABLE, Helper.PHARMACY_COL_ID + "=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return delete;
    }

    public long p_del_all() {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long del = sqLiteDatabase.delete(Helper.PHARMACY_TABLE, null, null);
        sqLiteDatabase.close();
        return del;
    }
    /*-----------------------------------------------------------------------------------------------------------------------------------------------------*/

    //--------------------[::::::::::::::::GETTER--[&]---SETTER::::::::::::::::]--------------------//

    public Context getM_con() {
        return m_con;
    }

    public void setM_con(Context m_con) {
        this.m_con = m_con;
    }

    public ArrayList<MedicineData> getMain_List() {
        return Main_List;
    }

    public void setMain_List(ArrayList<MedicineData> main_List) {
        this.Main_List = main_List;
    }

    //---------------------------------------------------------------------------------------------//

    /*=================================[::::MEDIPLUS - DATABASE::::]================================*/

    public static class Helper extends SQLiteOpenHelper {

        private static final String DB_NAME = "MEDIPLUS.db";
        private static final String TABLE_NAME = "DRUG_DETAILS";
        private static final int VERSION = 1;
        private static final String COL_ID = "ID";
        private static final String COL_NAME = "DRUG_NAME";
        private static final String COL_DESCRIPTION = "DESCRIPTION";
        private static final String COL_PRICE = "PRICE";
        private static final String COL_CATEGORY = "CATEGORY";
        private static final String COL_TYPE = "TYPE";
        private static final String COL_INSTRUCTIONS = "INSTRUCTIONS";
        //__________________________________________________________________________________________//
        //------------------------------------ALARM-TABLE------------------------------------------//

        private static final String AL_TABLE = "MED_ALARM";
        private static final String AL_COL_ID = "ID";
        private static final String AL_NAME = "AL_NAME";
        private static final String AL_MESSAGE = "AL_MESSAGE";
        private static final String AL_DATE = "AL_DATE";
        private static final String AL_TIME = "AL_TIME";
        private static final String AL_PENDING_NO = "PENDING_NO";
        private static final String FINISHED = "FINISHED";
        //__________________________________________________________________________________________//
        //----------------------------------PHARMACY-TABLE-----------------------------------------//

        private static final String PHARMACY_TABLE = "PHARMA_TABLE";
        private static final String PHARMACY_COL_ID = "PHARMA_ID";
        private static final String PHARMACY_NAME = "PHARMACY_NAME";
        private static final String PHARMACY_ADDRESS = "PHARMACY_ADDRESS";
        private static final String PHARMACY_PHONE = "PHONE_NUMBER";
        //__________________________________________________________________________________________//

        private static Helper helper;

        private Helper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        private static Helper getHelper(Context context) {
            if (helper == null) {
                helper = new Helper(context);
            }
            return helper;
        }

        @Override
        //Creating Table - [DRUG-DATA] & [ALARM] & [PHARMACY]
        public void onCreate(SQLiteDatabase sqLiteDatabase) throws SQLException {
            String create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME + " TEXT,"
                    + COL_DESCRIPTION + " TEXT,"
                    + COL_PRICE + " TEXT,"
                    + COL_CATEGORY + " TEXT,"
                    + COL_TYPE + " TEXT,"
                    + COL_INSTRUCTIONS + " TEXT);";


            String table_pharmacy = "CREATE TABLE IF NOT EXISTS " + PHARMACY_TABLE + "("
                    + PHARMACY_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PHARMACY_NAME + " TEXT,"
                    + PHARMACY_ADDRESS + " TEXT,"
                    + PHARMACY_PHONE + " TEXT);";

            String table_alarm = "CREATE TABLE IF NOT EXISTS " + AL_TABLE + "("
                    + AL_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + AL_NAME + " TEXT,"
                    + AL_MESSAGE + " TEXT,"
                    + AL_DATE + " TEXT,"
                    + AL_TIME + " TEXT,"
                    + AL_PENDING_NO + " TEXT,"
                    + FINISHED + " TEXT);";

            sqLiteDatabase.execSQL(create_table);
            sqLiteDatabase.execSQL(table_pharmacy);
            sqLiteDatabase.execSQL(table_alarm);
        }

        @Override
        //When the database needs to be Upgrade.
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PHARMACY_TABLE);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AL_TABLE);

            onCreate(sqLiteDatabase);
        }
    }
}