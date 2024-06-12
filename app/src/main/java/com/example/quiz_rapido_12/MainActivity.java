package com.example.quiz_rapido_12;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import utilidades.BaseDeDatos;

public class MainActivity extends AppCompatActivity {
  // Declaramos las variables que usaremos a lo largo
  // de la clase.
  private BaseDeDatos dbHelper;
  private SQLiteDatabase database;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Llamamos al dbHelper que seria la base de datos
    // y tambien obtenemos para que sea escribible.
    dbHelper = new BaseDeDatos(this);
    database = dbHelper.getWritableDatabase();

    // Creamos el boton guardar para guardar los datos
    // de la persona en la base de datos.
    Button guardar = findViewById(R.id.guardar);

    guardar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Llamamos al metodo privado de guardar personas.
        guardarPersonas();
      }
    });

    // Creamos el boton para ver las personas.
    Button verPersonas = findViewById(R.id.verPersonas);

    verPersonas.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Aqui con el intent lo que le estamos diciendo
        // es que se diriga a la ventana de ActivityList.
        Intent intent = new Intent(MainActivity.this, ActivityList.class);
        startActivity(intent);
      }
    });
  }

  private void guardarPersonas() {
    // Obtener referencias de los EditTexts y obtener sus valores.
    EditText nombreEditText = findViewById(R.id.nombre);
    EditText apellidoEditText = findViewById(R.id.apellido);
    EditText edadEditText = findViewById(R.id.edad);
    EditText correoEditText = findViewById(R.id.correo);
    EditText direccionEditText = findViewById(R.id.direccion);

    // Obteniendo sus valores
    String nombre = nombreEditText.getText().toString();
    String apellido = apellidoEditText.getText().toString();
    int edad = Integer.parseInt(edadEditText.getText().toString());
    String correo = correoEditText.getText().toString();
    String direccion = direccionEditText.getText().toString();

    // Aqui creamos un objeto ContentValues para almacenar
    // los valores de cada persona.
    ContentValues values = new ContentValues();
    values.put("nombre", nombre);
    values.put("apellido", apellido);
    values.put("edad", edad);
    values.put("correo", correo);
    values.put("direccion", direccion);

    // Aqui le estamos diciendo que en la tabla personas
    // inserte los valores que obtubimos.
    long newRowId = database.insert("personas", null, values);

    // Aqui mostramos un mensaje dependiendo si
    // los datos se guardaron en la base de datos.
    if (newRowId != -1) {
      Toast.makeText(this, "Datos Guardados!", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "Error Al Guardar.", Toast.LENGTH_LONG).show();
    }
  }
}