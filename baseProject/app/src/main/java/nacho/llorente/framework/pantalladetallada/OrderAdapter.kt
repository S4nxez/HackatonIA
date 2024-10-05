package nacho.llorente.framework.pantalladetallada

import nacho.llorente.framework.pantallamain.SwipeGesture
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nacho.llorente.R
import nacho.llorente.databinding.ViewOrderBinding
import nacho.llorente.domain.modelo.Order

class OrderAdapter(
    val context: Context,
    val actions: OrderActions
) :
    ListAdapter<Order, OrderAdapter.ItemViewholder>(DiffCallback()) {

    interface OrderActions {
        fun onDelete(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_order, parent, false)
        )
    }

    fun deleteItem(position: Int) {
        val order = currentList[position]
        actions.onDelete(order)

        val updatedList = currentList.toMutableList().apply {
            removeAt(position)
        }

        submitList(updatedList)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewOrderBinding.bind(itemView)

        fun bind(item: Order) {
            with(binding) {
                tvId.text = "id: "+item.id.toString()
                tvFecha.text = "fecha: "+item.orderDate.toString()
                tvTableId.text = "mesa: "+item.tableId.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                deleteItem(position)
            }
        }
    }
}


