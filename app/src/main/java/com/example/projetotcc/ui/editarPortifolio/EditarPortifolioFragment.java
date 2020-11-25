package com.example.projetotcc.ui.editarPortifolio;

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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetotcc.PaginaUsuario;
import com.example.projetotcc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;

import dominio.entidade.Servico;

public class EditarPortifolioFragment extends Fragment {

    private EditarPortifoliolViewModel mViewModel;
    private int PICK_IMAGE_REQUEST = 1;
    private Servico servico;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private Uri filePath;
    private boolean um = false, dois = false, tres = false, quatro = false;

    public static EditarPortifolioFragment newInstance() {
        return new EditarPortifolioFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_portifolio, container, false);
        servico = PaginaUsuario.servicop;
        image1 = view.findViewById(R.id.imagePortifolio1);
        image2 = view.findViewById(R.id.imagePortifolio2);
        image3 = view.findViewById(R.id.imagePortifolio3);
        image4 = view.findViewById(R.id.imagePortifolio4);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                um = true;
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_REQUEST);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dois = true;
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_REQUEST);

            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tres = true;
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_REQUEST);

            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quatro = true;
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE_REQUEST);

            }
        });

        Picasso.get().load(servico.getImagemUrl()).into(image1);
        try {

                Picasso.get().load(servico.getImagemUrl2()).into(image2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Picasso.get().load(servico.getImagemUrl3()).into(image3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
                Picasso.get().load(servico.getImagemUrl4()).into(image4);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditarPortifoliolViewModel.class);
        // TODO: Use the ViewModel
    }

    public void MudarImagem1(Uri imagem)
    {
        String filename = servico.getNome();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/servico/" + filename);
        ref.putFile(imagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                servico.setImagemUrl(String.valueOf(uri));
                                FirebaseFirestore.getInstance().collection("servico")
                                        .document(servico.getIDUser())
                                        .set(servico);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e("Teste", e.getMessage(), e);
                            }
                        });
                    }
                });
    }
    public void MudarImagem2(Uri imagem)
    {
        String filename = servico.getNome();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/servico/" + filename+"2");
        ref.putFile(imagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                servico.setImagemUrl2(String.valueOf(uri));
                                FirebaseFirestore.getInstance().collection("servico")
                                        .document(servico.getIDUser())
                                        .set(servico);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e("Teste", e.getMessage(), e);
                            }
                        });
                    }
                });
    }
    public void MudarImagem3(Uri imagem)
    {
        String filename = servico.getNome();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/servico/" + filename+"3");
        ref.putFile(imagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("3foto", "Sucesso");
                                servico.setImagemUrl3(String.valueOf(uri));
                                FirebaseFirestore.getInstance().collection("servico")
                                        .document(servico.getIDUser())
                                        .set(servico);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e("Teste", e.getMessage(), e);
                            }
                        });
                    }
                });
    }
    public void MudarImagem4(Uri imagem)
    {
        String filename = servico.getNome();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/servico/" + filename+"4");
        ref.putFile(imagem)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                servico.setImagemUrl4(String.valueOf(uri));
                                FirebaseFirestore.getInstance().collection("servico")
                                        .document(servico.getIDUser())
                                        .set(servico);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.e("Teste", e.getMessage(), e);
                            }
                        });
                    }
                });
    }
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if(intent != null) {
            try {
                filePath = intent.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap((ContentResolver) PaginaUsuario.context.getContentResolver(), (Uri) filePath);
                if(um) {
                    image1.setImageBitmap(bitmap);
                    MudarImagem1(filePath);
                    um = false;
                    dois = false;
                    tres = false;
                    quatro = false;
                }
                if(dois) {
                    image2.setImageBitmap(bitmap);
                    MudarImagem2(filePath);
                    um = false;
                    dois = false;
                    tres = false;
                    quatro = false;
                }
                if(tres) {
                    image3.setImageBitmap(bitmap);
                    MudarImagem3(filePath);
                    um = false;
                    dois = false;
                    tres = false;
                    quatro = false;
                }
                if(quatro) {
                    image4.setImageBitmap(bitmap);
                    MudarImagem4(filePath);
                    um = false;
                    dois = false;
                    tres = false;
                    quatro = false;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}