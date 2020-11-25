package com.example.projetotcc.ui.editarPerfil;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.MainActivity;
import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import dominio.entidade.Usuario;

public class EditarPerfilFragment extends Fragment {

    private EditarPerfilViewModel mViewModel;
    EditText nome, email, telefone;
    ImageView image;
    Usuario usuario;
    Uri imagem;
    private int PICK_IMAGE_REQUEST = 1;
    private Intent it;

    public static EditarPerfilFragment newInstance() {
        return new EditarPerfilFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        usuario = PaginaUsuario.usuario;
        nome = view.findViewById(R.id.NomeEditarPerfilFragment);
        email = view.findViewById(R.id.EmailEditarPerfil);
        telefone = view.findViewById(R.id.TelefoneEditarPerfil);
        image = view.findViewById(R.id.ImagemEditarPerfil);
        TextView btnChat = view.findViewById(R.id.btnEditarPerfil);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_REQUEST);
            }
        });
        nome.setText(usuario.getNome());
        email.setText(usuario.getEmail());
        telefone.setText(String.valueOf(usuario.getTel()));
        Picasso.get().load(usuario.getImageUrl()).into(image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditarPerfilViewModel.class);

        // TODO: Use the ViewModel
    }
    public void Editar()
    {
        AuthCredential authCredential = EmailAuthProvider.getCredential(usuario.getEmail(), usuario.getSenha());
        Log.i("Teste", String.valueOf(authCredential));
        usuario.setEmail(email.getText().toString());
                                                   FirebaseAuth.getInstance().getCurrentUser().reauthenticate(authCredential);
                                                   FirebaseAuth.getInstance().getCurrentUser().updateEmail(usuario.getEmail())
                                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void aVoid) {
                                                                   Log.i("Teste", "foi");
                                                               }
                                                           })
                                                           .addOnFailureListener(new OnFailureListener() {
                                                               @Override
                                                               public void onFailure(@NonNull Exception e) {
                                                                   Log.i("Teste", e.getMessage());
                                                               }
                                                           });
        usuario.setNome(nome.getText().toString());
        usuario.setTel(telefone.getText().toString());

                        FirebaseFirestore.getInstance().collection("users")
                .document(usuario.getId())
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        it = new Intent(PaginaUsuario.context, PaginaUsuario.class);

                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        PaginaUsuario.context.startActivity(it);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if(intent != null) {
            try {
                imagem = intent.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver) PaginaUsuario.context.getContentResolver(), (Uri) imagem);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}