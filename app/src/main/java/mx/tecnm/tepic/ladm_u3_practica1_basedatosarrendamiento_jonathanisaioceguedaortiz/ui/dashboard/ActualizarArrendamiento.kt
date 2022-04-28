package mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.Arrendamiento
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.Automovil
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.databinding.ActivityActualizarArrendamientoBinding

class ActualizarArrendamiento : AppCompatActivity() {
    lateinit var binding: ActivityActualizarArrendamientoBinding
    var idArren = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarArrendamientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idArren = this.intent.extras!!.getString("idarrendamiento")!!
        val arrendamiento = Arrendamiento(this).mostrarArrendaID(idArren)

        binding.domicilio.setText(arrendamiento.domicilio)
        binding.licencia.setText(arrendamiento.licenciaCond)
        binding.nombre.setText(arrendamiento.nombre)
        binding.marca.setText(arrendamiento.marca)
        binding.modelo.setText(arrendamiento.modelo)
        binding.fecha.isEnabled = false
        binding.fecha.setText(arrendamiento.fecha)

        binding.actualizar.setOnClickListener{
            var arrendamiento = Arrendamiento(this)

            arrendamiento.idArrenda = idArren.toInt()
            arrendamiento.nombre = binding.nombre.text.toString()
            arrendamiento.licenciaCond = binding.licencia.text.toString()
            arrendamiento.domicilio = binding.domicilio.text.toString()
            arrendamiento.marca = binding.marca.text.toString()
            arrendamiento.modelo = binding.modelo.text.toString()

            val respuesta = arrendamiento.actualizar()

            if (respuesta){
                Toast.makeText(this, "Se actualizó con exito", Toast.LENGTH_LONG).show()
                binding.domicilio.setText("")
                binding.licencia.setText("")
                binding.nombre.setText("")
                binding.marca.setText("")
                binding.modelo.setText("")
                binding.fecha.setText("")
            } else {
                AlertDialog.Builder(this)
                    .setTitle("ATENCION")
                    .setMessage("ERROR NO SE ACTUALIZO")
                    .show()
            }
        }

        binding.regresar.setOnClickListener {
            finish()
        }
    }
}