package mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.ui.dashboard

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
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.Arrendamiento
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.Automovil
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.databinding.FragmentDashboardBinding
import mx.tecnm.tepic.ladm_u3_practica1_basedatosarrendamiento_jonathanisaioceguedaortiz.ui.home.ActualizarAuto

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var listaIDs = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        mostrarDatosEnListView()

        binding.insertar.setOnClickListener {
            var arrendamiento = Arrendamiento(requireContext())

            arrendamiento.nombre = binding.nombre.text.toString()
            arrendamiento.domicilio = binding.domicilio.text.toString()
            arrendamiento.licenciaCond = binding.licencia.text.toString()
            arrendamiento.modelo = binding.modelo.text.toString()
            arrendamiento.marca = binding.marca.text.toString()

            val resultado = arrendamiento.insertar()
            if (resultado){
                Toast.makeText(requireContext(), "Se insertó con exito", Toast.LENGTH_LONG).show()
                mostrarDatosEnListView()
                binding.nombre.setText("")
                binding.domicilio.setText("")
                binding.licencia.setText("")
                binding.modelo.setText("")
                binding.marca.setText("")
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }

        binding.buscar.setOnClickListener {
            var datosArrendamiento = ArrayList<Arrendamiento>()
            if (!binding.nombre.text.isEmpty()){
                datosArrendamiento = Arrendamiento(requireContext()).mostrarArrendaNOM(binding.nombre.text.toString())
            }else if (!binding.licencia.text.isEmpty()){
                datosArrendamiento = Arrendamiento(requireContext()).mostrarArrendaLIC(binding.licencia.text.toString())
            }else if (!binding.domicilio.text.isEmpty()){
                datosArrendamiento = Arrendamiento(requireContext()).mostrarArrendaDOM(binding.domicilio.text.toString())
            }else if (!binding.marca.text.isEmpty()){
                datosArrendamiento = Arrendamiento(requireContext()).mostrarArrendaMAR(binding.marca.text.toString())
            }else if (!binding.modelo.text.isEmpty()){
                datosArrendamiento = Arrendamiento(requireContext()).mostrarArrendaMOD(binding.modelo.text.toString())
            }
            var mensaje = ""
            if (datosArrendamiento.size == 0){
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("No hay datos que cumplan esas caracteristicas!")
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()
                return@setOnClickListener
            }
            (0..datosArrendamiento.size-1).forEach {
                mensaje += "NOMBRE: " + datosArrendamiento[it].nombre +
                        " DOMICILIO: " + datosArrendamiento[it].domicilio +
                        " LICENCIA: " + datosArrendamiento[it].licenciaCond +
                        " ID AUTO: " + datosArrendamiento[it].idAuto +
                        " FECHA: " + datosArrendamiento[it].fecha + "\n\n"
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
        var listaArrendamiento = Arrendamiento(requireContext()).mostrarTodos()
        var datosArrendamiento = ArrayList<String>()

        listaIDs.clear()
        (0..listaArrendamiento.size-1).forEach {
            val arrenda = listaArrendamiento.get(it)
            datosArrendamiento.add("\n" + arrenda.nombre + "\n" + arrenda.domicilio + "\n" + arrenda.licenciaCond + "\n" + arrenda.fecha + "\nId Auto: " + arrenda.idAuto + "\n")
            listaIDs.add(arrenda.idArrenda.toString())
        }

        binding.lista.adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_list_item_1, datosArrendamiento)

        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val idArrendaClick = listaIDs.get(indice)
            val arrenda = Arrendamiento(requireContext()).mostrarArrendaID(idArrendaClick)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("¿Qué deseas hacer con ${arrenda.nombre}\nDomicilio: ${arrenda.domicilio} \nLicencia: ${arrenda.licenciaCond} \n" +
                        "Fecha: ${arrenda.fecha} \n" +
                        "Carro: ${arrenda.marca} ${arrenda.modelo}?")
                .setNegativeButton("Eliminar"){d,i->
                    arrenda.eliminar()
                    mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i->
                    var otraVentana = Intent(requireContext(), ActualizarArrendamiento::class.java)

                    otraVentana.putExtra("idarrendamiento", idArrendaClick)
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