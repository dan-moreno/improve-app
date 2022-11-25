package cl.dajomora.improveapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.IOException;

public class CuentaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SELECT_IMAGE = 1;
    private TextView tv_nombre, tv_correo, tv_ocupacion;
    private ImageView iv_foto;
    private Bundle bundle;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;

    public CuentaFragment() {
    }

    public static CuentaFragment newInstance(String param1, String param2) {
        CuentaFragment fragment = new CuentaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        StorageReference imagenCuenta = storageRef.child("imagenCuenta.jpg");

        try {
            File localfile = File.createTempFile("images","jpg");
            imagenCuenta.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(getContext())
                            .load(localfile)
                            .fitCenter()
                            .centerCrop()
                            .into(iv_foto);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = getActivity().getIntent().getExtras();

        iv_foto = view.findViewById(R.id.imgCuenta);
        tv_nombre = view.findViewById(R.id.userNombre);
        tv_correo = view.findViewById(R.id.userCorreo);
        tv_ocupacion = view.findViewById(R.id.userOcupacion);

        tv_nombre.setText(bundle.getString("cuenta_nombre"));
        tv_correo.setText(bundle.getString("cuenta_correo"));
        tv_ocupacion.setText(bundle.getString("cuenta_ocupacion"));

        Button b_imagen = view.findViewById(R.id.botonImagen);
        Button b_editar = view.findViewById(R.id.botonEditar);
        Button b_salir = view.findViewById(R.id.botonSalir);
        Button b_eliminar = view.findViewById(R.id.botonEliminar);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        progressDialog = new ProgressDialog(getContext());

        b_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,SELECT_IMAGE);
            }
        });
        
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK){

            progressDialog.setTitle("Subiendo...");
            progressDialog.setMessage("Subiendo foto al sistema");
            progressDialog.show();

            Uri uri = data.getData();
            StorageReference filePath = storageRef.child("imagenCuenta.jpg");


            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getContext())
                                    .load(uri)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(iv_foto);
                        }
                    });
                }
            });
        }
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
