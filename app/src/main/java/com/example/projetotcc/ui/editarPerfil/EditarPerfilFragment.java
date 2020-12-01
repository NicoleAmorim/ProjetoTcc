package com.example.projetotcc.ui.editarPerfil;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import dominio.entidade.Usuario;

public class EditarPerfilFragment extends Fragment {

    private EditarPerfilViewModel mViewModel;
    EditText nome, email, telefone;
    ImageView image;
    Usuario usuario;
    Uri imagem;
    boolean i;
    private int PICK_IMAGE_REQUEST = 1;
    private Intent it;
    private TextView qtdd;
    private RatingBar ratingBar;

    public static EditarPerfilFragment newInstance() {
        return new EditarPerfilFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        usuario = PaginaUsuario.usuario;
        nome = view.findViewById(R.id.NomeEditarSenha);
        email = view.findViewById(R.id.EmailEditarPerfil);
        telefone = view.findViewById(R.id.TelefoneEditarPerfil);
        image = view.findViewById(R.id.ImagemEditarPerfil);
        TextView btnChat = view.findViewById(R.id.btnEditarPerfil);
        qtdd = view.findViewById(R.id.numA);
        ratingBar = view.findViewById(R.id.estrelasE);

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
        R();
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
        try {
            String filename = FirebaseAuth.getInstance().getUid();
            final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/users/" + filename);
            ref.putFile(imagem)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
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
                                    usuario.setImageUrl(String.valueOf(uri));

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
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Teste", e.getMessage(), e);
                                }
                            });
                        }
                    });
        } catch (Exception exception) {
            exception.printStackTrace();
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
    }
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if(intent != null) {
            try {
                imagem = intent.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver) PaginaUsuario.context.getContentResolver(), (Uri) imagem);
                image.setImageBitmap(bitmap);
                i = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void R()
    {
        FirebaseFirestore.getInstance().collection("/avaliacao")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("R")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                        if (documentChanges != null) {
                            int i = 0;
                            int d = 0;
                            for (DocumentChange doc: documentChanges) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    PaginaUsuario.Rating rating = new PaginaUsuario.Rating();
                                    rating =  doc.getDocument().toObject(PaginaUsuario.Rating.class);
                                    d+= rating.getRating();
                                    i++;
                                }

                            }
                            try {
                                ratingBar.setNumStars(d / i);
                                qtdd.setText(String.valueOf(i));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                                ratingBar.setNumStars(0);
                                qtdd.setText("0");
                            }
                        }
                    }
                });
    }
}