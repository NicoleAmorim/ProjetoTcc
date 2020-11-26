package dominio.entidade;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Servico implements Parcelable {

   private String IDUser;
   private String ImagemUrl;
   private String ImagemUrl2;
   private String ImagemUrl3;
   private String ImagemUrl4;
   private String Tipo;
   private String descricao;
   private String nome;
   private String imagem;

   public Servico() {
   }

   public Servico(String Nome, String Descricao, int id) {
      this.setNome(Nome);
      this.setDescricao(Descricao);
   }
   protected Servico(Parcel in) {
      IDUser = in.readString();
      ImagemUrl = in.readString();
      ImagemUrl2 = in.readString();
      ImagemUrl3 = in.readString();
      ImagemUrl4 = in.readString();
      Tipo = in.readString();
      descricao = in.readString();
      nome = in.readString();
      imagem = in.readString();
   }

   public static final Creator<Servico> CREATOR = new Creator<Servico>() {
      @Override
      public Servico createFromParcel(Parcel in) {
         return new Servico(in);
      }

      @Override
      public Servico[] newArray(int size) {
         return new Servico[size];
      }
   };

   public String getIDUser() {
      return IDUser;
   }

   public void setIDUser(String IDUser) {
      this.IDUser = IDUser;
   }

   public String getTipo() {
      return Tipo;
   }

   public void setTipo(String tipo) {
      Tipo = tipo;
   }

   public String getDescricao() {
      return descricao;
   }

   public void setDescricao(String descricao) {
      this.descricao = descricao;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getImagemUrl() {
      return ImagemUrl;
   }

   public void setImagemUrl(String imagemUrl) {
      ImagemUrl = imagemUrl;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(IDUser);
      dest.writeString(ImagemUrl);
      dest.writeString(ImagemUrl2);
      dest.writeString(ImagemUrl3);
      dest.writeString(ImagemUrl4);
      dest.writeString(Tipo);
      dest.writeString(descricao);
      dest.writeString(nome);
      dest.writeString(imagem);
   }

   public String getImagemUrl2() {
      return ImagemUrl2;
   }

   public void setImagemUrl2(String imagemUrl2) {
      ImagemUrl2 = imagemUrl2;
   }

   public String getImagemUrl3() {
      return ImagemUrl3;
   }

   public void setImagemUrl3(String imagemUrl3) {
      ImagemUrl3 = imagemUrl3;
   }

   public String getImagemUrl4() {
      return ImagemUrl4;
   }

   public void setImagemUrl4(String imagemUrl4) {
      ImagemUrl4 = imagemUrl4;
   }

   public String getImagem() {
      return imagem;
   }

   public void setImagem(String imagem) {
      this.imagem = imagem;
   }
}
