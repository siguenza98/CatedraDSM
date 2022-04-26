package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class vista_inicio extends AppCompatActivity {
    String userId = "";
    private Button btnAgregarCita;
    private Button btnVerCitas;
    private Button btnConfigurarCuenta;
    private Button btnCerrarSesion;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_inicio);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        userId = datos.getString("userId");

        btnAgregarCita = (Button)findViewById(R.id.btnAgregarCita);
        btnVerCitas = (Button)findViewById(R.id.btnVerCitas);
        btnConfigurarCuenta = (Button)findViewById(R.id.btnConfigurarCuenta);
        btnCerrarSesion = (Button)findViewById(R.id.btnCerrarSesion);

        btnAgregarCita.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getBaseContext(), AgendarCita.class);
                i.putExtra("userId",userId);
                startActivity(i);
            }
        });

        btnVerCitas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getBaseContext(), HistorialCitas.class);
                i.putExtra("userId",userId);
                startActivity(i);
            }
        });

        btnConfigurarCuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getBaseContext(), configuracionDeCuenta.class);
                i.putExtra("userId",userId);
                startActivity(i);
            }
        });
        sharedpreferences = getSharedPreferences("usuario", 0);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit(); // commit changes
                Toast.makeText(vista_inicio.this, "Ha cerrado sesión.", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getBaseContext(), Login.class);
                startActivity(i);
            }
        });
    }
}