package sv.edu.udb.catedradsm.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//EL MODELO DE LA APLICACION OCUPA UN SINGLETON PARA MANEJAR
//UNA UNICA INSTANCIA DE LISTA DE PETICIONES
public class ConexionModel {
    public static ConexionModel instance;
    public static Context ctx;
    public RequestQueue requestQueue;

    private ConexionModel(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized ConexionModel getInstance(Context context){
        if(instance == null){
            instance = new ConexionModel(context);
        }

        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}
