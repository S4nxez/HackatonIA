package nacho.llorente.framework.pantallamain

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nacho.llorente.R
import nacho.llorente.databinding.ViewCustomerBinding
import nacho.llorente.domain.modelo.Customer
import nacho.llorente.framework.common.ConstantesFramework
import nacho.llorente.framework.pantalladetallada.DetailedActivity


class CustomerAdapter(
    val context: Context,
    val actions: CustomerActions
) :
    ListAdapter<Customer, CustomerAdapter.ItemViewholder>(DiffCallback()) {
    private var selectedCustomers = mutableSetOf<Customer>()
    private var selectedMode: Boolean = false

    interface CustomerActions {
        fun onDelete(customer: Customer)
        fun onStartSelectMode(customer: Customer)
        fun itemHasClicked(customer: Customer)

    }


    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }
    fun resetSelectMode() {
        selectedMode = false
        selectedCustomers.clear()
        notifyDataSetChanged()
    }
    fun setSelectedItems(customersSeleccionadas: List<Customer>) {
        selectedCustomers.clear()
        selectedCustomers.addAll(customersSeleccionadas)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_customer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewCustomerBinding.bind(itemView)

        fun bind(item: Customer) {
            listemers(item, itemView, binding)
            with(binding) {
                tvNombre.text = item.name + " " + item.surname
                tvId.text = item.id.toString()
                if (selectedMode)
                    selected.visibility = View.VISIBLE
                else {
                    item.isSelected = false
                    selected.visibility = View.GONE
                }

                if (selectedCustomers.contains(item)) {
                    itemView.setBackgroundResource(R.drawable.item_background_selected);
                    binding.selected.isChecked = true;
                } else {
                    itemView.setBackgroundResource(R.drawable.item_background);
                    binding.selected.isChecked = false;
                }

            }
        }
    }

    private fun listemers(item: Customer, itemView: View, binding: ViewCustomerBinding) {
        itemView.setOnLongClickListener {
            if (!selectedMode) {
                actions.onStartSelectMode(item)
            }
            true
        }

        itemView.setOnClickListener {
            if (selectedMode)
            {
                item.isSelected=!item.isSelected
                selectedMode = item.isSelected
                actions.itemHasClicked(item)

            }
            else{
                val context = it.context
                val intent = Intent(context, DetailedActivity::class.java)
                intent.putExtra(ConstantesFramework.CUSTOMERDETAILED, item.id)
                context.startActivity(intent)
            }
        }

        with(binding){
            selected.setOnClickListener {
                if (selectedMode) {

                    if (binding.selected.isChecked) {
                        item.isSelected = true
                        itemView.setBackgroundColor(android.graphics.Color.DKGRAY)
                        selectedCustomers.add(item)
                    } else {
                        item.isSelected = false
                        itemView.setBackgroundColor(android.graphics.Color.argb(255, 0, 255, 255))
                        selectedCustomers.remove(item)
                    }
                    actions.itemHasClicked(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    selectedCustomers.remove(currentList[position])
                    actions.onDelete(currentList[position])
                    if (selectedMode)
                        actions.itemHasClicked(currentList[position])
                }
            }
        }
    }
}


