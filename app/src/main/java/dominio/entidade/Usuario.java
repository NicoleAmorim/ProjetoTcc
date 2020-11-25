package dominio.entidade;

import android.graphics.Bitmap;

public class Usuario {
   private int cod;
   private String id;
   private String cpf;
   private String email;
   private String imageUrl;
   private int idade;
   private String nome;
   private String senha;
   private String sexo;
   private String tel;
   private String username;
   private String Token;
   private boolean online;

   public Usuario() {
   }

   public Usuario(String var1, String var2, String var3, String var4, String var5, int var6, int var7, String var8, Bitmap var9) {
      this.nome = var1;
      this.email = var2;
      this.username = var3;
      this.cpf = var4;
      this.senha = var5;

      this.idade = var7;
      this.sexo = var8;
   }

   public String getCpf() {
      return this.cpf;
   }

   public String getEmail() {
      return this.email;
   }

   public int getIdade() {
      return this.idade;
   }

   public String getNome() {
      return this.nome;
   }

   public String getSenha() {
      return this.senha;
   }

   public String getSexo() {
      return this.sexo;
   }

   public String getTel() {
      return this.tel;
   }

   public String getUsername() {
      return this.username;
   }

   public void setCpf(String var1) {
      this.cpf = var1;
   }

   public void setEmail(String var1) {
      this.email = var1;
   }

   public void setIdade(int var1) {
      this.idade = var1;
   }

   public void setNome(String var1) {
      this.nome = var1;
   }

   public void setSenha(String var1) {
      this.senha = var1;
   }

   public void setSexo(String var1) {
      this.sexo = var1;
   }

   public void setTel(String var1) {
      this.tel = var1;
   }

   public void setUsername(String var1) {
      this.username = var1;
   }

   public int getCod() {
      return cod;
   }

   public void setCod(int cod) {
      this.cod = cod;
   }


   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public String getToken() {
      return Token;
   }

   public void setToken(String token) {
      Token = token;
   }

   public boolean isOnline() {
      return online;
   }

   public void setOnline(boolean online) {
      this.online = online;
   }
}
