package splitbanking.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class CardListAdapter extends ArrayAdapter<Card> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private ViewHolder holder;
    private List<Card> cards;

    static class ViewHolder{
        TextView cardName;
        TextView sharedStatus;
    }

    public CardListAdapter(Context context, int resource, List<Card> cards){
        super(context, resource, cards);
        mContext = context;
        mResource = resource;
        this.cards = cards;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        Card cardItem = getItem(position);
        String cardName = cardItem.getCardName();
        String sharedStatus;
        if(cardItem.getSharedUsers().size() <= 1){
            sharedStatus = "Not Shared";
        }else{
            sharedStatus = "Shared";
        }

        final View result;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder= new ViewHolder();

            holder.cardName = convertView.findViewById(R.id.tvCardName);
            holder.sharedStatus = convertView.findViewById(R.id.tvSharedStatus);

            convertView.setTag(holder);

            result = convertView;
        }else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;
        holder.cardName.setText(cardName);
        holder.sharedStatus.setText(sharedStatus);
        return convertView;
    }
}
