package epsi.dettes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Etienne on 21/01/15.
 */
public class DetteAdapter extends BaseAdapter {
    List<Dette> data;
    Context context;

    public DetteAdapter(Context _context, List<Dette> _data) {
        context = _context;
        data    = _data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    /*static class ViewHolder {
        CheckBox titleView;

    }
*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*ViewHolder holder;
        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.todo_item, parent, false);
            holder = new ViewHolder();
            holder.titleView    = (CheckBox) convertView.findViewById(R.id.todoItemTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Todo todo = data.get(position);
        holder.titleView.setText(todo.title);
        return convertView;*/

        TextView title, name, montant = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dette_item,
                    parent, false);
            title = (TextView) convertView.findViewById(R.id.dette_title);
            name = (TextView) convertView.findViewById(R.id.dette_name);
            montant = (TextView) convertView.findViewById(R.id.dette_montant);

        } else {
            title = (TextView) convertView.findViewById(R.id.dette_title);
            name = (TextView) convertView.findViewById(R.id.dette_name);
            montant = (TextView) convertView.findViewById(R.id.dette_montant);
        }

        Dette current = data.get(position);
        title.setText("Titre : " + current.title);
        name.setText("Nom : " + current.name);
        montant.setText("Montant : " + current.montant);
        return convertView;
    }
}
