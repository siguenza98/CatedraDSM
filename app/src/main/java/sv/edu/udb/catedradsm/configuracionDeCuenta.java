package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;


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

        CuentaModel cuenta = new CuentaModel("11");
        System.out.println("invocando método del controller ver cita");

        cargarDatos(cuenta);

    }

    public void cargarDatos(CuentaModel cuenta){
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
                        /*String contrasena = obj.getString("fecha");
                        String repetirContrasena = obj.getString("hora");
                        String costototal = obj.getString("costototal");*/

                        txtNombreConfigurarCuenta.setText(nombre);
                        txtApellidoConfigurarCuenta.setText(apellido);
                        txtTelefonoConfigurarCuenta.setText(telefono);
                        txtCorreoConfigurarCuenta.setText(correo);


                    }else{
                        Toast.makeText(configuracionDeCuenta.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }

                    /*String mensaje = obj.getString("respuesta");
                    String code = obj.getString("code");
                    if(mensaje.equals("OK")){
                        Toast.makeText(VerCita.this, "Cita agendada con éxito", Toast.LENGTH_SHORT).show();
                        //System.out.println(obj.toString());
                        //redirigir hacia activity principal
                    }else{
                        System.out.println("ERROR");
                    }*/

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}