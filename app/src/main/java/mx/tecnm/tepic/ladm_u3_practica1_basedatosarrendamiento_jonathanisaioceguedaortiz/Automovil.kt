package mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Automovil(este: Context) {
    var idAuto = 0
    var modelo = ""
    var marca = ""
    var kilometraje = 0
    private var este = este
    var baseDatos = BaseDatos(este, "renta_autos", null, 1)
    private var err = ""

    fun insertar() : Boolean{
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("MODELO", modelo)
            datos.put("MARCA", marca)
            datos.put("KILOMETRAJE", kilometraje)

            var resultado = tabla.insert("AUTOMOVIL", "IDAUTO", datos)
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

    fun mostrarTodos() : ArrayList<Automovil> {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var arreglo = ArrayList<Automovil>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTOMOVIL"

            var cursor = tabla.rawQuery(SQL_SELECT, null)
            if (cursor.moveToFirst()){
                do {
                    var automovil = Automovil(este)
                    automovil.idAuto = cursor.getInt(0)
                    automovil.modelo = cursor.getString(1)
                    automovil.marca = cursor.getString(2)
                    automovil.kilometraje = cursor.getInt(3)
                    arreglo.add(automovil)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException){
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarAutomovilID(idAutomovil:String) : Automovil {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var automovil = Automovil(este)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTOMOVIL WHERE IDAUTO = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(idAutomovil))
            if (cursor.moveToFirst()){
                automovil.idAuto = cursor.getInt(0)
                automovil.modelo = cursor.getString(1)
                automovil.marca = cursor.getString(2)
                automovil.kilometraje = cursor.getInt(3)
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return automovil
    }

    fun mostrarAutomovilMOD(modelo:String) : ArrayList<Automovil> {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var arreglo = ArrayList<Automovil>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTOMOVIL WHERE MODELO = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(modelo))
            if (cursor.moveToFirst()){
                do {
                    var automovil = Automovil(este)
                    automovil.idAuto = cursor.getInt(0)
                    automovil.modelo = cursor.getString(1)
                    automovil.marca = cursor.getString(2)
                    automovil.kilometraje = cursor.getInt(3)
                    arreglo.add(automovil)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarAutomovilMAR(marca:String) : ArrayList<Automovil> {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var arreglo = ArrayList<Automovil>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTOMOVIL WHERE MARCA = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(marca))
            if (cursor.moveToFirst()){
                do {
                    var automovil = Automovil(este)
                    automovil.idAuto = cursor.getInt(0)
                    automovil.modelo = cursor.getString(1)
                    automovil.marca = cursor.getString(2)
                    automovil.kilometraje = cursor.getInt(3)
                    arreglo.add(automovil)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun mostrarAutomovilKIL(kilom:String) : ArrayList<Automovil> {
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        var arreglo = ArrayList<Automovil>()
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM AUTOMOVIL WHERE KILOMETRAJE <= ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(kilom))
            if (cursor.moveToFirst()){
                do {
                    var automovil = Automovil(este)
                    automovil.idAuto = cursor.getInt(0)
                    automovil.modelo = cursor.getString(1)
                    automovil.marca = cursor.getString(2)
                    automovil.kilometraje = cursor.getInt(3)
                    arreglo.add(automovil)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun eliminar():Boolean{
        //var baseDatos = BaseDatos(este, "renta_autos", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase
            var SQL_SELECT = "SELECT * FROM ARRENDAMIENTO WHERE IDAUTO = ?"

            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(idAuto.toString()))
            if (cursor.moveToFirst()){
                return false
            }
            val resultado = tabla.delete("AUTOMOVIL", "IDAUTO=?", arrayOf(idAuto.toString()))
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

            datosActualizados.put("MODELO", modelo)
            datosActualizados.put("MARCA", marca)
            datosActualizados.put("KILOMETRAJE", kilometraje)

            val respuesta = tabla.update("AUTOMOVIL", datosActualizados, "IDAUTO=?", arrayOf(idAuto.toString()))

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
}