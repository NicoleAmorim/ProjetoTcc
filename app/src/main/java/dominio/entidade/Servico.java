package dominio.entidade;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Servico implements Parcelable {

   private String IDUser;
   private String ImagemUrl;
   private String Tipo;
   private String descricao;
   private String nome;
   private String preco;

   public Servico() {
   }

   public Servico(String Nome, String Descricao, String Preco, int id) {
      this.setNome(Nome);
      this.setDescricao(Descricao);
      this.setPreco(Preco);
   }
   protected Servico(Parcel in) {
      IDUser = in.readString();
      ImagemUrl = in.readString();
      Tipo = in.readString();
      descricao = in.readString();
      nome = in.readString();
      preco = in.readString();
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

   public String getPreco() {
      return preco;
   }

   public void setPreco(String preco) {
      this.preco = preco;
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
      dest.writeString(Tipo);
      dest.writeString(descricao);
      dest.writeString(nome);
      dest.writeString(preco);
   }
}
