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

public class ActivityEdit extends AppCompatActivity {
  // Declaramos las variables que vamos a usar.
  private BaseDeDatos dbHelper;
  private SQLiteDatabase database;
  private EditText nombreEditText, apellidoEditText, edadEditText, correoEditText, direccionEditText;
  private int personaId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_edit);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Llamamos al dbHelper que seria la base de datos
    // y tambien obtenemos para que sea escribible.
    dbHelper = new BaseDeDatos(this);
    database = dbHelper.getWritableDatabase();

    // Primeramente declaramos los editText que vienen
    // del activity_edit.xml.
    nombreEditText = findViewById(R.id.nombreEditText);
    apellidoEditText = findViewById(R.id.apellidoEditText);
    edadEditText = findViewById(R.id.edadEditText);
    correoEditText = findViewById(R.id.correoEditText);
    direccionEditText = findViewById(R.id.direccionEditText);

    // Aqui le estamos diciendo que con ayuda del intent
    // obtenga los datos que pasaron del ActivityList.java
    // al ActivityEdit.java.
    Intent intent = getIntent();
    personaId = intent.getIntExtra("id", -1);
    String nombre = intent.getStringExtra("nombre");
    String apellido = intent.getStringExtra("apellido");
    int edad = intent.getIntExtra("edad", -1);
    String correo = intent.getStringExtra("correo");
    String direccion = intent.getStringExtra("direccion");

    // Y seteamos los valores que obtuvimos.
    nombreEditText.setText(nombre);
    apellidoEditText.setText(apellido);
    edadEditText.setText(String.valueOf(edad));
    correoEditText.setText(correo);
    direccionEditText.setText(direccion);

    // Declaramos la variable para guardar la persona.
    Button guardarPersona = findViewById(R.id.guardar);

    guardarPersona.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Llamamos al metodo guardar.
        guardar();
      }
    });
  }
  private void guardar() {
    // Obtenemos los valores del EditText que ingresa el usuario.
    String nombre = nombreEditText.getText().toString();
    String apellido = apellidoEditText.getText().toString();
    int edad = Integer.parseInt(edadEditText.getText().toString());
    String correo = correoEditText.getText().toString();
    String direccion = direccionEditText.getText().toString();

    // Almacenamos los valores en el objeto ContentValues.
    ContentValues values = new ContentValues();
    values.put("nombre", nombre);
    values.put("apellido", apellido);
    values.put("edad", edad);
    values.put("correo", correo);
    values.put("direccion", direccion);

    // Aqui le decimos que ponga los valores que puso el usuario
    // en la tabla personas dependiendo el id de la persona.
    int rowsModified = database.update("personas", values, "id = ?", new String[]{String.valueOf(personaId)});

    // Por ultimo le decimos si hubo alguna
    // fila modificada.
    if (rowsModified > 0) {
      // Mostrar mensaje del toast.
      Toast.makeText(this, "Persona Actualizada!", Toast.LENGTH_SHORT).show();
      // Mandar al usuario con ayuda del
      // intent al ActivityList.
      Intent intent = new Intent(ActivityEdit.this, ActivityList.class);
      startActivity(intent);
    } else {
      Toast.makeText(this, "Error al actualizar personas.", Toast.LENGTH_LONG).show();
    }
  }
}