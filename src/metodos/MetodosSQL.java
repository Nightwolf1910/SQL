package metodos;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
//import java.sql.Date;

public class MetodosSQL {
    public static ConexionBD conexion=new ConexionBD();
    public static PreparedStatement sentencia_preparada;
    public static ResultSet res;
    public static String sql;  
    public static int resultadoNumero=0;
    DefaultTableModel modelo=new DefaultTableModel();
    
    public void buscarEmpleados(DefaultTableModel modelo){
        if(modelo.getRowCount()>0){
            modelo.setNumRows(0);
        }
        String[] fila=new String[10];
        Connection conexion=null;
        String sentencia_mostrar=("SELECT E.DNI,E.NOMBRE || ' ' ||E.APELLIDO AS NOMBRE,TRUNC(MONTHS_BETWEEN(SYSDATE, E.FECHA_NAC)/12) AS EDAD,\n" +
            "E.SEXO,E.CELULAR,E.CORREO,E.SUELDO,A.NOMBRE AS AREA,T.ROL,S.ASEGURADORA\n" +
            "FROM EMPLEADO E, AREA A, TRABAJO T, SEGURO S\n" +
            "WHERE A.AREA_ID=E.AREA_ID AND T.TRABAJO_ID=E.TRABAJO_ID AND S.SEGURO_ID=E.SEGURO_ID");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            PreparedStatement sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            ResultSet resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                fila[0]=resultado.getString(1);
                fila[1]=resultado.getString(2);
                fila[2]=resultado.getString(3);
                fila[3]=resultado.getString(4);
                fila[4]=resultado.getString(5);
                fila[5]=resultado.getString(6);
                fila[6]=resultado.getString(7);
                fila[7]=resultado.getString(8);
                fila[8]=resultado.getString(9);
                fila[9]=resultado.getString(10);
                modelo.addRow(fila);
                System.out.println(fila[1]);
                System.out.println(resultado.getString(1));
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public int guardarEmpleado(int dni,String nombre,String apellido,String sexo,
            int celular,String direccion,String categoria,int sueldo,
            String nacimiento,JComboBox puesto,JComboBox area,JComboBox trabajo,
            JComboBox contrato,JComboBox seguro,JComboBox prueba){
        int resultado=0;
        String pues="";
        String ar="";
        String trab="";
        String cont="";
        String segu="";
        int pru=0;
        if(puesto.getSelectedItem().equals("Director Ejecutivo")){
                pues="CEO";
        }else if(puesto.getSelectedItem().equals("Director de Operaciones")){
                pues="COO";
        }else if(puesto.getSelectedItem().equals("Director Comercial")){
                pues="CSO";
        }else if(puesto.getSelectedItem().equals("Director Marketing")){
                pues="CMO";
        }else if(puesto.getSelectedItem().equals("Director de Recursos Humanos")){
                pues="CHRO";
        }else if(puesto.getSelectedItem().equals("Customer Success")){
                pues="CS";
        }else if(puesto.getSelectedItem().equals("Director Financiero")){
                pues="CFO";
        }
        if(area.getSelectedItem().equals("Tecnologias de la informacion")){
                ar="TI";
        }else if(area.getSelectedItem().equals("Marketing")){
                ar="MK";
        }else if(area.getSelectedItem().equals("Finanzas")){
                ar="FN";
        }
        if(trabajo.getSelectedItem().equals("Limpidador")){
                trab="LIM";
        }else if(trabajo.getSelectedItem().equals("Tecnico")){
                trab="TEC";
        }else if(trabajo.getSelectedItem().equals("Programador")){
                trab="PRO";
        }else if(trabajo.getSelectedItem().equals("Economista")){
                trab="ECN";
        }else if(trabajo.getSelectedItem().equals("Cajero")){
                trab="CAJ";
        }else if(trabajo.getSelectedItem().equals("Reponedor")){
                trab="REP";
        }
        if(contrato.getSelectedItem().equals("Temporal")){
                cont="TMP";
        }
        if(seguro.getSelectedItem().equals("INTERSEGURO")){
                segu="INTSG";
        }else if(seguro.getSelectedItem().equals("LA POSITIVA")){
                segu="LPOST";
        }else if(seguro.getSelectedItem().equals("RIMAC SEGUROS")){
                segu="RMCSG";
        }
        if(prueba.getSelectedItem().equals("VIRTUAL")){
                pru=1;
        }
        Date fecha=new Date();
        SimpleDateFormat formato=new SimpleDateFormat("dd/MM/YYYY");
        
        String sentencia_guardar=("INSERT INTO empleado(dni,nro_empleado,nombre,apellido,correo,"
                + "sexo,celular,direccion,categorial_laboral,sueldo,fecha_nac,fecha_cont,dni_jefe,"
                + "puesto_id,area_id,trabajo_id,contrato_id,seguro_id,prueba_id) VALUES(?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.setInt(1, dni);
            sentencia_preparada.setInt(2, modelo.getRowCount()+1);
            sentencia_preparada.setString(3, nombre);
            sentencia_preparada.setString(4, apellido);
            sentencia_preparada.setString(5, nombre+"."+apellido+"@MALLPLAZA.COM");
            sentencia_preparada.setString(6, sexo);
            sentencia_preparada.setInt(7, celular);
            sentencia_preparada.setString(8, direccion);
            sentencia_preparada.setString(9, categoria);
            sentencia_preparada.setInt(10, sueldo);
            sentencia_preparada.setString(11, nacimiento);
            sentencia_preparada.setString(12, formato.format(fecha));
            sentencia_preparada.setInt(13, 15616535);
            sentencia_preparada.setString(14, pues);
            sentencia_preparada.setString(15, ar);
            sentencia_preparada.setString(16, trab);
            sentencia_preparada.setString(17, cont);
            sentencia_preparada.setString(18, segu);
            sentencia_preparada.setInt(19, pru);
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultado;
    }
    public void EmpleadosFiltrados(DefaultTableModel modelo,String nombre){
        if(modelo.getRowCount()>0){
            modelo.setNumRows(0);
        }
        String[] fila=new String[10];
        Connection conexion=null;
        String sentencia_mostrar=("SELECT E.DNI,E.NOMBRE || ' ' ||E.APELLIDO AS NOMBRE,TRUNC(MONTHS_BETWEEN(SYSDATE, E.FECHA_NAC)/12) AS EDAD,\n" +
            "E.SEXO,E.CELULAR,E.CORREO,E.SUELDO,A.NOMBRE AS AREA,T.ROL,S.ASEGURADORA\n" +
            "FROM EMPLEADO E, AREA A, TRABAJO T, SEGURO S\n" +
            "WHERE A.AREA_ID=E.AREA_ID AND T.TRABAJO_ID=E.TRABAJO_ID AND S.SEGURO_ID=E.SEGURO_ID"
                + " AND (E.NOMBRE LIKE '%"+nombre+"%' OR E.APELLIDO LIKE '%"+nombre+"%')");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            PreparedStatement sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            ResultSet resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                fila[0]=resultado.getString(1);
                fila[1]=resultado.getString(2);
                fila[2]=resultado.getString(3);
                fila[3]=resultado.getString(4);
                fila[4]=resultado.getString(5);
                fila[5]=resultado.getString(6);
                fila[6]=resultado.getString(7);
                fila[7]=resultado.getString(8);
                fila[8]=resultado.getString(9);
                fila[9]=resultado.getString(10);
                modelo.addRow(fila);
                System.out.println(fila[1]);
                System.out.println(resultado.getString(1));
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void mostrarEmpleado(String nombreCom,JTextField dni,JTextField nombre,JTextField apellido,
            JRadioButton masculino,JRadioButton femenino,JTextField celular,JTextField direccion,JTextField categoria,
            JTextField sueldo,JDateChooser nacimiento,JComboBox puesto,JComboBox area,JComboBox trabajo,
            JComboBox contrato,JComboBox seguro,JComboBox prueba){
        Connection conexion=null;
        String sex="";
        String pues="";
        String ar="";
        String trab="";
        String cont="";
        String segu="";
        int pru=0;
        String sentencia_mostrar=("SELECT DNI,NOMBRE,APELLIDO,CELULAR,SEXO,DIRECCION,"
                + "CATEGORIAL_LABORAL,SUELDO,FECHA_NAC,PUESTO_ID,AREA_ID,TRABAJO_ID,"
                + "CONTRATO_ID,SEGURO_ID,PRUEBA_ID FROM EMPLEADO "
                + "WHERE (NOMBRE || ' ' ||APELLIDO)='"+nombreCom+"'");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            PreparedStatement sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            ResultSet resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                dni.setText(resultado.getString(1));
                nombre.setText(resultado.getString(2));
                apellido.setText(resultado.getString(3));
                celular.setText(resultado.getString(4));
                sex=resultado.getString(5);
                direccion.setText(resultado.getString(6));
                categoria.setText(resultado.getString(7));
                sueldo.setText(resultado.getString(8));
                nacimiento.setDate(resultado.getDate(9));
                pues=resultado.getString(10);
                ar=resultado.getString(11);
                trab=resultado.getString(12);
                cont=resultado.getString(13);
                segu=resultado.getString(14);
                pru=resultado.getInt(15);
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(sex=="M"){
            masculino.setSelected(true);
            femenino.setSelected(false);
        }else if(sex=="F"){
            masculino.setSelected(false);
            femenino.setSelected(true);
        }
        if(pues=="CEO"){
                puesto.setSelectedItem("Director Ejecutivo");
        }else if(pues=="COO"){
                puesto.setSelectedItem("Director de Operaciones");
        }else if(pues=="CSO"){
                puesto.setSelectedItem("Director Comercial");
        }else if(pues=="CMO"){
                puesto.setSelectedItem("Director Marketing");
        }else if(pues=="CHRO"){
                puesto.setSelectedItem("Director de Recursos Humanos");
        }else if(pues=="CS"){
                puesto.setSelectedItem("Customer Success");
        }else if(pues=="CFO"){
                puesto.setSelectedItem("Director Financiero");
        }
        if(ar=="TI"){
                area.setSelectedItem("Tecnologias de la informacion");
        }else if(ar=="MK"){
                area.setSelectedItem("Marketing");
        }else if(ar=="FN"){
                area.setSelectedItem("Finanzas");
        }
        if(trab=="LIM"){
                trabajo.setSelectedItem("Limpidador");
        }else if(trab=="TEC"){
                trabajo.setSelectedItem("Tecnico");;
        }else if(trab=="PRO"){
                trabajo.setSelectedItem("Programador");;
        }else if(trab=="ECN"){
                trabajo.setSelectedItem("Economista");;
        }else if(trab=="CAJ"){
                trabajo.setSelectedItem("Cajero");;
        }else if(trab=="REP"){
                trabajo.setSelectedItem("Reponedor");;
        }
        if(cont=="TMP"){
                contrato.setSelectedItem("Temporal");
        }
        if(segu=="INTSG"){
                seguro.setSelectedItem("INTERSEGURO");
        }else if(segu=="LPOST"){
                seguro.setSelectedItem("LA POSITIVA");
        }else if(segu=="RMCSG"){
                seguro.setSelectedItem("RIMAC SEGUROS");
        }
        if(pru==1){
                prueba.setSelectedItem("VIRTUAL");
        }
    }
    public int cambiarEmpleado(int dni,String nombre,String apellido,String sexo,
            int celular,String direccion,String categoria,int sueldo,
            String nacimiento,JComboBox puesto,JComboBox area,JComboBox trabajo,
            JComboBox contrato,JComboBox seguro,JComboBox prueba){
        int resultado=0;
        Connection conexion=null;
        String pues="";
        String ar="";
        String trab="";
        String cont="";
        String segu="";
        int pru=0;
        if(puesto.getSelectedItem().equals("Director Ejecutivo")){
                pues="CEO";
        }else if(puesto.getSelectedItem().equals("Director de Operaciones")){
                pues="COO";
        }else if(puesto.getSelectedItem().equals("Director Comercial")){
                pues="CSO";
        }else if(puesto.getSelectedItem().equals("Director Marketing")){
                pues="CMO";
        }else if(puesto.getSelectedItem().equals("Director de Recursos Humanos")){
                pues="CHRO";
        }else if(puesto.getSelectedItem().equals("Customer Success")){
                pues="CS";
        }else if(puesto.getSelectedItem().equals("Director Financiero")){
                pues="CFO";
        }
        if(area.getSelectedItem().equals("Tecnologias de la informacion")){
                ar="TI";
        }else if(area.getSelectedItem().equals("Marketing")){
                ar="MK";
        }else if(area.getSelectedItem().equals("Finanzas")){
                ar="FN";
        }
        if(trabajo.getSelectedItem().equals("Limpidador")){
                trab="LIM";
        }else if(trabajo.getSelectedItem().equals("Tecnico")){
                trab="TEC";
        }else if(trabajo.getSelectedItem().equals("Programador")){
                trab="PRO";
        }else if(trabajo.getSelectedItem().equals("Economista")){
                trab="ECN";
        }else if(trabajo.getSelectedItem().equals("Cajero")){
                trab="CAJ";
        }else if(trabajo.getSelectedItem().equals("Reponedor")){
                trab="REP";
        }
        if(contrato.getSelectedItem().equals("Temporal")){
                cont="TMP";
        }
        if(seguro.getSelectedItem().equals("INTERSEGURO")){
                segu="INTSG";
        }else if(seguro.getSelectedItem().equals("LA POSITIVA")){
                segu="LPOST";
        }else if(seguro.getSelectedItem().equals("RIMAC SEGUROS")){
                segu="RMCSG";
        }
        if(prueba.getSelectedItem().equals("VIRTUAL")){
                pru=1;
        }
        String sentencia_guardar=("UPDATE EMPLEADO SET nombre=?,apellido=?,correo=?,"
                + "sexo=?,celular=?,direccion=?,categorial_laboral=?,sueldo=?,fecha_nac=?,"
                + "puesto_id=?,area_id=?,trabajo_id=?,contrato_id=?,seguro_id=?,prueba_id=?,"
                + "WHERE dni=?");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.setString(1,nombre);
            sentencia_preparada.setString(2,apellido);
            sentencia_preparada.setString(3,nombre+"."+apellido+"@mallplaza.com");
            sentencia_preparada.setString(4,sexo);
            sentencia_preparada.setInt(5,celular);
            sentencia_preparada.setString(6,direccion);
            sentencia_preparada.setString(7,categoria);
            sentencia_preparada.setInt(8,sueldo);
            sentencia_preparada.setString(9,nacimiento);
            sentencia_preparada.setString(10,pues);
            sentencia_preparada.setString(11,ar);
            sentencia_preparada.setString(12,trab);
            sentencia_preparada.setString(13,cont);
            sentencia_preparada.setString(14,segu);
            sentencia_preparada.setInt(15,pru);
            sentencia_preparada.setInt(16,dni);
            
            sentencia_preparada.execute();
            sentencia_preparada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    public boolean existeEmpleado(String nombre){
        boolean existe=false;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT NOMBRE || ' ' ||APELLIDO "
                    + "FROM empleado WHERE NOMBRE || ' ' ||APELLIDO='"+nombre+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            res=sentencia_preparada.executeQuery();
            if(res.next()){
                existe=true;
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return existe;
    }
    public boolean existeCorreo(String correo){
        boolean existe=false;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT correo "
                    + "FROM empleado WHERE correo='"+correo+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            res=sentencia_preparada.executeQuery();
            if(res.next()){
                existe=true;
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return existe;
    }
    public void eliminarEmpleado(String nombre){
        Connection conexion=null;
        String sentencia_guardar=("DELETE FROM EMPLEADO WHERE NOMBRE || ' ' ||APELLIDO='"+nombre+"'");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.execute();
            sentencia_preparada.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /*
    public int buscarPorcentaje(){
        int porcentaje=0;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT porcentaje FROM contra_porcen");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                porcentaje=resultado.getInt("porcentaje");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return porcentaje;
    }
    public int cambiarPorcentaje(int porcentaje){
        int resultado=0;
        String sentencia_guardar=("UPDATE contra_porcen SET porcentaje='"+porcentaje+"'");
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultado;
    }
    public int guardarProducto(String codigo,String producto,int cantidad,int unidad,float costo,String lugar){
        int resultado=0;
        String sentencia_guardar=("INSERT INTO "+lugar+" (codigo,producto,cantidad,unidad_caja,costo) VALUES(?,?,?,?,?)");
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.setString(1, codigo);
            sentencia_preparada.setString(2, producto);
            sentencia_preparada.setInt(3, cantidad);
            sentencia_preparada.setInt(4, unidad);
            sentencia_preparada.setFloat(5, costo);
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultado;
    }
    public String buscarCodigo(String producto,String lugar){
        String busqueda_codigo="";
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT codigo FROM "+lugar+" WHERE producto='"+producto+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                busqueda_codigo=String.valueOf(resultado.getString("codigo"));
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return busqueda_codigo;
    }
    public String buscarProducto(String codigo,String lugar){
        String busqueda_producto="";
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT producto FROM "+lugar+" WHERE codigo='"+codigo+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                busqueda_producto=resultado.getString("producto");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return busqueda_producto;
    }
    public boolean verificarProducto(String codigo, String producto,String lugar){
        boolean busqueda_producto=false;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT producto FROM "+lugar+" WHERE codigo='"+codigo+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                if(resultado.getString("producto").equals(producto)){
                    busqueda_producto=true;
                }
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return busqueda_producto;
    }
    public int cantidadDeProducto(String codigo, String producto,String lugar){
        int busqueda_cantidad=0;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT cantidad FROM "+lugar+" WHERE codigo='"+codigo+"' AND producto='"+producto+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                busqueda_cantidad=resultado.getInt("cantidad");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return busqueda_cantidad;
    }
    public int descontarProducto(String codigo,String producto,int cantidad,String lugar){
        int resultado=0;
        String sentencia_guardar=("UPDATE "+lugar+" SET cantidad='"+(cantidadDeProducto(codigo, producto,lugar)-cantidad)
                +"' WHERE codigo='"+codigo+"' AND producto='"+producto+"'");
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultado;
    }
    public int agregarCantidadDeProducto(String codigo,String producto,int cantidad,String lugar){
        int resultado=0;
        String sentencia_guardar=("UPDATE "+lugar+" SET cantidad='"+(cantidadDeProducto(codigo, producto,lugar)+cantidad)
                +"' WHERE codigo='"+codigo+"' AND producto='"+producto+"'");
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultado;
    }
    public int unidadesPorCaja(String codigo,String producto,String lugar){
        int unidades=0;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT unidad_caja FROM "+lugar+" WHERE codigo='"+codigo+"' AND producto='"+producto+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                unidades=resultado.getInt("unidad_caja");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return unidades;
    }
    public float costoDeProducto(String codigo,String producto,String lugar){
        float costo=0;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            String sentencia_buscar=("SELECT costo FROM "+lugar+" WHERE codigo='"+codigo+"' AND producto='"+producto+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_buscar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                costo=resultado.getFloat("costo");
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return costo;
    }
    public  int cambiarCostoDeProducto(String codigo,String producto,float costo,String lugar){
        int resultado=0;
        String sentencia_guardar=("UPDATE "+lugar+" SET costo='"+costo+"' WHERE codigo='"+codigo+"' AND producto='"+producto+"'");
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultado;
    }
    public float costoTotal(String lugar){
        float costoTotal=0;
        try {
            Connection conexion=ConexionBD.conectarBaseDatos();
            PreparedStatement sentencia_preparada=conexion.prepareStatement(
                    "SELECT costo,SUM(cantidad*costo/unidad_caja) AS costo_total FROM "+lugar);
            ResultSet resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                costoTotal=resultado.getFloat(2);
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return costoTotal;
    }
    public float precioDeVenta(String codigo,String producto,String lugar,int cantidad){
        float precio=0;
        precio=cantidad*costoDeProducto(codigo, producto, lugar)/unidadesPorCaja(codigo, producto, lugar);
        precio+=precio*buscarPorcentaje()/100;
        return precio;
    }
    public void actualizarTabla(DefaultTableModel modelo,String lugar){
        if(modelo.getRowCount()>0){
            modelo.setNumRows(0);
        }
        String[] producto=new String[6];
        Connection conexion=null;
        String sentencia_mostrar=("SELECT * FROM "+lugar+" ORDER BY producto");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            PreparedStatement sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            ResultSet resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                producto[0]=resultado.getString("codigo");
                producto[1]=resultado.getString("producto");
                producto[2]=String.valueOf(cajasEnTabla(Integer.parseInt(resultado.getString("cantidad")), Integer.parseInt(resultado.getString("unidad_caja"))));
                producto[3]=String.valueOf(unidadesEnTabla(Integer.parseInt(resultado.getString("cantidad")), Integer.parseInt(resultado.getString("unidad_caja"))));
                producto[4]=resultado.getString("unidad_caja");
                producto[5]=String.valueOf(resultado.getFloat("costo"));
                modelo.addRow(producto);
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public int guardarVenta(String codigo,String producto,int cantidad,int unidad,float venta){
        int resultado=0;
        Connection conexion=null;
        String sentencia_guardar=("INSERT INTO venta (n,codigo,producto,cantidad,unidad_caja,venta,fecha,hora) VALUES(?,?,?,?,?,?,?,?)");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.setInt(1, 0);
            sentencia_preparada.setString(2, codigo);
            sentencia_preparada.setString(3, producto);
            sentencia_preparada.setInt(4, cantidad);
            sentencia_preparada.setInt(5, unidad);
            sentencia_preparada.setFloat(6, venta);
            sentencia_preparada.setDate(7, convert(FechaActual()));
            sentencia_preparada.setString(8, HoraActual());
            resultado=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return resultado;
    }
    public void actualizarTablaVenta(DefaultTableModel modeloV){
        if(modeloV.getRowCount()>0){
            modeloV.setNumRows(0);
        }
        String[] producto=new String[8];
        Connection conexion=null;
        String sentencia_mostrar=("SELECT * FROM venta ORDER BY n DESC");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            PreparedStatement sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            ResultSet resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                producto[0]=resultado.getString("n");
                producto[1]=resultado.getString("codigo");
                producto[2]=resultado.getString("producto");
                producto[3]=String.valueOf(cajasEnTabla(Integer.parseInt(resultado.getString("cantidad")), Integer.parseInt(resultado.getString("unidad_caja"))));
                producto[4]=String.valueOf(unidadesEnTabla(Integer.parseInt(resultado.getString("cantidad")), Integer.parseInt(resultado.getString("unidad_caja"))));
                producto[5]=String.valueOf(resultado.getFloat("venta"));
                producto[6]=FechaActualS(resultado.getDate("fecha"));
                producto[7]=resultado.getString("hora");
                modeloV.addRow(producto);
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public java.sql.Date convert(Date dia){
        java.sql.Date day=new java.sql.Date(dia.getTime());
        return day;
    }
    public static String FechaActualS(Date fecha){
        SimpleDateFormat formato=new SimpleDateFormat("dd/MM/YYYY");
        return formato.format(fecha);
    }
    public Calendar FechaActualCalendar(){
        Date fecha=new Date();
        Calendar dia=new GregorianCalendar();
        dia.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate());
        return dia;
    }
    public Date FechaActual(){
        Date fecha=new Date();
        return fecha;
    }
    public String DiaSemana(int dia){
        String diaS="";
        switch(dia){
            case 1:
                diaS="Lunes";
                break;
            case 2:
                diaS="Martes";
                break;
            case 3:
                diaS="Miercoles";
                break;
            case 4:
                diaS="Jueves";
                break;
            case 5:
                diaS="Viernes";
                break;
            case 6:
                diaS="Sabado";
                break;
            case 0:
                diaS="Domingo";
                break;
        }
        return diaS;
    }
    public String MesAño(int mes){
        String mesA="";
        switch(mes){
            case 0:
                mesA="Enero";
                break;
            case 1:
                mesA="Febrero";
                break;
            case 2:
                mesA="Marzo";
                break;
            case 3:
                mesA="Abril";
                break;
            case 4:
                mesA="Mayo";
                break;
            case 5:
                mesA="Junio";
                break;
            case 6:
                mesA="Julio";
                break;
            case 7:
                mesA="Agosto";
                break;
            case 8:
                mesA="Septiembre";
                break;
            case 9:
                mesA="Octubre";
                break;
            case 10:
                mesA="Noviembre";
                break;
            case 11:
                mesA="Diciembre";
                break;
        }
        return mesA;
    }
    public static String HoraActual(){
        Date hora=new Date();
        SimpleDateFormat formatoHora=new SimpleDateFormat("HH:mm:ss");
        return formatoHora.format(hora);
    }
    public int cajasEnTabla(int cantidad,int unidad_caja){
        int cajas=0;
        cajas=cantidad/unidad_caja;
        return cajas;
    }
    public int unidadesEnTabla(int cantidad,int unidad_caja){
        int unidades=0;
        unidades=cantidad%unidad_caja;
        return unidades;
    }*/
}