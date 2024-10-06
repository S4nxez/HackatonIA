package nacho.llorente.framework.pantalladetallada

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import nacho.llorente.databinding.ActivityDetailedBinding
import dagger.hilt.android.AndroidEntryPoint


import android.os.Bundle
import nacho.llorente.framework.common.ConstantesFramework

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private val viewModel: DetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(ConstantesFramework.CLASSDETAILED, 0)
        //viewModel.handleEvent(DetailedEvent.GetClass(id))

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.uiState.observe(this) { state ->
            observeState(state)
        }

        binding.button.setOnClickListener {
            val tableId = binding.tableId.text.toString().toIntOrNull()
            if (tableId != null){

                //aqui hacemos que cuando le de al boton que ahora es add que va a ser el de enviar una duda, que llame a la funcion de enviar la duda, y que luego haga un get de las dudas para que se actualice la lista
                /*
                viewModel.handleEvent(DetailedEvent.NewDuda(id)) //este es el id de la clase, sirve para que teng mas contexto
                viewModel.handleEvent(DetailedEvent.GetDudas(id))

                 */
            }
            else
                Toast.makeText(this, ConstantesFramework.NUMBERMESAERROR, Toast.LENGTH_SHORT).show()
        }

    }
    private fun observeState(state: DetailedState) {

        //si el state de el mensaje cambia, que añade el nuevo mensaje a la lista
        /*
        state.dudas.let {
        with(binding) {
            dudaAdapter.submitList(it)
        }
         */

        state.error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}