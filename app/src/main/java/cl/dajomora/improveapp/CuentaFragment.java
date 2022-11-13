package cl.dajomora.improveapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class CuentaFragment extends Fragment {

    private TextView tv_nombre, tv_correo, tv_ocupacion;
    private Button b_editar, b_salir, b_eliminar;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cuenta, container, false);
        bundle = getActivity().getIntent().getExtras();

        tv_nombre = (TextView) rootView.findViewById(R.id.userNombre);
        tv_correo = (TextView) rootView.findViewById(R.id.userCorreo);
        tv_ocupacion = (TextView) rootView.findViewById(R.id.userOcupacion);

        tv_nombre.setText(bundle.getString("cuenta_nombre"));
        tv_correo.setText(bundle.getString("cuenta_correo"));
        tv_ocupacion.setText(bundle.getString("cuenta_ocupacion"));

        b_editar = (Button) rootView.findViewById(R.id.botonEditar);
        b_salir = (Button) rootView.findViewById(R.id.botonSalir);
        b_eliminar = (Button) rootView.findViewById(R.id.botonEliminar);

        b_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        b_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        return rootView;
    }

    private void editar() {
        Intent intent = new Intent(getActivity(), EditarCuentaActivity.class);
        intent.putExtra("cuenta_nombre", tv_nombre.getText().toString());
        intent.putExtra("cuenta_correo", tv_correo.getText().toString());
        intent.putExtra("cuenta_ocupacion", tv_ocupacion.getText().toString());
        intent.putExtra("desafio_completado", bundle.getString("desafio_completado"));
        startActivity(intent);
    }

    public void salir() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("desafio_completado", bundle.getString("desafio_completado"));
        startActivity(intent);
    }

    private void eliminar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("¿Está seguro/a?");
        builder.setMessage("Esta acción no se puede deshacer");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try
                {
                    String nombre = tv_nombre.getText().toString();
                    String correo = tv_correo.getText().toString();
                    SQLiteDatabase db = getActivity().openOrCreateDatabase("BD_USUARIOS", Context.MODE_PRIVATE,null);
                    String sql = "DELETE FROM Usuario WHERE Nombre = ? AND Correo = ?";
                    SQLiteStatement statement = db.compileStatement(sql);
                    statement.bindString(1,nombre);
                    statement.bindString(2,correo);
                    statement.execute();
                    Toast.makeText(getActivity(),"Cuenta eliminada",Toast.LENGTH_LONG).show();
                    salir();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(),"Error no se pudieron eliminar los datos",Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
