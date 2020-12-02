package com.example.projetotcc;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.projetotcc.ui.pedidos.PedidosFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.projetotcc.PaginaUsuario.getContext;
import static com.example.projetotcc.PaginaUsuario.rStar;
import static com.example.projetotcc.PaginaUsuario.usuario;

public class RStar {
    private Activity activity;
    private AlertDialog dialog;
    private ImageView r1, r2, r3, r4, r5;

    public RStar(Activity myActivity){
        activity = myActivity;
    }

    public void StartRat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rating, null);
        builder.setView(view);
        r1 = view.findViewById(R.id.R1);
        r2 = view.findViewById(R.id.R2);
        r3 = view.findViewById(R.id.R3);
        r4 = view.findViewById(R.id.R4);
        r5 = view.findViewById(R.id.R5);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R1();
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R2();
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R3();
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R4();
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R5();
            }
        });

        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void DismissDialog(){
        dialog.dismiss();
    }
    public  void R1() {
        Rating rating = new Rating();
        rating.setRating(1);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection(PedidosFragment.pedido.getUuid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection(FirebaseAuth.getInstance().getUid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection("pedidos")
                                .document(FirebaseAuth.getInstance().getUid())
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("pedidos")
                                .document(PedidosFragment.pedido.getUuid())
                                .delete();
                        PedidosFragment.application.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PedidosFragment()).commit();
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R2() {
        Rating rating = new Rating();
        rating.setRating(2);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection(PedidosFragment.pedido.getUuid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection(FirebaseAuth.getInstance().getUid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection("pedidos")
                                .document(FirebaseAuth.getInstance().getUid())
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("pedidos")
                                .document(PedidosFragment.pedido.getUuid())
                                .delete();
                        PedidosFragment.application.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PedidosFragment()).commit();
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R3() {
        Rating rating = new Rating();
        rating.setRating(3);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection(PedidosFragment.pedido.getUuid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection(FirebaseAuth.getInstance().getUid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection("pedidos")
                                .document(FirebaseAuth.getInstance().getUid())
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("pedidos")
                                .document(PedidosFragment.pedido.getUuid())
                                .delete();
                        PedidosFragment.application.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PedidosFragment()).commit();
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R4() {
        Rating rating = new Rating();
        rating.setRating(4);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection(PedidosFragment.pedido.getUuid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection(FirebaseAuth.getInstance().getUid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection("pedidos")
                                .document(FirebaseAuth.getInstance().getUid())
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("pedidos")
                                .document(PedidosFragment.pedido.getUuid())
                                .delete();
                        PedidosFragment.application.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PedidosFragment()).commit();
                        rStar.DismissDialog();
                    }
                });
    }
    public  void R5() {
        Rating rating = new Rating();
        rating.setRating(5);
        FirebaseFirestore.getInstance().collection("avaliacao")
                .document(PedidosFragment.pedido.getUuid())
                .collection("R")
                .document(usuario.getId())
                .set(rating)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection(PedidosFragment.pedido.getUuid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("conversas")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection(FirebaseAuth.getInstance().getUid())
                                .document("mensagem")
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(PedidosFragment.pedido.getUuid())
                                .collection("pedidos")
                                .document(FirebaseAuth.getInstance().getUid())
                                .delete();
                        FirebaseFirestore.getInstance().collection("ultima-mensagem")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("pedidos")
                                .document(PedidosFragment.pedido.getUuid())
                                .delete();
                        PedidosFragment.application.getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new PedidosFragment()).commit();
                        rStar.DismissDialog();
                    }
                });
    }
    public static class Rating
    {
        private int rating;

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }
}
