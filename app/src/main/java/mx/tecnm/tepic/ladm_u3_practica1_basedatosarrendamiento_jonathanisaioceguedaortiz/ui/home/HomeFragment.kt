package mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.ui.home

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.Automovil
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listaIDs = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        mostrarDatosEnListView()

        binding.insertar.setOnClickListener {
            var automovil = Automovil(requireContext())

            automovil.modelo = binding.modelo.text.toString()
            automovil.marca = binding.marca.text.toString()
            automovil.kilometraje = binding.kilometraje.text.toString().toInt()

            val resultado = automovil.insertar()
            if (resultado){
                Toast.makeText(requireContext(), "Se insertó con exito", Toast.LENGTH_LONG).show()
                mostrarDatosEnListView()
                binding.modelo.setText("")
                binding.marca.setText("")
                binding.kilometraje.setText("")
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }

        binding.buscar.setOnClickListener {
            var datosAutomovil = ArrayList<Automovil>()
            if (!binding.marca.text.isEmpty()){
                datosAutomovil = Automovil(requireContext()).mostrarAutomovilMAR(binding.marca.text.toString())
            }else if (!binding.modelo.text.isEmpty()){
                datosAutomovil = Automovil(requireContext()).mostrarAutomovilMOD(binding.modelo.text.toString())
            }else if (!binding.kilometraje.text.isEmpty()){
                datosAutomovil = Automovil(requireContext()).mostrarAutomovilKIL(binding.kilometraje.text.toString())
            }
            var mensaje = ""
            if (datosAutomovil.size == 0){
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("No hay datos que cumplan esas caracteristicas!")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
                return@setOnClickListener
            }
            (0..datosAutomovil.size-1).forEach {
                mensaje += "Id: " + datosAutomovil[it].idAuto + " | " + datosAutomovil[it].marca + " " +  datosAutomovil[it].modelo + " kilometraje = " +
                datosAutomovil[it].kilometraje + " \n"
            }
            AlertDialog.Builder(requireContext())
                .setTitle("Datos recuperados")
                .setMessage(mensaje)
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun mostrarDatosEnListView(){
        var listaAutomovil = Automovil(requireContext()).mostrarTodos()
        var datosAutomovil = ArrayList<String>()

        listaIDs.clear()
        (0..listaAutomovil.size-1).forEach {
            val auto = listaAutomovil.get(it)
            datosAutomovil.add("" + auto.idAuto + " " + auto.marca + " " + auto.modelo + " | Kms: " + auto.kilometraje)
            listaIDs.add(auto.idAuto.toString())
        }

        binding.lista.adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_list_item_1, datosAutomovil)

        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idAutomovilClick = listaIDs.get(indice)
            val auto = Automovil(requireContext()).mostrarAutomovilID(idAutomovilClick)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("¿Qué deseas hacer con ${auto.marca},\nModelo: ${auto.modelo} con id: ${auto.idAuto}?")
                .setNegativeButton("Eliminar") { d, i ->
                    if (auto.eliminar()) {
                    }else{
                        AlertDialog.Builder(requireContext())
                            .setTitle("ERROR")
                            .setMessage("NO SE PUDO ELIMINAR EL DATO PORQUE HAY UN(OS) ARRENDAMIENTO(S) ASOCIADO(S)")
                            .show()
                    }
                    mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentana = Intent(requireContext(), ActualizarAuto::class.java)

                    otraVentana.putExtra("idauto", idAutomovilClick)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar"){d,i->}
                .show()
        }


    }

    override fun onResume() {
        mostrarDatosEnListView()
        super.onResume()
    }
}