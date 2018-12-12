package com.example.soyeonlee.myapplication8;

public class ContractDB {

    private ContractDB() {};
    private static final String _ID = "_ID";
    private static final String TABLE_NAME = "MEMO_LIST";
    private static final String COL_DATE = "DATE";
    private static final String COL_WRITE = "WRTIE";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " "
            + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT"+ ", " +  COL_DATE + " TEXT" + ", " + COL_WRITE + " TEXT" + ")";
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS" + " " + TABLE_NAME;
    public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME;
    //public static final String SQL_INSERT = "INSERT OR REPLACE INTO" + TABLE_NAME;
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TABLE_NAME + " "
            + "(" + COL_DATE + ", " + COL_WRITE + ") VALUES";
    //public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME;
    public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME +" WHERE " + _ID + " = ";
    public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET";
}
