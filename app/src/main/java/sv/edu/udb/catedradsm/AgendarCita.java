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

import sv.edu.udb.catedradsm.controllers.CitasController;
import sv.edu.udb.catedradsm.models.CitasModel;

public class AgendarCita extends AppCompatActivity {
    CitasController controller = new CitasController(AgendarCita.this);
    private EditText edtNombre;
    private EditText edtSucursal;
    private EditText edtEstilista;
    private EditText edtFecha;
    private EditText edtHora;
    private Button btnAgregar;
    private Button btnCancelar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtSucursal = (EditText) findViewById(R.id.edtSucursal);
        edtEstilista = (EditText) findViewById(R.id.edtEstilista);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtHora = (EditText) findViewById(R.id.edtHora);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println("Metodo click");
                String nombre = edtNombre.getText().toString();
                String sucursal = edtNombre.getText().toString();
                String estilista = edtNombre.getText().toString();
                String fecha = edtNombre.getText().toString();
                String hora = edtNombre.getText().toString();
                System.out.println("invocando método del controller");
                CitasModel cita = new CitasModel(1,1,1,"prueba","2022-05-10","3:30 pm",0.00);

                guardar(cita);
            }
        });
    }

    public void guardar(CitasModel citas){
        controller.realizarPeticion(citas, new CitasController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(AgendarCita.this, mensaje, Toast.LENGTH_SHORT).show();
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