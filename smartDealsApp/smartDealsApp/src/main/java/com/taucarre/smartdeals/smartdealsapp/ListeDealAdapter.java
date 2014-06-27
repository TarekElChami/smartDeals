package com.taucarre.smartdeals.smartdealsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taucarre.smartdeals.presentation.modele.ListeDeal;

/**
 * Created by tarekelchami on 23/04/14.
 */
public class ListeDealAdapter extends BaseAdapter {

    ListeDeal listeDeal;
    Context context;

    public ListeDealAdapter(Context context, ListeDeal liste) {
        this.context = context;
        listeDeal = liste;
    }

    public ListeDeal getListeDeal() {
        return listeDeal;
    }

    @Override
    public int getCount() {
        return listeDeal.size() ;
    }

    @Override
    public Object getItem(int i) {
        return listeDeal.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.liste_item_definition,viewGroup, false);

        TextView titreDeal = (TextView) itemView.findViewById(R.id.nomDeal);
        TextView lieuDeal = (TextView) itemView.findViewById(R.id.emplacementDeal);

        TextView descriptionDeal = (TextView) itemView.findViewById(R.id.textDescDeal);

        TextView counterDeal = (TextView) itemView.findViewById(R.id.counter);

        //TODO : recuperer le rest des champs vues deal

        titreDeal.setText(listeDeal.get(i).getNomDeal());
        lieuDeal.setText(listeDeal.get(i).getNomMarchand());
        descriptionDeal.setText(listeDeal.get(i).getDesciprtionDeal());
        //counterDeal.setText(String.valueOf(listeDeal.get(i).));

        return itemView;
    }
}
