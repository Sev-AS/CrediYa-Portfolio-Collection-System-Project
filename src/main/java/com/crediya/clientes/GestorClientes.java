package com.crediya.clientes;

import java.util.ArrayList;
import java.util.List;


public class GestorClientes {
    //Atributo list
    private List<Clientes> clientes;

    //Contructor el constructor crea el new Arraylist ya que toca inicializarlo
    public GestorClientes (){
        this.clientes = new ArrayList<>();
    }
    // Metodo agregar cliente
    public void agregarCliente(Clientes cliente){
        clientes.add(cliente);
    }
    //Metodo ver la lista de los clientes
    public void listadetodoslosCliente(){
        if(clientes.isEmpty()){
        System.out.println("No hay clientes registrados");   
        return;
        }
        for (Clientes c: clientes){
            System.out.println(c);  
        }
    }
    //Metodo para obtener toda la lista de clientes
    public List<Clientes> getClientes(){
        return clientes;
    }
    //Obtener un clientes especifico
    public Clientes getEspecifiCliente(String nombre){
        for (Clientes c : clientes){
            if (c.getnombre().equals(nombre)){
                System.out.println(c.toString());
                return c;
            }
        }
        return null;
    }
    //Eliminar un cliente en especifico
    public Clientes deleteEspecifiCliente(String nombre){
        for (int i = 0; i < clientes.size(); i++){
            if (clientes.get(i).getnombre().equals(nombre)){
                Clientes eliminado = clientes.remove(i);
                System.out.println("Cliente eliminado");
                return eliminado;
            }
        }
        return null;
    }
    //Actualizar clientes en la lista
    //Se crea un nuevo cliente con la informacion a actualizar al cliente al que realmente quieres cambiarle
    //Luego todo se cambia mediante getters y setters, de esa manera para no tener que eliminar solo manejos de metodos
    //vale la pena aprenderlo
    public boolean actualizarCliente(String nombre, Clientes nuevosDatos) {
        for (Clientes c : clientes) {
            if (c.getnombre().equals(nombre)) {
                c.setid(nuevosDatos.getid());
                c.setnombre(nuevosDatos.getnombre());
                c.setdocumento(nuevosDatos.getdocumento());
                c.setcorreo(nuevosDatos.getcorreo());
                c.settelefono(nuevosDatos.gettelefono());
                System.out.println("Cliente actualizado");
                return true;
            }
        }
        System.out.println("Cliente no encontrado");
        return false;
    }


    public static void main(String[] args) {
        GestorClientes gestorCliente = new GestorClientes();

        Clientes brandon = new Clientes(1,"Brandon",1005321771,"delleore1@gmail.com","+57-3132248230");

        gestorCliente.agregarCliente(brandon);
        gestorCliente.listadetodoslosCliente(); 
        gestorCliente.getEspecifiCliente("Brandon");

        Clientes nuevos = new Clientes(1,"Brandon Pérez",1005321771,"nuevo@gmail.com","+57-3000000000");

        gestorCliente.actualizarCliente("Brandon", nuevos);

        gestorCliente.listadetodoslosCliente(); 

        gestorCliente.deleteEspecifiCliente("Brandon Pérez");

        gestorCliente.listadetodoslosCliente(); 
    }
}
