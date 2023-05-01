//Empaqueto en PROG08_Principal
package PROG08_Principal;

import java.util.ArrayList; //Importamos la clase para modificar de array a ArrayList


/**
 *
 * @author cmora
 * La clase Banco contiene la estructura que almacena las cuentas
 */
class Banco {
    //Atributo
    private ArrayList<CuentaBancaria> cuentas; //ArrayList de tipo CuentaBancaria

    
    /**
     * Método constructor, inicializa contador a 0 y crea el array de 100.
    */
    
    public Banco() {
        this.cuentas = new ArrayList<>();
    }
  
    /**
     * Recibe por parámetro una CuentaBancaria y lo almacena.
     * Devuelve TRUE o FALSE si la operación se realiza con éxito
    */
    public boolean abrirCuenta (CuentaBancaria cuenta){
 
        CuentaBancaria existe = this.buscarCuenta(cuenta.getIban());
        if (existe != null){
            System.out.println("\nNO SE PUDO CREAR. La cuenta ya existe.\n");
            return false;
        }
        this.cuentas.add(cuenta);//Añade la cuenta al ArrayList.
        return true;       
        
        
    }
    
    /**
     * Recibe una cuenta por parámetro y la busca para ver si existe en el array.
     * @param cuenta
     * @return si no encuentra la cuenta devuelve null, sino devuelve la cuenta.
     */
    private CuentaBancaria buscarCuenta (String iban){
        
        for (CuentaBancaria c:this.cuentas) { //Cambio a bucle FOR-EACH.
            if (c.getIban().equals(iban)) //Compara si existe el IBAN
                return c; //Si existe devuelve la cuenta
        }
        return null;//Si no existe, devuelve null
    }
    
    /**
     * No recibe nada por parámetro. 
     * @return Devuelve un array de tipo String con información de las cuentas existentes.
    */
    public String[] listadoCuentas (){
        String[] infoCuentas = new String[this.cuentas.size()];//Declaro el array del tamaño del numero de cuentas existentes.
        for (int i = 0; i < infoCuentas.length; i++) {//Recorro todo el array de cuentas
            infoCuentas[i] = this.cuentas.get(i).toString();//Llamada para recibir la información de cada cuenta
        }
        return infoCuentas;//Devuelvo la información
    }
    
    /**
     * Recibe un IBAN por parámetro.
     * @return devuelve un String con información de la cuenta o NULL si no existe.
    */
    public String informacionCuenta (String iban){
        CuentaBancaria existe = this.buscarCuenta(iban);
        if (existe != null){
            return existe.devolverInfoString();
        }
        return null;
    }
    
    /**
     * Recibe un IBAN y una cantidad por parámetro e ingresa esa cantidad en la 
     * cuenta. 
     * @return Devuelve TRUE si la operación se realiza con éxito, sino FALSE. 
    */
    public boolean ingresoCuenta (String iban, double cantidadIngreso){
        CuentaBancaria existe = this.buscarCuenta(iban);
        if (existe != null){//La cuenta existe
            existe.setSaldo(existe.getSaldo() + cantidadIngreso);
            return true;
        }
        return false;
    } 
    
    /**
     * Recibe un IBAN y una cantidad por parámetro y retira esa cantidad de la 
     * cuenta si es posible.
     * @return Devuelve TRUE si la operación se realiza con éxito, sino FALSE. 
    */
    public boolean retiradaCuenta (String iban, double cantidadRetirada){
        CuentaBancaria existe = this.buscarCuenta(iban);
        
        boolean haySaldo = false; //Controla que la retirada se puede hacer de una cuenta con saldo
        if (existe != null){//La cuenta existe
        //Como las CuentasCorrientesEmpresa admiten descubiertos, vamos a plantear esa situación
        
            if (existe.getSaldo() - cantidadRetirada >= 0){//Compueba que hay saldo suficiciente
                haySaldo = true;
            }
            else
                if (existe instanceof CuentaCorrienteEmpresa){//Si la cuenta es instancia de CCEmpresa
                    CuentaCorrienteEmpresa aux = (CuentaCorrienteEmpresa)existe;//Casting a la clase correspondiente
                    if (Math.abs(existe.getSaldo() - cantidadRetirada) <= aux.getMaxDescubierto())
                        //Comprubea que la cantidad a retirar deja un descubierto menor al permitido
                        haySaldo = true;
                }
            }
                
        if (haySaldo){//Si hay saldo suficiente lo retira de la cuenta.
            existe.setSaldo(existe.getSaldo() - cantidadRetirada);
            }
        
        return haySaldo;//Devuelve el valor correcto
    } 
    
    /**
     * Recibe un IBAN por parámetro y devuelve el saldo de la cuenta si existe.
     * @return Devuelve -1 en caso de que no existe la cuenta. 
    */
    public double obtenerSaldo (String iban){
        CuentaBancaria existe = this.buscarCuenta(iban);
        if (existe != null){//La cuenta existe
            return existe.getSaldo();
        }
        return -1;//Si la cuenta no existe
    } 
    
    /**
     * Elimina una cuenta bancaria por IBAN. El saldo de la cuenta debe ser 0 para
     * que pueda ser eliminada.
     * @param iban de la cuenta bancaria
     * @return true si la elimina, false en caso contrario
     */
    public boolean eliminarCuenta (String iban){
        CuentaBancaria existe = this.buscarCuenta(iban);
        if (existe != null && existe.getSaldo() == 0){//La cuenta existe y el saldo es 0
            for (CuentaBancaria aux:this.cuentas) //Recorremos el ArrayList
                if (aux.getIban().equals(iban)){
                    this.cuentas.remove(aux);
                    return true;
                }
        }
        return false;
    }
      
    
    
}
