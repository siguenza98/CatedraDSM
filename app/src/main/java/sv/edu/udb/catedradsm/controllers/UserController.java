package sv.edu.udb.catedradsm.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sv.edu.udb.catedradsm.models.ConexionModel;

public class UserController {
    String url = "https://udbdsmapi.000webhostapp.com/api/user";
    Context context;
    String respuesta = "";

    public UserController(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String mensaje);
        void onResponse(Object respuesta);
    }

    public void realizarPeticion(VolleyResponseListener volleyResponseListener){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
    }

    public void iniciarSesion(String correo, String pass, VolleyResponseListener volleyResponseListener){
        String apiUrl = url + "/iniciarSesion";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, apiUrl, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
    }


}