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

        TextView txt = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dette_item,
                    parent, false);
            txt = (TextView) convertView.findViewById(R.id.dette_title);



        } else {
            txt = (TextView) convertView.findViewById(R.id.dette_title);
        }

        Dette current = data.get(position);
        txt.setText(current.title + " name : " + current.name);
        txt.setTag(current);
        return convertView;
    }
}
