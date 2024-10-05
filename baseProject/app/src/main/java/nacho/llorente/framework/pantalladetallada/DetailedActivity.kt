package nacho.llorente.framework.pantalladetallada

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import nacho.llorente.databinding.ActivityDetailedBinding
import dagger.hilt.android.AndroidEntryPoint


import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import nacho.llorente.domain.modelo.Order
import nacho.llorente.framework.common.ConstantesFramework

@AndroidEntryPoint
class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var orderAdapter: OrderAdapter
    private val viewModel: DetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(ConstantesFramework.CUSTOMERDETAILED, 0)
        viewModel.handleEvent(DetailedEvent.GetCustomer(id))
        viewModel.handleEvent(DetailedEvent.GetOrders(id))

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

        viewModel.uiState.observe(this) { state ->
            observeState(state)
        }

        binding.button.setOnClickListener {
            val tableId = binding.tableId.text.toString().toIntOrNull()
            if (tableId != null){
                viewModel.handleEvent(DetailedEvent.AddOrder(tableId))

                viewModel.handleEvent(DetailedEvent.GetOrders(id))
            }
            else
                Toast.makeText(this, ConstantesFramework.NUMBERMESAERROR, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupAdapter() {
        orderAdapter = OrderAdapter(this,
            object : OrderAdapter.OrderActions {
                override fun onDelete(order: Order) =
                    viewModel.handleEvent(DetailedEvent.DeleteOrder(order))
            })
        binding.rvOrders.adapter = orderAdapter
        val touchHelper = ItemTouchHelper(orderAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvOrders)
    }

    private fun observeState(state: DetailedState) {
        state.customer?.let {
            with(binding) {
                binding.tvName.text = it.name + " " + it.surname
                binding.tvEmail.text = it.email
                binding.tvPhone.text = it.phone.toString()
            }
        }

        state.orders.let {
            orderAdapter.submitList(it)
        }

        state.error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}