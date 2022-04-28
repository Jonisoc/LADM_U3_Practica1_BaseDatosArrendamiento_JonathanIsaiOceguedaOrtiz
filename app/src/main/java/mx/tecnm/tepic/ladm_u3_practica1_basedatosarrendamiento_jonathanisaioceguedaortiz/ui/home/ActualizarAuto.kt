package mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.Automovil
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.databinding.ActivityActualizarAutoBinding

class ActualizarAuto : AppCompatActivity() {
    lateinit var binding:ActivityActualizarAutoBinding
    var idAuto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarAutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idAuto = this.intent.extras!!.getString("idauto")!!
        val automovil = Automovil(this).mostrarAutomovilID(idAuto)

        binding.marca.setText(automovil.marca)
        binding.modelo.setText(automovil.modelo)
        binding.kilometraje.setText(automovil.kilometraje.toString())

        binding.actualizar.setOnClickListener{
            var automovil = Automovil(this)

            automovil.idAuto = idAuto.toInt()
            automovil.marca = binding.marca.text.toString()
            automovil.modelo = binding.modelo.text.toString()
            automovil.kilometraje = binding.kilometraje.text.toString().toInt()

            val respuesta = automovil.actualizar()

            if (respuesta){
                Toast.makeText(this, "Se actualizó con exito", Toast.LENGTH_LONG).show()
                binding.modelo.setText("")
                binding.marca.setText("")
                binding.kilometraje.setText("")
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