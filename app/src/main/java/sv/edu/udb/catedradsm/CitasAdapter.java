package sv.edu.udb.catedradsm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sv.edu.udb.catedradsm.models.CitasModel;

public class CitasAdapter extends ArrayAdapter<CitasModel> {
    List<CitasModel> citas;
    private Activity context;

    public CitasAdapter(@NonNull Activity context, @NonNull List<CitasModel> citas) {
        super(context, R.layout.activity_cita_layout, citas);
        this.context = context;
        this.citas = citas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=null;
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        if (view == null)
            rowview = layoutInflater.inflate(R.layout.activity_cita_layout,null);
        else rowview = view;

        TextView tvMotivo = rowview.findViewById(R.id.tvMotivo);
        TextView tvSucursal = rowview.findViewById(R.id.tvSucursal);
        TextView tvEstilista = rowview.findViewById(R.id.tvEstilista);
        TextView tvFecha = rowview.findViewById(R.id.tvFecha);
        TextView tvHora = rowview.findViewById(R.id.tvHora);
        TextView tvId = rowview.findViewById(R.id.tvId);

        tvId.setText(String.valueOf(citas.get(position).getId()));
        tvMotivo.setText("Motivo : "+ citas.get(position).getMotivo());
        tvSucursal.setText("Sucursal : " + citas.get(position).getSucursal());
        tvEstilista.setText("Estilista : " + citas.get(position).getEstilista());
        tvFecha.setText("Fecha : " + citas.get(position).getFecha());
        tvHora.setText("Hora : " + citas.get(position).getHora());

        return rowview;
    }
}
