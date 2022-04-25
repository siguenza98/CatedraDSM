package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import sv.edu.udb.catedradsm.controllers.CrearCuentaController;
import sv.edu.udb.catedradsm.models.CrearCuentaModel;

public class crearCuenta extends AppCompatActivity {
    CrearCuentaController controller = new CrearCuentaController(crearCuenta.this);
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
                CrearCuentaModel crearCuenta = new CrearCuentaModel("Pamela","Velasquez","pamela.velasquez@gmail.com","77777777","12345");

                guardar(crearCuenta);
            }
        });
    }

    public void guardar(CrearCuentaModel crearCuenta){
        controller.realizarPeticion(crearCuenta, new CrearCuentaController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(crearCuenta.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    String mensaje = obj.getString("respuesta");
                    String code = obj.getString("code");

                    if(mensaje.equals("OK")){
                        System.out.println("EXITO");
                    }else{
                        System.out.println("ERROR");
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }
}