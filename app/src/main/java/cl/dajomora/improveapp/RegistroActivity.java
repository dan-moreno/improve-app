package cl.dajomora.improveapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText ed_nombre, ed_correo, ed_pass, ed_pass2;
    private AutoCompleteTextView sp_ocupacion;
    private CheckBox cb_terminos;
    private Button b_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ed_nombre = findViewById(R.id.userNombre);
        ed_correo = findViewById(R.id.userCorreo);
        ed_pass = findViewById(R.id.userPass);
        ed_pass2 = findViewById(R.id.userPass2);
        sp_ocupacion = findViewById(R.id.ocupacionTextView);
        cargarOcupaciones();
        cb_terminos = findViewById(R.id.checkBoxTerminos);
        b_registrar = findViewById(R.id.btnregistrar);

        b_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_nombre.getText().toString().isEmpty() || ed_correo.getText().toString().isEmpty() || ed_pass.getText().toString().isEmpty() ||
                        ed_pass2.getText().toString().isEmpty() || sp_ocupacion.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Complete todos los campos",Toast.LENGTH_LONG).show();
                }
                else{
                    if(cb_terminos.isChecked()){
                        if(ed_pass.getText().toString().equals(ed_pass2.getText().toString())){
                            registrar();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Debe aceptar los términos",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void cargarOcupaciones() {
        ArrayList<String> ocupations = new ArrayList<>();
        ocupations.add("Estudiante");
        ocupations.add("Trabajador dependiente");
        ocupations.add("Trabajador independiente");
        ocupations.add("Cesante");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(RegistroActivity.this, android.R.layout.simple_spinner_item, ocupations);
        sp_ocupacion.setAdapter(adapter);
    }

    public void registrar() {
        try
        {
            String nombre = ed_nombre.getText().toString();
            String correo = ed_correo.getText().toString();
            String pass = ed_pass.getText().toString();
            String ocupacion = sp_ocupacion.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("BD_USUARIOS", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS Usuario(ID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre VARCHAR, Correo VARCHAR, Pass VARCHAR, Ocupacion VARCHAR)");

            String sql = "INSERT INTO Usuario(Nombre,Correo,Pass,Ocupacion)values(?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nombre);
            statement.bindString(2,correo);
            statement.bindString(3,pass);
            statement.bindString(4,ocupacion);
            statement.execute();
            Toast.makeText(this,"Datos agregados satisfactoriamente en la base de datos.",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }
    }
}
