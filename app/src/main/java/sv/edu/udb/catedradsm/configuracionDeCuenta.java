package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.controllers.CuentaController;
import sv.edu.udb.catedradsm.models.CuentaModel;

public class configuracionDeCuenta extends AppCompatActivity {
    CuentaController controller = new CuentaController(configuracionDeCuenta.this);
    private EditText txtNombreConfigurarCuenta;
    private EditText txtApellidoConfigurarCuenta;
    private EditText txtTelefonoConfigurarCuenta;
    private EditText txtCorreoConfigurarCuenta;
    private EditText txtContrasenaConfigurarCuenta;
    private EditText txtRepetirContrasenaConfigurarCuenta;
    private Button btnGuardarConfiguracion;
    private Button btnVolverAInicio;
    String userId = "";
    String userNombre = "";
    String userApellido = "";
    String userTelefono = "";
    String userCorreo = "";
    String userPassword = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_de_cuenta);

        txtNombreConfigurarCuenta = (EditText) findViewById(R.id.txtNombreConfigurarCuenta);
        txtApellidoConfigurarCuenta = (EditText) findViewById(R.id.txtApellidoConfigurarCuenta);
        txtTelefonoConfigurarCuenta = (EditText) findViewById(R.id.txtTelefonoConfigurarCuenta);
        txtCorreoConfigurarCuenta = (EditText) findViewById(R.id.txtCorreoConfigurarCuenta);
        txtContrasenaConfigurarCuenta = (EditText) findViewById(R.id.txtContrasenaConfigurarCuenta);
        txtRepetirContrasenaConfigurarCuenta = (EditText) findViewById(R.id.txtRepetirContrasenaConfigurarCuenta);
        btnGuardarConfiguracion = (Button) findViewById(R.id.btnGuardarConfiguracion);
        btnVolverAInicio = (Button) findViewById(R.id.btnVolverAInicio);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        userId = datos.getString("userId");

        CuentaModel cuenta = new CuentaModel(userId);


        System.out.println("invocando método del controller ver cita");

        cargarDatos(cuenta);


        btnGuardarConfiguracion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CuentaModel cuentaA = new CuentaModel(userId,userNombre,userApellido,userCorreo,userTelefono,userPassword);
                actualizarDatos(cuentaA);
            }
        });

        btnVolverAInicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void actualizarDatos(CuentaModel cuentaA){
        cuentaA.setId(userId);
        cuentaA.setNombre(txtNombreConfigurarCuenta.getText().toString());
        cuentaA.setApellido(txtApellidoConfigurarCuenta.getText().toString());
        cuentaA.setTelefono(txtTelefonoConfigurarCuenta.getText().toString());
        cuentaA.setCorreo(txtCorreoConfigurarCuenta.getText().toString());
        cuentaA.setPassword(txtContrasenaConfigurarCuenta.getText().toString());
        controller.actualizarCuenta(cuentaA, new CuentaController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(configuracionDeCuenta.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    if(respuesta.toString().length()>0){

                        /*String contrasena = obj.getString("fecha");
                        String repetirContrasena = obj.getString("hora");
                        String costototal = obj.getString("costototal");*/
                        if(txtContrasenaConfigurarCuenta.getText().toString().trim().equals(txtRepetirContrasenaConfigurarCuenta.getText().toString().trim())){


                            Toast.makeText(configuracionDeCuenta.this, "Cambios realizados", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getBaseContext(), vista_inicio.class);
                            i.putExtra("userId",userId);
                            startActivity(i);

                        }else{
                            Toast.makeText(configuracionDeCuenta.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(configuracionDeCuenta.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }


                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void cargarDatos(CuentaModel cuenta){
        cuenta.setId(userId);
        controller.verCuenta(cuenta, new CuentaController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(configuracionDeCuenta.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    if(respuesta.toString().length()>0){
                        String id = obj.getString("id");
                        String nombre = obj.getString("nombre");
                        String apellido = obj.getString("apellido");
                        String telefono = obj.getString("telefono");
                        String correo = obj.getString("correo");
                        String password = obj.getString("password");
                        /*String contrasena = obj.getString("fecha");
                        String repetirContrasena = obj.getString("hora");
                        String costototal = obj.getString("costototal");*/

                        txtNombreConfigurarCuenta.setText(nombre);
                        txtApellidoConfigurarCuenta.setText(apellido);
                        txtTelefonoConfigurarCuenta.setText(telefono);
                        txtCorreoConfigurarCuenta.setText(correo);
                        //txtContrasenaConfigurarCuenta.setText(password);
                        //txtRepetirContrasenaConfigurarCuenta.setText(password);

                    }else{
                        Toast.makeText(configuracionDeCuenta.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }


                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
