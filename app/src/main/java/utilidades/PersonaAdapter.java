package utilidades;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_rapido_12.ActivityEdit;
import com.example.quiz_rapido_12.R;

import java.util.ArrayList;

public class PersonaAdapter extends BaseAdapter {
  // Declaracion de las variables a utilizar.
  private Context context;
  private ArrayList<Persona> personasList;
  private BaseDeDatos dbHelper;

  // Constructor para la clase PersonaAdapter que inicializa las variables.
  public PersonaAdapter(Context context, ArrayList<Persona> personasList, BaseDeDatos dbHelper) {
    this.context = context;
    this.personasList = personasList;
    this.dbHelper = dbHelper;
  }

  // Este metodo devuelve el numero de personas en la lista de personas.
  @Override
  public int getCount() {
    return personasList.size();
  }

  // Este metodo devuelve el objeto persona en la pocision deseada.
  @Override
  public Object getItem(int position) {
    return personasList.get(position);
  }

  // El metodo devuelve la pocision del elemento en la pocision que le digamos.
  @Override
  public long getItemId(int position) {
    return position;
  }

  // El metodo devuelve la vista para un elemento en la pocision que le digamos.
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Si la vista no a sido creada usamos el LayoutInflater para
    // construir la vista de nuestro objeto.
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_persona, parent, false);
    }

    // Obtenemos el objeto persona en la pocision actual.
    Persona persona = (Persona) getItem(position);

    // Aqui obtenemos las referencias de cada TextView y le asignamos los valores.
    TextView nombreTextView = convertView.findViewById(R.id.nombreTextView);
    TextView apellidoTextView = convertView.findViewById(R.id.apellidoTextView);
    TextView edadTextView = convertView.findViewById(R.id.edadTextView);
    TextView correoTextView = convertView.findViewById(R.id.correoTextView);
    TextView direccionTextView = convertView.findViewById(R.id.direccionTextView);

    // Aqui asignamos.
    nombreTextView.setText(persona.getNombre());
    apellidoTextView.setText(persona.getApellido());
    edadTextView.setText(String.valueOf(persona.getEdad()));
    correoTextView.setText(persona.getCorreo());
    direccionTextView.setText(persona.getDireccion());

    // Creacion de la variable editar
    Button editar = convertView.findViewById(R.id.editar);

    editar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Con el intent podemos pasar los datos al ActivityInit y abrirlo al final.
        Intent intent = new Intent(context, ActivityEdit.class);
        intent.putExtra("id", persona.getId());
        intent.putExtra("nombre", persona.getNombre());
        intent.putExtra("apellido", persona.getApellido());
        intent.putExtra("edad", persona.getEdad());
        intent.putExtra("correo", persona.getCorreo());
        intent.putExtra("direccion", persona.getDireccion());
        context.startActivity(intent);
      }
    });

    // Creacion de la variable eliminar.
    Button eliminar = convertView.findViewById(R.id.eliminar);


    eliminar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Llamar a la funcion eliminarPersona y le pasamos el id
        eliminarPersona(persona.getId());
      }
    });

    // Devolver la vista creada o actualizada.
    return convertView;
  }

  // Metodo Privado para borrar la persona se encuentra en el activity list
  private void eliminarPersona(int id) {
    // Poder acceder a la base de datos para escribir.
    SQLiteDatabase database = dbHelper.getWritableDatabase();

    // Con esta linea podemos decirle que elimine la persona segun su id
    // en la base de datos.
    int rowsDeleted = database.delete("personas", "id = ?", new String[]{String.valueOf(id)});

    // Con esto le decimos si se realizo correctamente la eliminacion
    // de la persona usando un Toast.
    if (rowsDeleted > 0) {
      Toast.makeText(context, "Persona eliminada!", Toast.LENGTH_SHORT).show();
      personasList.removeIf(persona -> persona.getId() == id);
      notifyDataSetChanged();
    } else {
      Toast.makeText(context, "Hubo un error.", Toast.LENGTH_LONG).show();
    }
  }
}
