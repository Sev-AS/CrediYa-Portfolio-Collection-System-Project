package com.crediya.clientes;

public class Clientes {
  //Atributos
  private int id;
  private String nombre;
  private int documento;
  private String correo;
  private String telefono;


  //IMPORTANTE LOS CLIENTES NO TIENEN UN ID STATICO
  //Contructor
  public Clientes (int id, String nombre,int documento,String correo,String telefono) {
    this.id= id;
    this.nombre= nombre;
    this.documento= documento;
    this.correo= correo;
    this.telefono= telefono;
  }
  //Getters
  public int getid(){
    return this.id;
  }
  public String getnombre(){
    return this.nombre;
  }
  public int getdocumento(){
    return this.documento;
  }
  public String getcorreo(){
    return this.correo;
  }
  public String gettelefono(){
    return this.telefono;
  }
  //Setters
  public void setid (int id){
    this.id = id;
  }
  public void setnombre(String nombre){
  this.nombre = nombre;
  }
  public void setdocumento(int documento){
  this.documento = documento;
  }
  public void setcorreo(String correo){
  this.correo = correo;
  }
  public void settelefono(String telefono){
  this.telefono = telefono;
  }
  //Overrides
  @Override
  public String toString() {
      return "Clientes " +"id='" + id + ", nombre='" + nombre +", documento='" + documento +", correo='" + correo +", telefono='" + telefono ;
  }
  /* 
  public static void main(String[] args) {
    Clientes brandon = new Clientes("1","Brandon",1005321771,"delleore1@gmail.com","+57-3132248230");
    System.out.println(brandon.toString());
  }
  */
}
