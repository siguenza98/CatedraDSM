package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.controllers.CuentaController;
import sv.edu.udb.catedradsm.models.CuentaModel;

public class crearCuenta extends AppCompatActivity {
    CuentaController controller = new CuentaController(crearCuenta.this);
    private EditText txtNombreCrearCuenta;
    private EditText txtApellidoCrearCuenta;
    private EditText txtTelefonoCrearCuenta;
    private EditText txtCorreoCrearCuenta;
    private EditText txtContrasenaCrearCuenta;
    private EditText txtRepetirContrasenaCrearCuenta;
    private Button btnCrearCuenta;
    private TextView txtIniciaSesion;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        txtNombreCrearCuenta = (EditText) findViewById(R.id.txtNombreCrearCuenta);
        txtApellidoCrearCuenta = (EditText) findViewById(R.id.txtApellidoCrearCuenta);
        txtTelefonoCrearCuenta = (EditText) findViewById(R.id.txtTelefonoCrearCuenta);
        txtCorreoCrearCuenta = (EditText) findViewById(R.id.txtCorreoCrearCuenta);
        txtContrasenaCrearCuenta = (EditText) findViewById(R.id.txtContrasenaCrearCuenta);
        txtRepetirContrasenaCrearCuenta = (EditText) findViewById(R.id.txtRepetirContrasenaCrearCuenta);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        txtIniciaSesion = (TextView) findViewById(R.id.txtIniciaSesion);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Metodo click");
                String nombre = txtNombreCrearCuenta.getText().toString();
                String apellido = txtApellidoCrearCuenta.getText().toString();
                String telefono = txtTelefonoCrearCuenta.getText().toString();
                String correo = txtCorreoCrearCuenta.getText().toString();
                String contrasena = txtContrasenaCrearCuenta.getText().toString();
                System.out.println("invocando método del controller");
                CuentaModel crearCuenta = new CuentaModel(nombre,apellido,correo,telefono,contrasena);

                guardar(crearCuenta);
            }
        });

        txtIniciaSesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(crearCuenta.this, "CLICK", Toast.LENGTH_SHORT).show();

                //REDIRECCIONAR A CREAR CUENTA
                Intent i = new Intent(getBaseContext(), Login.class);

                startActivity(i);
            }
        });
    }

    public void guardar(CuentaModel crearCuenta){
        controller.realizarPeticion(crearCuenta, new CuentaController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(crearCuenta.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());

                    System.out.println("respuesta: "+respuesta.toString());

                    String mensaje = obj.getString("resultado");

                    if(mensaje.equals("OK")){
                        Toast.makeText(crearCuenta.this, "Cuenta creada con exito.", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getBaseContext(), Login.class);

                        startActivity(i);
                    }else{
                        Toast.makeText(crearCuenta.this, "Hubo un error.", Toast.LENGTH_SHORT).show();
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }
}