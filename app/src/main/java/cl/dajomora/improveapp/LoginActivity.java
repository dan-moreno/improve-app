package cl.dajomora.improveapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText ed_correo, ed_pass;
    private Button b_registrar, b_entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_correo = findViewById(R.id.userCorreo);
        ed_pass = findViewById(R.id.userPass);
        b_entrar = findViewById(R.id.botonEntrar);
        b_registrar = findViewById(R.id.botonRegistrar);

        b_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_correo.getText().toString().isEmpty() || ed_pass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingrese todos los datos",Toast.LENGTH_LONG).show();
                }
                else{
                    iniciarSesion();
                }
            }
        });

        b_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion() {
        try
        {
            String correo = ed_correo.getText().toString();
            String pass = ed_pass.getText().toString();
            SQLiteDatabase db = openOrCreateDatabase("BD_USUARIOS", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS Usuario(ID INTEGER PRIMARY KEY AUTOINCREMENT, Nombre VARCHAR, Correo VARCHAR, Pass VARCHAR, Ocupacion VARCHAR)");

            final Cursor c = db.rawQuery("SELECT * FROM Usuario",null);
            int c_nombre = c.getColumnIndex("Nombre");
            int c_correo = c.getColumnIndex("Correo");
            int c_pass = c.getColumnIndex("Pass");
            int c_ocupacion = c.getColumnIndex("Ocupacion");
            boolean flag = true;

            if(c.moveToFirst())
            {
                do{
                    if(correo.equals(c.getString(c_correo)) && pass.equals(c.getString(c_pass))){
                        flag = false;
                        Toast.makeText(this,"Sesion iniciada",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("cuenta_nombre", c.getString(c_nombre));
                        intent.putExtra("cuenta_correo", c.getString(c_correo));
                        intent.putExtra("cuenta_ocupacion", c.getString(c_ocupacion));
                        intent.putExtra("desafio_completado", "");
                        startActivity(intent);
                    }
                } while(c.moveToNext());
            }

            if(flag){
                ed_correo.setText("");
                ed_pass.setText("");
                ed_correo.requestFocus();
                Toast.makeText(this,"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Error no se pudieron guardar los datos.",Toast.LENGTH_LONG).show();
        }

    }
}
