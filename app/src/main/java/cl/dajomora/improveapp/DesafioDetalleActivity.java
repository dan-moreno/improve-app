package cl.dajomora.improveapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DesafioDetalleActivity extends AppCompatActivity {

    private Bundle bundle;
    private TextView tvTitulo, tvDescripcion;
    private ImageView ivIcono;
    private Button botonAceptar;
    private CheckBox cb_completado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio_detalle);
        bundle = getIntent().getExtras();

        ivIcono = (ImageView) findViewById(R.id.iconoDesafioDetalle);
        tvTitulo = (TextView) findViewById(R.id.tituloDesafioDetalle);
        tvDescripcion = (TextView) findViewById(R.id.descripcionDesafioDetalle);
        cb_completado = (CheckBox) findViewById(R.id.checkBoxCompletado);

        int icono = bundle.getInt("icono");
        String titulo = bundle.getString("titulo");
        String descripcion = bundle.getString("descripcion");

        ivIcono.setImageResource(icono);
        tvTitulo.setText(titulo);
        tvDescripcion.setText(descripcion);

        botonAceptar = (Button) findViewById(R.id.botonAceptar);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volver(view);
            }
        });

    }

    public void volver(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        if(cb_completado.isChecked()){
            intent.putExtra("desafio_completado", tvTitulo.getText().toString());
        }
        else{
            intent.putExtra("desafio_completado", "");
        }
        intent.putExtra("cuenta_nombre", bundle.getString("cuenta_nombre"));
        intent.putExtra("cuenta_correo", bundle.getString("cuenta_correo"));
        intent.putExtra("cuenta_ocupacion", bundle.getString("cuenta_ocupacion"));
        startActivity(intent);
    }

}
