package sv.edu.udb.catedradsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sv.edu.udb.catedradsm.controllers.CitasController;
import sv.edu.udb.catedradsm.models.CitasModel;

public class HistorialCitas extends AppCompatActivity {
    CitasController controller = new CitasController(HistorialCitas.this);
    SharedPreferences sharedPreferences;
    List<CitasModel> listaCitas;
    ListView listadoCitas;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_citas);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        userId = datos.getString("userId");

        listadoCitas = findViewById(R.id.listadoCitas);

        listadoCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String idSeleccionado = String.valueOf(listaCitas.get(i).getId());

                Intent intent = new Intent(getBaseContext(), VerCita.class);
                intent.putExtra("userId",userId);
                intent.putExtra("idCita",idSeleccionado);
                startActivity(intent);
            }
        });

        CitasModel cita = new CitasModel();
        cita.setIdCliente(Integer.parseInt(userId));
        cargarDatos(cita);
    }

    public void cargarDatos(CitasModel cita){
        controller.historialCitas(cita, new CitasController.VolleyResponseListener() {
            @Override
            public void onError(String mensaje) {
                Toast.makeText(HistorialCitas.this, mensaje, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Object respuesta) {
                try{
                    JSONObject obj = new JSONObject(respuesta.toString());
                    String mensaje = obj.getString("respuesta");

                    if(mensaje.equals("OK")){
                        JSONArray jsonArray = obj.getJSONArray("citas");
                        listaCitas = new ArrayList<CitasModel>();
                        CitasModel cita;

                        for(int i = 0; i<jsonArray.length(); i++){
                            cita = new CitasModel();
                            JSONObject item = (JSONObject)jsonArray.get(i);
                            cita.setId(Integer.parseInt(item.getString("id")));
                            cita.setMotivo(item.getString("motivo"));
                            cita.setSucursal(item.getString("nombreSucursal"));
                            cita.setEstilista(item.getString("nombreEstilista")+" "+item.getString("apellidoEstilista"));
                            cita.setFecha(item.getString("fecha"));
                            cita.setHora(item.getString("hora"));

                            listaCitas.add(cita);
                        }

                        llenarListView();
                    }else{
                        Toast.makeText(HistorialCitas.this, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void llenarListView(){
        CitasAdapter adapter = new CitasAdapter(HistorialCitas.this, listaCitas);
        listadoCitas.setAdapter(adapter);
    }
}