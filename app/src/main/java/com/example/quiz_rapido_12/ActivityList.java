package com.example.quiz_rapido_12;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import utilidades.BaseDeDatos;
import utilidades.Persona;
import utilidades.PersonaAdapter;

public class ActivityList extends AppCompatActivity {
  // Declaracion de las variables a utilizar.
  private BaseDeDatos dbHelper;
  private SQLiteDatabase database;
  private ListView listView;
  private PersonaAdapter adapter;
  private ArrayList<Persona> personaList;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_list);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Llamamos al dbHelper que seria la base de datos
    // y tambien obtenemos para que sea escribible.
    dbHelper = new BaseDeDatos(this);
    database = dbHelper.getWritableDatabase();

    // Aqui declaramos listView al listView que
    // tenemos en el layout. Tambien inicializamos
    // la nueva clase personaList.
    listView = findViewById(R.id.listView);
    personaList = new ArrayList<>();

    // Llamamos al metodo privado para cargar las
    // personas de la base de datos (las que esten).
    mostrarPersonas();

    // Inicializamos el adaptador y lo seteamos con el
    // listView.
    adapter = new PersonaAdapter(this, personaList, dbHelper);
    listView.setAdapter(adapter);

    // Declaramos la variable regresar.
    Button regresar = findViewById(R.id.regresar);

    regresar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // El intent nos llevara de nuevo al ActivityMain.
        Intent intent = new Intent(ActivityList.this, MainActivity.class);
        startActivity(intent);
      }
    });
  }

  // Método privado para mostrar las personas desde la base de datos
  private void mostrarPersonas () {
    // Aqui decimos que queremos consultar la tabla personas
    // en la base de datos.
    Cursor cursor = database.query("personas", null, null, null, null, null, null);

    // Verificamos si el cursor trajo nulo
    // sino entonces podemos entrar.
    if (cursor != null) {
      // Recorremos el cursor cada elemento y nos
      // movemos al siguiente por cada vuelta.
      while (cursor.moveToNext()) {
        // Obtenemos cada valor de la fila actual.
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"));
        int edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"));
        String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));
        String direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"));

        // Creamos un nuevo objeto con los valores que obtuvimos en cada vuelta.
        Persona persona = new Persona(id, nombre, apellido, edad, correo, direccion);

        // Los agregamos a la lista de personas.
        personaList.add(persona);
      }
      // Cerramos el cursor
      cursor.close();
    }
  }
}