package com.scrapp.manuel.tuquelees;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrapp.manuel.tuquelees.util.Book;
import com.scrapp.manuel.tuquelees.util.ManuAdapter;
import com.scrapp.manuel.tuquelees.util.RecyclerItemClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private ManuAdapter adapter;
    private ProgressDialog pDialog;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        inicializarComponentes();
        return view;
    }

    private void inicializarComponentes() {
        pDialog = new ProgressDialog(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        adapter = new ManuAdapter(new ArrayList<Book>());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        adapter.openBookFragment(view.getContext(), position);
                    }
                })
        );
        new CargarDatos().execute("");
    }

    private void terminaCarga() {
        adapter.notifyDataSetChanged();
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
                document = Jsoup.connect("http://www.tuquelees.com/lo-que-mas-gusta").userAgent("Mozilla/5.0").get();

                Elements elementsTitles = document.getElementsByTag("h2");
                Elements elementsAutor = document.getElementsByClass("cColor");
                Elements elementsImage = document.getElementsByClass("cPortada");
                Elements elementsLink = document.select("a[href*=/libro/]");

                int i = 0;
                for (org.jsoup.nodes.Element element : elementsTitles) {
                    Book book = new Book();
                    book.setTitle(element.text());
                    book.setImage(elementsImage.eq(i).attr("src"));
                    book.setAutor(elementsAutor.eq(i).text());
                    book.setLink(elementsLink.eq(i).attr("href"));
                    Log.d("link---->", book.getLink());
                    //URL url = new URL(elementsImage.eq(i).attr("src"));
                    //Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //book.setImageLoad(image);

                    adapter.add(book);
                    i++;
                }


                /*Log.d("tamanio", elementsCreator.size() + " ");
                for (Element element : elementsNameAndLink) {
                    String name = element.text();
                    if (name.length() != 0 && element.tagName() == "span") {
                         item = new Item();
                        item.setName(name);
                        String link = element.child(0).attr("abs:href");
                        item.setLink(link);
                        String classType = elementsClassType.eq(i + 1).text();
                        item.setClassType(classType);
                        String rating = elementsRating.eq(i).text();
                        item.setRating(rating);
                        String cost = elementsCost.eq(i + 1).text();
                        item.setCost(cost);
                        String creator = elementsCreator.eq(i).text();
                        item.setCreator(creator);

                        adapter.add(item);
                        i++;
                    }
                }
                Log.d("nuevo", "Han habido " + i + " resultados buenos");*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            /*if (entradasAux.isEmpty()) {
                Toast.makeText(MainActivity.this, "Hay un fallo con la conexion a Internet", Toast.LENGTH_LONG).show();
            } else {
                entradas = entradasAux;
                if (!adapter.isEmpty()) {
                    adapter.clear();
                }  //Se podria hacer una comprobacion para no pintar dos veces si es lo mismo
                actualizarListView();
            }
            */

            terminaCarga();

            // Dejar de mostrar proceso de carga

            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
}
