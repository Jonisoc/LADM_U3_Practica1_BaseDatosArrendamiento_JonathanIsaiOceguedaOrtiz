package mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Arrendamiento(este: Context) {
    var idArrenda = 0
    var nombre = ""
    var domicilio = ""
    var licenciaCond = ""
    var idAuto = 0
    var fecha = "YYYY-MM-DD"
    //var fecha = "YYYY-MM-DD HH:MM:SS.SSS"
    var marca = ""
    var modelo = ""
    private var este = este
    var baseDatos = BaseDatos(este, "renta_autos", null, 1)
    private var err = ""
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    //  CREATE TABLE ARRENDAMIENTO (
        //  IDARRENDA INTEGER PRIMARY KEY AUTOINCREMENT
        //  NOMBRE VARCHAR(20)
        //  DOMICILIO VARCHAR(50)
        //  LICENCIACOND VARCHAR(50)
        //  IDAUTO INTEGER
        //  FECHA DATE
    //  )

    fun insertar() : Boolean{
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            var SQL_SELECT = "SELECT IDAUTO FROM AUTOMOVIL WHERE MARCA = ? AND MODELO = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(marca, modelo))
            if (cursor.moveToFirst()){
                idAuto = cursor.getInt(0)
            }else{
                return false
            }
            datos.put("NOMBRE", nombre)
            datos.put("DOMICILIO", domicilio)
            datos.put("LICENCIACOND", licenciaCond)
            datos.put("IDAUTO", idAuto)
            fecha = sdf.format(Date())
            datos.put("FECHA", fecha)


            var resultado = tabla.insert("ARRENDAMIENTO", "IDARRENDA", datos)
            if (resultado == -1L){
                return false
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
            return false
        } finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarTodos() : ArrayList<Arrendamiento> {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO"

            var cursor = tabla.rawQuery(SQL_SELECT, null)
            if (cursor.moveToFirst()){
                do {
                    var arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licenciaCond = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    arrendamiento.fecha = cursor.getString(5)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarArrendaID(idArrendamiento:String) : Arrendamiento {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var arrendamiento = Arrendamiento(este)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDARRENDA = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(idArrendamiento))

            if (cursor.moveToFirst()){
                arrendamiento.idArrenda = cursor.getInt(0)
                arrendamiento.nombre = cursor.getString(1)
                arrendamiento.domicilio = cursor.getString(2)
                arrendamiento.licenciaCond = cursor.getString(3)
                arrendamiento.idAuto = cursor.getInt(4)
                var auto = Automovil(este).mostrarAutomovilID(cursor.getInt(4).toString())
                arrendamiento.marca = auto.marca
                arrendamiento.modelo = auto.modelo
                arrendamiento.fecha = cursor.getString(5)
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arrendamiento
    }

    fun eliminar():Boolean{
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            val resultado = tabla.delete("ARRENDAMIENTO", "IDARRENDA=?", arrayOf(idArrenda.toString()))
            if (resultado==0){
                return false
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return true
    }

    fun actualizar():Boolean{
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            val datosActualizados = ContentValues()

            var SQL_SELECT = "SELECT IDAUTO FROM AUTOMOVIL WHERE MARCA = ? AND MODELO = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(marca, modelo))
            if (cursor.moveToFirst()){
                idAuto = cursor.getInt(0)
            }else{
                return false
            }

            datosActualizados.put("NOMBRE", nombre)
            datosActualizados.put("DOMICILIO", domicilio)
            datosActualizados.put("LICENCIACOND", licenciaCond)
            datosActualizados.put("IDAUTO", idAuto)
            //datosActualizados.put("FECHA", fecha)

            val respuesta = tabla.update("ARRENDAMIENTO", datosActualizados, "IDARRENDA=?", arrayOf(idArrenda.toString()))

            if (respuesta==0){
                return false
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarArrendaNOM(nombre:String) : ArrayList<Arrendamiento> {
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE NOMBRE = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(nombre))
            if (cursor.moveToFirst()){
                do {
                    var arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licenciaCond = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    arrendamiento.fecha = cursor.getString(5)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarArrendaLIC(licenciaCond:String) : ArrayList<Arrendamiento> {
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE LICENCIACOND = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(licenciaCond))
            if (cursor.moveToFirst()){
                do {
                    var arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licenciaCond = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    arrendamiento.fecha = cursor.getString(5)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarArrendaDOM(domicilio:String) : ArrayList<Arrendamiento> {
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE DOMICILIO = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(domicilio))
            if (cursor.moveToFirst()){
                do {
                    var arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licenciaCond = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    arrendamiento.fecha = cursor.getString(5)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarArrendaMAR(marca:String) : ArrayList<Arrendamiento> {
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDAUTO = (SELECT IDAUTO FROM AUTOMOVIL WHERE MARCA = ?)"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(marca))
            if (cursor.moveToFirst()){
                do {
                    var arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licenciaCond = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    arrendamiento.fecha = cursor.getString(5)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarArrendaMOD(modelo:String) : ArrayList<Arrendamiento> {
        var arreglo = ArrayList<Arrendamiento>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDAUTO = (SELECT IDAUTO FROM AUTOMOVIL WHERE MODELO = ?)"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(modelo))
            if (cursor.moveToFirst()){
                do {
                    var arrendamiento = Arrendamiento(este)
                    arrendamiento.idArrenda = cursor.getInt(0)
                    arrendamiento.nombre = cursor.getString(1)
                    arrendamiento.domicilio = cursor.getString(2)
                    arrendamiento.licenciaCond = cursor.getString(3)
                    arrendamiento.idAuto = cursor.getInt(4)
                    arrendamiento.fecha = cursor.getString(5)
                    arreglo.add(arrendamiento)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }
}