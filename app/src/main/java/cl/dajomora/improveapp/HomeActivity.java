package cl.dajomora.improveapp;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    LogrosFragment logrosFragment = new LogrosFragment();
    DesafiosFragment desafiosFragment = new DesafiosFragment();
    CuentaFragment cuentaFragment = new CuentaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.desafios);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logros:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, logrosFragment).commit();
                return true;

            case R.id.desafios:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, desafiosFragment).commit();
                return true;

            case R.id.cuenta:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, cuentaFragment).commit();
                return true;
        }
        return false;
    }
}
