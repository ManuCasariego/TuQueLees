package com.scrapp.manuel.tuquelees;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * A placeholder fragment containing a simple view.
 */
public class BookActivityFragment extends Fragment{

    private View view;
    private ProgressDialog pDialog;
    private String title, autor, link, image, rating, description;
    private TextView textViewDescription;
    private TextView textViewTitle;
    private ImageView imageView;
    private LinearLayout linearLayoutVibrant;

    public BookActivityFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);
        inicializarComponentes();
        return view;
    }

    private void inicializarComponentes() {
        pDialog = new ProgressDialog(view.getContext());
        Intent intent = getActivity().getIntent();

        title = intent.getStringExtra("Title");
        autor = intent.getStringExtra("Autor");
        link = intent.getStringExtra("Link");
        image = intent.getStringExtra("Image");

        textViewDescription = (TextView) view.findViewById(R.id.textViewDetailedDescription);
        textViewTitle = (TextView) view.findViewById(R.id.textViewDetailedTitle);
        imageView = (ImageView) view.findViewById(R.id.detailedImage);
        linearLayoutVibrant = (LinearLayout) view.findViewById(R.id.linearLayoutVibrantColor);


        new CargarDatos().execute("");
    }

    protected void terminaCarga() {
        //textViewTitle.setText(title);
        textViewTitle.setText(title);
        textViewDescription.setText(description);
        Picasso.with(view.getContext()).load(image).into(imageView);

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                applyPalette(palette, image);
            }
        });

    }

    private void applyPalette(Palette palette, String image) {
        linearLayoutVibrant.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.Grey)));


    }

    private class CargarDatos extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar progreso de carga
            pDialog.setMessage("Cargando...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Boolean doInBackground(String... params) {
            Document document = null;
            try {
                document = Jsoup.connect(link).userAgent("Mozilla/5.0").get();
                Elements elementsSinopsis = document.getElementsByClass("cSinopsis");
                Elements elementsRating = document.select("span[style*=font-size]");
                description = elementsSinopsis.first().text();
                rating = elementsRating.first().text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            terminaCarga();
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
}
