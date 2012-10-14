package com.mayera;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class KnowledgeArray extends ArrayAdapter {
  private final Context context;
  private final List<Question> roadSigns;
  

  public KnowledgeArray(Context context, int i, List<Question> roadSigns) {
    super(context, com.mayera.R.layout.roadsigns, roadSigns);

    this.context = context;
    this.roadSigns = roadSigns;

  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(com.mayera.R.layout.roadsigns, parent, false);
    TextView questionView = (TextView) rowView.findViewById(com.mayera.R.id.question);
    TextView textView = (TextView) rowView.findViewById(com.mayera.R.id.correctanswer);
    questionView.setText(roadSigns.get(position).getQuestion());
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    return rowView;
  }
}
