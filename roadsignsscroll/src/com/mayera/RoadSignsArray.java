package com.mayera;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RoadSignsArray extends ArrayAdapter {
  private final Context context;
  private final List<RoadSign> roadSigns;
  private final int NumberOfRoadSigns = 54;

  public RoadSignsArray(Context context, int i, List<RoadSign> roadSigns) {
    super(context, R.layout.roadsigns, roadSigns);

    this.context = context;
    this.roadSigns = roadSigns;

  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.roadsigns, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.correctanswer);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.roadsign);
    textView.setText(roadSigns.get(position).getCorrectAnswer());
    imageView.setImageResource((roadSigns.get(position).getIcon()));
    return rowView;
  }
}
