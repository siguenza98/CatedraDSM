package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.controllers.CitasController;
import sv.edu.udb.catedradsm.models.CitasModel;

public class VerCita extends AppCompatActivity {
    CitasController controller = new CitasController(VerCita.this);
    private EditText edtMotivo;
    private EditText edtSucursal;
    private EditText edtEstilista;
    private EditText edtFecha;
    private EditText edtHora;
    private Button btnVolver;
    private String userId = "";
    private String idCita = "";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cita);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        userId = datos.getString("userId");
        idCita = datos.getString("idCita");

        edtMotivo = (EditText) findViewById(R.id.edtMotivo);
        edtSucursal = (EditText) findViewById(R.id.edtSucursal);
        edtEstilista = (EditText) findViewById(R.id.edtEstilista);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        edtHora = (EditText) findViewById(R.id.edtHora);
        btnVolver = (Button) findViewById(R.id.btnVolver);


        CitasModel cita = new CitasModel(Integer.parseInt(idCita));
        cargarDatos(cita);

        btnVolver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void cargarDatos(CitasModel cita){
        controller.verCita(cita, new CitasController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(VerCita.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());

                    System.out.println("respuesta: "+respuesta.toString());

                    if(respuesta.toString().length()>0){
                        JSONArray jsonArray = obj.getJSONArray("cita");
                        JSONObject item = (JSONObject)jsonArray.get(0);

                        String id = item.getString("id");
                        String idcliente = item.getString("idcliente");
                        String idestilista = item.getString("idestilista");
                        String idsucursal = item.getString("idsucursal");
                        String motivo = item.getString("motivo");
                        String fecha = item.getString("fecha");
                        String hora = item.getString("hora");
                        String costototal = item.getString("costototal");
                        String sucursal = item.getString("nombreSucursal");
                        String estilista = item.getString("nombreEstilista") + " " + item.getString("apellidoEstilista");

                        edtMotivo.setText(motivo);
                        edtEstilista.setText(estilista);
                        edtSucursal.setText(sucursal);
                        edtFecha.setText(fecha);
                        edtHora.setText(hora);


                    }else{
                        System.out.println("mensaje desde response de la vista");
                        Toast.makeText(VerCita.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}