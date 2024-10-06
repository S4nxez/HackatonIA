package nacho.llorente.framework.pantallamain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import nacho.llorente.R
import nacho.llorente.databinding.ActivityMainBinding
import nacho.llorente.domain.modelo.Class
import nacho.llorente.framework.common.ConstantesFramework
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var primeraVez: Boolean = false

    private lateinit var classAdapter: ClassAdapter
    //private lateinit vat classAdapter: ClassAdapter

    private val viewModel: MainViewModel by viewModels()

    // esto es para el context bar
    private val callback by lazy {
        configContextBar()
    }

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        primeraVez = true
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        observarViewModel()
        configAppBar()
    }

    private fun setupAdapter() {
        //initialization of the adapter with the actions
        classAdapter = ClassAdapter(this,
            object : ClassAdapter.ClassActions {
                override fun onDelete(clase: Class) =
                    viewModel.handleEvent(MainEvent.DeleteClass(clase))

                override fun onStartSelectMode(clase: Class) {
                    viewModel.handleEvent(MainEvent.StartSelectMode)
                    viewModel.handleEvent(MainEvent.SeleccionaClass(clase))
                }

                override fun itemHasClicked(clase: Class) {
                    viewModel.handleEvent(MainEvent.SeleccionaClass(clase))
                }
            }
        )


        binding.rvClasses.adapter = classAdapter
        val touchHelper = ItemTouchHelper(classAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvClasses)

    }

    private fun observarViewModel() {

        viewModel.uiState.observe(this) { estado ->
            estado.classes.let {
                if (it.isNotEmpty()) {
                    classAdapter.submitList(it)
                }
            }
            cambiosModoSeleccion(estado)
            estado.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun cambiosModoSeleccion(estado: MainState) {

        estado.classesSeleccionadas.let { classesSeleccionadas ->
            if (classesSeleccionadas.isNotEmpty()) {
                classAdapter.setSelectedItems(classesSeleccionadas)
                if (classesSeleccionadas.size == 1)
                    actionMode?.title = "1 clase seleccionada"
                else
                    actionMode?.title =
                        "${classesSeleccionadas.size} " + ConstantesFramework.SELECTED
            } else {
                classAdapter.resetSelectMode()
                primeraVez = true
                actionMode?.finish()
            }
        }


        estado.selectMode.let { seleccionado ->
            if (seleccionado) {
                if (primeraVez) {
                    classAdapter.startSelectMode()
                    startSupportActionMode(callback)?.let {
                        actionMode = it
                    }

                    primeraVez = false
                } else {
                    classAdapter.startSelectMode()
                }
            } else {
                classAdapter.resetSelectMode()
                primeraVez = true
                actionMode?.finish()
            }
        }
    }

    fun configContextBar() = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.context_bar, menu)
            binding.topAppBar.visibility = android.view.View.GONE
            return true
        }

        //esto es para el context bar
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.more -> { //esto es que cuando el usuario pulsa el boton de borrar se borren los usuarios seleccionados y se quite el modo seleccion
                    viewModel.handleEvent(MainEvent.DeleteClassesSeleccionadas())
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            viewModel.handleEvent(MainEvent.ResetSelectMode)
            binding.topAppBar.visibility = android.view.View.VISIBLE
            classAdapter.resetSelectMode()
        }


        //esto si no lo pongo da error lo siento oscar si te sangran los ojos
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }
    }

    private fun configAppBar() {
        //cuando se pulsa el boton de buscar
        val actionSearch = binding.topAppBar.menu.findItem(R.id.search).actionView as SearchView
        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false //esto es para que no haga nada si se pulsa el boton de buscar
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filtro ->
                    viewModel.handleEvent(MainEvent.GetClassesFiltradas(filtro))
                }
                return true
            }
        })
    }
}