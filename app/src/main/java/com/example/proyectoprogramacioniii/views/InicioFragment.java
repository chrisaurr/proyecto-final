package com.example.proyectoprogramacioniii.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectoprogramacioniii.ActivityCategorias;
import com.example.proyectoprogramacioniii.ActivityPrincipal;
import com.example.proyectoprogramacioniii.R;
import com.example.proyectoprogramacioniii.databinding.FragmentInicioBinding;
import com.example.proyectoprogramacioniii.utils.LoadingDialog;

import org.htmlunit.BrowserVersion;
import org.htmlunit.Page;
import org.htmlunit.ScriptResult;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.html.HtmlAnchor;
import org.htmlunit.html.HtmlButton;
import org.htmlunit.html.HtmlDivision;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.util.WebConnectionWrapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import io.github.bonigarcia.wdm.WebDriverManager;



import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    LoadingDialog loadingDialog;

    int id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentInicioBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        id = getArguments().getInt("id");
        System.out.println(id);

        loadingDialog = new LoadingDialog(getActivity());

        binding.lacuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.iniciarLoadingDialog();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                ArrayList<String> milista = (ArrayList<String>) getCategoriasLaCuracao();
                                ArrayList<String> milista2 = (ArrayList<String>) getUrlConsultLaCuracao();
                                String tienda = "laCuracao";
                                Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                                intent.putExtra("miLista", milista);
                                intent.putExtra("miLista2", milista2);
                                intent.putExtra("tienda", tienda);
                                intent.putExtra("id", id);
                                startActivity(intent);
                                loadingDialog.ocultarLoginDialog();
                            }
                        },
                        3000
                );
            }
        });


        binding.tiendaMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.iniciarLoadingDialog();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                ArrayList<String> milista = (ArrayList<String>) getCategoriasMax();
                                ArrayList<String> milista2 = (ArrayList<String>) getUrlConsultMax();
                                String tienda = "Max";
                                Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                                intent.putExtra("miLista", milista);
                                intent.putExtra("miLista2", milista2);
                                intent.putExtra("tienda", tienda);
                                intent.putExtra("id", id);

                                startActivity(intent);
                                loadingDialog.ocultarLoginDialog();
                            }
                        },
                        3000
                );
            }
        });

        binding.agenciasWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.iniciarLoadingDialog();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                ArrayList<String> milista = (ArrayList<String>) getCategoriasAgenciasWay();
                                ArrayList<String> milista2 = (ArrayList<String>) getUrlConsultAgenciasWay();
                                String tienda = "agenciasWay";
                                Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                                intent.putExtra("miLista", milista);
                                intent.putExtra("miLista2", milista2);
                                intent.putExtra("tienda", tienda);
                                intent.putExtra("id", id);

                                startActivity(intent);
                                loadingDialog.ocultarLoginDialog();
                            }
                        },
                        3000
                );
            }
        });

        binding.tecnoFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.iniciarLoadingDialog();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                ArrayList<String> milista = (ArrayList<String>) getCategoriasTecnoFacil();
                                ArrayList<String> milista2 = (ArrayList<String>) getUrlConsultTecnoFacil();
                                String tienda = "TecnoFacil";
                                Intent intent = new Intent(getActivity(), ActivityCategorias.class);
                                intent.putExtra("miLista", milista);
                                intent.putExtra("miLista2", milista2);
                                intent.putExtra("tienda", tienda);
                                intent.putExtra("id", id);

                                startActivity(intent);
                                loadingDialog.ocultarLoginDialog();
                            }
                        },
                        3000
                );
            }
        });

        return view;
    }

    public void pasarActivity(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                startActivity(new Intent(getActivity(), ActivityPrincipal.class));

            }
        }, 3000L);
    }

    public List<String> getCategorias() {
        List<String> lista = new ArrayList<>();
        try{
            String url = "https://www.pacifiko.com/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("megamenu");
            String data1;

            for(Element d : data){
                lista = d.getElementsByTag("span").eachText();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> getCategoriasLaCuracao() {
        List<String> lista = new ArrayList<>();
        try{
            String url = "https://www.lacuracaonline.com/guatemala/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("li.level1");

            for(Element d: data){
                lista.add(d.selectFirst("span").text());
            }

            for(String l: lista){
                Log.v("Log", l);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> getCategoriasMax() {
        List<String> lista = new ArrayList<>();
        Log.v("Logelektra", "Se ejecuta el método");
        try{
            String url = "https://www.max.com.gt/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("li.parent");

            for(Element d: data){
                lista.add(d.selectFirst("span").text());
            }

            lista.remove(lista.size()-2);
            lista.remove(lista.size()-1);
            lista.remove(0);

            for(String l: lista){
                Log.v("Logelektra", "categorias:"+l);
            }

        } catch (IOException e){
            e.printStackTrace();
        }


        return lista;
    }

    public List<String> getCategoriasAgenciasWay() {
        List<String> lista = new ArrayList<>();

        try{
            String url = "https://agenciaswayonline.com/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("ul.list-categories > li");

            for(Element d: data){
                lista.add(d.selectFirst("a").text());
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> getCategoriasTecnoFacil() {
        List<String> lista = new ArrayList<>();

        Log.v("Logelektra", "Se ejecuta el método");
        try{
            String url = "https://www.tecnofacil.com.gt/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("div.media-body > a");

            for(Element d: data){
                lista.add(d.text());
            }

            for(String l: lista){
                Log.v("Logelektra", "categorias:"+l);
            }

        } catch (IOException e){
            e.printStackTrace();
        }


        return lista;
    }




    public List<String> getUrlConsult() {
        List<String> lista = new ArrayList<>();
        try{
            String url = "https://www.pacifiko.com/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("a.clearfix");
            Elements data1;

            for(Element d : data){
                lista.add( d.attr("abs:href"));

            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> getUrlConsultLaCuracao() {
        List<String> lista = new ArrayList<>();
        try{
            String url = "https://www.lacuracaonline.com/guatemala/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("li.level1");
            Elements data1;

            for(Element d : data){
                lista.add( d.selectFirst("a").attr("href"));

            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> getUrlConsultMax() {
        List<String> lista = new ArrayList<>();
        List<String> listaFinal = new ArrayList<>();
        List<String> listaUrlS = new ArrayList<>();
        try{
            String url = "https://www.max.com.gt/#";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("li.parent");
            Elements data1;

            for(Element d : data){
                lista.add( d.selectFirst("a").attr("href"));

            }

            lista.remove(lista.size()-2);
            lista.remove(lista.size()-1);
            lista.remove(0);


            int contador = 0;
            for(String iterator:lista){
                String urlFinal = iterator;
                Log.v("iterador", iterator);
                Log.v("iterador", String.valueOf(contador));
                Document docFinal = Jsoup.connect(urlFinal).get();
                int contadorIterativo = 0;

                Elements dataFinal = docFinal.select("a.see-offers");
                Elements dataFinal2 = docFinal.select("div.computing-offer-info");
                Element primero = dataFinal.first();

                    for(Element d : dataFinal){
                        if(contadorIterativo == 2 && contador < 1){
                        listaFinal.add( d.attr("href"));
                        }else if (contadorIterativo == 1) {
                            listaFinal.add( d.attr("href"));
                        }
                        contadorIterativo++;
                    }
                    contador++;
            }


           /* listaUrlS.add(lista.get(0));
            listaUrlS.add(lista.get(1));*/
            for(int i = 0; i < listaFinal.size(); i++){
                listaUrlS.add(listaFinal.get(i));
            }
            listaUrlS.add(lista.get(lista.size()-2));
            listaUrlS.add(lista.get(lista.size()-1));

            listaUrlS.remove(1);

            for(String l: listaUrlS){

                Log.v("LogelektralistFInal", "categorias:"+l);
            }


        } catch (IOException e){
            e.printStackTrace();
        }

        return listaUrlS;
    }

    public List<String> getUrlConsultAgenciasWay() {
        List<String> lista = new ArrayList<>();

        try{
            String url = "https://agenciaswayonline.com/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("ul.list-categories > li");

            for(Element d: data){
                lista.add(d.selectFirst("a").attr("href"));
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<String> getUrlConsultTecnoFacil() {
        List<String> lista = new ArrayList<>();

        Log.v("Logelektra", "Se ejecuta el método");
        try{
            String url = "https://www.tecnofacil.com.gt/";
            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("div.media-body > a");

            // lista = data.eachText();
            //Log.v("Logelektra", data.toString());

            for(Element d: data){
                lista.add(d.attr("href"));
            }

            for(String l: lista){
                Log.v("Logelektra", "categorias:"+l);
            }

        } catch (IOException e){
            e.printStackTrace();
        }


        return lista;
    }

}