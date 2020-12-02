package com.example.projetotcc;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

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

    public RStar(Activity myActivity){
        activity = myActivity;
    }

    public void StartRat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_rating, null));

        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void DismissDialog(){
        dialog.dismiss();
    }
    public  void R1(View view) {
        PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
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
    public  void R2(View view) {
        PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
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
    public  void R3(View view) {
        PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
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
    public  void R4(View view) {
        PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
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
    public  void R5(View view) {
        PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
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
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener()
                                {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Log.e("Teste", o.toString());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Teste", e.getMessage(), e);
                                    }
                                });
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
}
