package utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "personas.db";
  private static final int DATABASE_VERSION = 1;

  private static final String TABLE_PERSONAS = "personas";
  private static final String COLUMN_ID = "id";
  private static final String COLUMN_NOMBRE = "nombre";
  private static final String COLUMN_APELLIDO = "apellido";
  private static final String COLUMN_EDAD = "edad";
  private static final String COLUMN_CORREO = "correo";
  private static final String COLUMN_DIRECCION = "direccion";

  private static final String CREATE_TABLE =
          "CREATE TABLE " + TABLE_PERSONAS + " (" +
                  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  COLUMN_NOMBRE + " TEXT, " +
                  COLUMN_APELLIDO + " TEXT, " +
                  COLUMN_EDAD + " TEXT, " +
                  COLUMN_CORREO + " TEXT, " +
                  COLUMN_DIRECCION + " TEXT);";

  public BaseDeDatos(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAS);
    onCreate(db);
  }
}
