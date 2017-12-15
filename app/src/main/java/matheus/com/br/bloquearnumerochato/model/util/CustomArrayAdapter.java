package matheus.com.br.bloquearnumerochato.model.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import matheus.com.br.bloquearnumerochato.R;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;

public class CustomArrayAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private List<Blacklist> records;

    @SuppressWarnings("unchecked")
    public CustomArrayAdapter(Context context, int resource, @SuppressWarnings("rawtypes") List objects) {
        super(context, resource, objects);

        this.records = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        final Blacklist phoneNumber = records.get(position);

        ((TextView) convertView.findViewById(R.id.phone_number_tv)).setText(phoneNumber.getPhoneNumber());
        convertView.findViewById(R.id.phone_number_tv).setBackgroundColor(position % 2 == 0 ? Color.argb(255, 63, 81, 181) : Color.argb(255, 48, 63, 159));
        ((TextView) convertView.findViewById(R.id.phone_number_tv)).setTextColor(Color.rgb(255, 255, 255));

        return convertView;
    }

}
