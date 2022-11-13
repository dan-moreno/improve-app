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
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class EditarCuentaActivity extends AppCompatActivity {

    private Bundle bundle;
    private TextInputEditText ed_nombre, ed_correo;
    private AutoCompleteTextView sp_ocupacion;
    private Button b_actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);
        bundle = getIntent().getExtras();

        ed_nombre = findViewById(R.id.userNombre);
        ed_correo = findViewById(R.id.userCorreo);
        sp_ocupacion = findViewById(R.id.ocupacionTextView);
        cargarOcupaciones();
        b_actualizar = findViewById(R.id.btnactualizar);

        ed_nombre.setText(bundle.getString("cuenta_nombre"));
        ed_correo.setText(bundle.getString("cuenta_correo"));

        b_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });
    }

    private void actualizar() {
        try
        {
            String nombreAnterior = bundle.getString("cuenta_nombre");
            String correoAnterior = bundle.getString("cuenta_correo");
            String nombreNuevo = ed_nombre.getText().toString();
            String correoNuevo = ed_correo.getText().toString();
            String ocupacionNueva = sp_ocupacion.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_USUARIOS", Context.MODE_PRIVATE,null);

            String sql = "UPDATE Usuario SET Nombre = ?, Correo = ?, Ocupacion = ? WHERE Nombre = ? AND Correo = ?";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,nombreNuevo);
            statement.bindString(2,correoNuevo);
            statement.bindString(3,ocupacionNueva);
            statement.bindString(4,nombreAnterior);
            statement.bindString(5,correoAnterior);
            statement.execute();

            Toast.makeText(this,"Datos actualizados",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("cuenta_nombre", nombreNuevo);
            intent.putExtra("cuenta_correo", correoNuevo);
            intent.putExtra("cuenta_ocupacion", ocupacionNueva);
            intent.putExtra("desafio_completado", bundle.getString("desafio_completado"));
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron actualizar los datos.",Toast.LENGTH_LONG).show();
        }
    }

    private void cargarOcupaciones() {
        ArrayList<String> ocupations = new ArrayList<>();
        ocupations.add("Estudiante");
        ocupations.add("Trabajador dependiente");
        ocupations.add("Trabajador independiente");
        ocupations.add("Cesante");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditarCuentaActivity.this, android.R.layout.simple_spinner_item, ocupations);
        sp_ocupacion.setAdapter(adapter);
    }
}
