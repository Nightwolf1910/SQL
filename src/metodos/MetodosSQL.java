package metodos;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MetodosSQL {
    public static Connection conexion=null;
    public static PreparedStatement sentencia_preparada;
    public static ResultSet resultado; 
    public static int resultadoNumero=0;
    public static String sentencia_mostrar;
    DefaultTableModel modelo=new DefaultTableModel();
    String sex="";
    String pues="";
    String ar="";
    String trab="";
    String cont="";
    String segu="";
    int pru=0;
    
    public String contraseñaEmpleado(String correo){
        String contraDB="";
        sentencia_mostrar=("SELECT CONTRASEÑA FROM EMPLEADO WHERE CORREO='"+correo+"'");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
            while(resultado.next()){
                contraDB=resultado.getString(1);
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return contraDB;
    }
    public void buscarEmpleados(DefaultTableModel modelo){
        if(modelo.getRowCount()>0){
            modelo.setNumRows(0);
        }
        String[] fila=new String[9];
        sentencia_mostrar=("SELECT E.DNI,E.NOMBRE || ' ' ||E.APELLIDO AS NOMBRE,TRUNC(MONTHS_BETWEEN(SYSDATE, E.FECHA_NAC)/12) AS EDAD,\n" +
            "E.SEXO,E.CELULAR,E.SUELDO,A.NOMBRE AS AREA,T.ROL,S.ASEGURADORA\n" +
            "FROM EMPLEADO E, AREA A, TRABAJO T, SEGURO S\n" +
            "WHERE A.AREA_ID=E.AREA_ID AND T.TRABAJO_ID=E.TRABAJO_ID AND S.SEGURO_ID=E.SEGURO_ID");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
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
                modelo.addRow(fila);
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void guardarEmpleado(int dni,String nombre,String apellido,String sexo,
            int celular,String direccion,String categoria,int sueldo,
            String nacimiento,JComboBox puesto,JComboBox area,JComboBox trabajo,
            JComboBox contrato,JComboBox seguro,JComboBox prueba,String contraseña){
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
        
        String sentencia_guardar=("INSERT INTO empleado(dni,nombre,apellido,correo,"
                + "sexo,celular,direccion,categorial_laboral,sueldo,fecha_nac,fecha_cont,dni_jefe,"
                + "puesto_id,area_id,trabajo_id,contrato_id,seguro_id,prueba_id,contraseña) VALUES(?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_guardar);
            sentencia_preparada.setInt(1, dni);
            sentencia_preparada.setString(2, nombre);
            sentencia_preparada.setString(3, apellido);
            sentencia_preparada.setString(4, nombre+"."+apellido+"@mallplaza.com");
            sentencia_preparada.setString(5, sexo);
            sentencia_preparada.setInt(6, celular);
            sentencia_preparada.setString(7, direccion);
            sentencia_preparada.setString(8, categoria);
            sentencia_preparada.setInt(9, sueldo);
            sentencia_preparada.setString(10, nacimiento);
            sentencia_preparada.setString(11, formato.format(fecha));
            sentencia_preparada.setInt(12, 15616535);
            sentencia_preparada.setString(13, pues);
            sentencia_preparada.setString(14, ar);
            sentencia_preparada.setString(15, trab);
            sentencia_preparada.setString(16, cont);
            sentencia_preparada.setString(17, segu);
            sentencia_preparada.setInt(18, pru);
            sentencia_preparada.setString(19, contraseña);
            resultadoNumero=sentencia_preparada.executeUpdate();
            sentencia_preparada.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void EmpleadosFiltrados(DefaultTableModel modelo,String nombre){
        if(modelo.getRowCount()>0){
            modelo.setNumRows(0);
        }
        String[] fila=new String[9];
        sentencia_mostrar=("SELECT E.DNI,E.NOMBRE || ' ' ||E.APELLIDO AS NOMBRE,TRUNC(MONTHS_BETWEEN(SYSDATE, E.FECHA_NAC)/12) AS EDAD,\n" +
            "E.SEXO,E.CELULAR,E.SUELDO,A.NOMBRE AS AREA,T.ROL,S.ASEGURADORA\n" +
            "FROM EMPLEADO E, AREA A, TRABAJO T, SEGURO S\n" +
            "WHERE A.AREA_ID=E.AREA_ID AND T.TRABAJO_ID=E.TRABAJO_ID AND S.SEGURO_ID=E.SEGURO_ID"
                + " AND (E.NOMBRE LIKE '%"+nombre+"%' OR E.APELLIDO LIKE '%"+nombre+"%')");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
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
                modelo.addRow(fila);
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void mostrarEmpleado(String nombreCom,JTextField dni,JTextField nombre,JTextField apellido,
            JRadioButton masculino,JRadioButton femenino,JTextField celular,JTextField direccion,JTextField categoria,
            JTextField sueldo,JDateChooser nacimiento,JComboBox puesto,JComboBox area,JComboBox trabajo,
            JComboBox contrato,JComboBox seguro,JComboBox prueba,JTextField contraseña){
        sentencia_mostrar=("SELECT DNI,NOMBRE,APELLIDO,CELULAR,SEXO,DIRECCION,"
                + "CATEGORIAL_LABORAL,SUELDO,FECHA_NAC,PUESTO_ID,AREA_ID,TRABAJO_ID,"
                + "CONTRATO_ID,SEGURO_ID,PRUEBA_ID,CONTRASEÑA FROM EMPLEADO "
                + "WHERE (NOMBRE || ' ' ||APELLIDO)='"+nombreCom+"'");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
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
                contraseña.setText(resultado.getString(16));
            }
            conexion.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(sex.equals("M")){
            masculino.setSelected(true);
        }else if(sex.equals("F")){
            femenino.setSelected(true);
        }
        if(pues.equals("CEO")){
                puesto.setSelectedItem("Director Ejecutivo");
        }else if(pues.equals("COO")){
                puesto.setSelectedItem("Director de Operaciones");
        }else if(pues.equals("CSO")){
                puesto.setSelectedItem("Director Comercial");
        }else if(pues.equals("CMO")){
                puesto.setSelectedItem("Director Marketing");
        }else if(pues.equals("CHRO")){
                puesto.setSelectedItem("Director de Recursos Humanos");
        }else if(pues.equals("CS")){
                puesto.setSelectedItem("Customer Success");
        }else if(pues.equals("CFO")){
                puesto.setSelectedItem("Director Financiero");
        }
        if(ar.equals("TI")){
                area.setSelectedItem("Tecnologias de la informacion");
        }else if(ar.equals("MK")){
                area.setSelectedItem("Marketing");
        }else if(ar.equals("FN")){
                area.setSelectedItem("Finanzas");
        }
        if(trab.equals("LIM")){
                trabajo.setSelectedItem("Limpidador");
        }else if(trab.equals("TEC")){
                trabajo.setSelectedItem("Tecnico");;
        }else if(trab.equals("PRO")){
                trabajo.setSelectedItem("Programador");;
        }else if(trab.equals("ECN")){
                trabajo.setSelectedItem("Economista");;
        }else if(trab.equals("CAJ")){
                trabajo.setSelectedItem("Cajero");;
        }else if(trab.equals("REP")){
                trabajo.setSelectedItem("Reponedor");;
        }
        if(cont.equals("TMP")){
                contrato.setSelectedItem("Temporal");
        }
        if(segu.equals("INTSG")){
                seguro.setSelectedItem("INTERSEGURO");
        }else if(segu.equals("LPOST")){
                seguro.setSelectedItem("LA POSITIVA");
        }else if(segu.equals("RMCSG")){
                seguro.setSelectedItem("RIMAC SEGUROS");
        }
        if(pru==1){
                prueba.setSelectedItem("VIRTUAL");
        }
    }
    public void cambiarEmpleado(int dni,String nombre,String apellido,String sexo,
            int celular,String direccion,String categoria,int sueldo,
            String nacimiento,JComboBox puesto,JComboBox area,JComboBox trabajo,
            JComboBox contrato,JComboBox seguro,JComboBox prueba,String contraseña){
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
        sentencia_mostrar=("UPDATE EMPLEADO SET nombre=?,apellido=?,correo=?,"
                + "sexo=?,celular=?,direccion=?,categorial_laboral=?,sueldo=?,fecha_nac=?,"
                + "puesto_id=?,area_id=?,trabajo_id=?,contrato_id=?,seguro_id=?,prueba_id=?,"
                + "contraseña=? WHERE dni=?");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
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
            sentencia_preparada.setString(16, contraseña);
            sentencia_preparada.setInt(17,dni);
            sentencia_preparada.execute();
            sentencia_preparada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public String nombreDeCorreo(String correo){
        String nombre="";
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_mostrar=("SELECT NOMBRE || ' ' ||APELLIDO "
                    + "FROM empleado WHERE CORREO='"+correo+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                nombre=resultado.getString(1);
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return nombre;
    }
    public boolean existeEmpleado(String nombre){
        boolean existe=false;
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_mostrar=("SELECT NOMBRE || ' ' ||APELLIDO "
                    + "FROM empleado WHERE NOMBRE || ' ' ||APELLIDO='"+nombre+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
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
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_mostrar=("SELECT correo "
                    + "FROM empleado WHERE correo='"+correo+"'");
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            resultado=sentencia_preparada.executeQuery();
            if(resultado.next()){
                existe=true;
            }
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return existe;
    }
    public void eliminarEmpleado(String nombre){
        sentencia_mostrar=("DELETE FROM EMPLEADO WHERE NOMBRE || ' ' ||APELLIDO='"+nombre+"'");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            sentencia_preparada.execute();
            sentencia_preparada.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //*******************************PLSQL*****************************
    public void cambioArea(){
        sentencia_mostrar=("CREATE SEQUENCE CAMBIO_SEQUENCE --Crea valores desde 1 a mas\n" +
            "INCREMENT BY 1\n" +
            "START WITH 1 ORDER;\n" +
            "\n" +
            "CREATE OR REPLACE TRIGGER INDEX_CAMBIOS\n" +
            "/*intercepta el insert a la tabla area log\n" +
            "y reemplaza el log_id por la sequencia cambio_sequence*/\n" +
            "BEFORE INSERT ON AREA_LOG \n" +
            "FOR EACH ROW \n" +
            "BEGIN\n" +
            "  :NEW.LOG_ID:=CAMBIO_SEQUENCE.NEXTVAL;\n" +
            "END;\n" +
            "\n" +
            "CREATE OR REPLACE TRIGGER CAMBIO_AREA\n" +
            "/*Cuando se actualiza el area id se inserta un log con el cambio \n" +
            "y un motivo default*/\n" +
            "AFTER UPDATE ON EMPLEADO\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "    CASE\n" +
            "        WHEN UPDATING('AREA_ID') THEN --El 0 dentro del insert sera reemplazado por INDEX_CAMBIOS.\n" +
            "            INSERT INTO AREA_LOG VALUES(:OLD.DNI,0,SYSDATE,:OLD.AREA_ID,:NEW.AREA_ID,'DEFAULT');\n" +
            "    END CASE;\n" +
            "END;");
        try {
            conexion=ConexionBD.conectarBaseDatos();
            sentencia_preparada=conexion.prepareStatement(sentencia_mostrar);
            sentencia_preparada.execute();
            sentencia_preparada.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}