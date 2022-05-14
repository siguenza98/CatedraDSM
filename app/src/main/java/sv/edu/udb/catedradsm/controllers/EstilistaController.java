package sv.edu.udb.catedradsm.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.models.EstilistaModel;

import sv.edu.udb.catedradsm.models.ConexionModel;


public class EstilistaController {
    String url = "";
    Context context;
    String respuesta = "";

    public EstilistaController(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String mensaje);
        void onResponse(Object respuesta);
    }

    public void obtenerEstilistas(int idSucursal, EstilistaController.VolleyResponseListener volleyResponseListener){
        url = "https://udbdsmapi.000webhostapp.com/api/estilista/getEstilistasBySucursalId";
        JSONObject datos = new JSONObject();
        try{
            datos.put("idsucursal",idSucursal);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datos,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        volleyResponseListener.onResponse(response);
                    }catch(Exception e){
                        Toast.makeText(context, "Hubo un error al realizar la petición", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyResponseListener.onError("Hubo un error al realizar la petición.");
                }

            });
            ConexionModel.getInstance(context).addToRequestQueue(request);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
