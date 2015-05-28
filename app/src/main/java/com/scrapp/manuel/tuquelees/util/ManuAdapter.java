package com.scrapp.manuel.tuquelees.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.scrapp.manuel.tuquelees.BookActivity;
import com.scrapp.manuel.tuquelees.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Manuel on 07/05/2015.
 */
public class ManuAdapter extends RecyclerView.Adapter<ManuAdapter.MyViewHolder> {

    private List<Book> data = Collections.emptyList();
    private AdapterView.OnItemClickListener onItemClickListener;

    public ManuAdapter(List<Book> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item_custom, viewGroup, false);
       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = data.get(i);


                Intent intent = new Intent(v.getContext(), BookActivity.class);



                intent.putExtra("Title", book.getTitle());

                intent.putExtra("Autor", book.getAutor());
                intent.putExtra("Image", book.getImage());
                intent.putExtra("Link", book.getLink());
                intent.putExtra("Rating", book.getRating());


                v.getContext().startActivity(intent);
            }
        });*/
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        Book book = data.get(i);
        //myViewHolder.textViewTitle.setText(book.getTitle());
        //myViewHolder.textViewRating.setText(book.getRating());
        //myViewHolder.textViewAutor.setText(book.getImage());
        Picasso.with(myViewHolder.imageView.getContext()).load(book.getImage()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void openBookFragment(Context context, int position) {
        Book book = data.get(position);


        Intent intent = new Intent(context, BookActivity.class);



        intent.putExtra("Title", book.getTitle());

        intent.putExtra("Autor", book.getAutor());
        intent.putExtra("Image", book.getImage());
        intent.putExtra("Link", book.getLink());
        intent.putExtra("Rating", book.getRating());


        context.startActivity(intent);
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAutor;
        private ImageView imageView;
        private TextView textViewRating;

        //Aca se aniade el resto de campos del item custom

        public MyViewHolder(final View itemView) {
            super(itemView);
            //textViewTitle = (TextView) itemView.findViewById(R.id.textViewRecyclerTitle);
            //textViewAutor= (TextView) itemView.findViewById(R.id.textViewRecyclerAutor);
            //textViewRating = (TextView) itemView.findViewById(R.id.textViewRecyclerRating);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewRecycler);


        }
    }

    private void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Book item, int position) {
        data.add(position, item);

    }

    public void add(Book item) {
        data.add(item);
    }

    private void move(int position) {
        data.add(position, data.get(position));
        notifyItemInserted(position);

    }

}
