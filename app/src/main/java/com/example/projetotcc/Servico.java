package com.example.projetotcc;

import android.graphics.Bitmap;

public class Servico {
   private int ID;
   private int IDUser;
   private String Tipo;
   private boolean continuar;
   private String descricao;
   private String nome;
   private String preco;
   private Bitmap imagem;

   public Servico() {
   }

   public Servico(String Nome, String Descricao, String Preco, int id) {
      this.setNome(Nome);
      this.setDescricao(Descricao);
      this.setID(id);
      this.setPreco(Preco);
   }

   public int getID() {
      return ID;
   }

   public void setID(int ID) {
      this.ID = ID;
   }

   public int getIDUser() {
      return IDUser;
   }

   public void setIDUser(int IDUser) {
      this.IDUser = IDUser;
   }

   public String getTipo() {
      return Tipo;
   }

   public void setTipo(String tipo) {
      Tipo = tipo;
   }

   public boolean isContinuar() {
      return continuar;
   }

   public void setContinuar(boolean continuar) {
      this.continuar = continuar;
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

   public Bitmap getImagem() {
      return imagem;
   }

   public void setImagem(Bitmap imagem) {
      this.imagem = imagem;
   }
}
