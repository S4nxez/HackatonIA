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
import nacho.llorente.databinding.ViewClassBinding
import nacho.llorente.domain.modelo.Class
import nacho.llorente.framework.common.ConstantesFramework
import nacho.llorente.framework.pantalladetallada.DetailedActivity


class ClassAdapter(
    val context: Context,
    val actions: ClassActions
) :    ListAdapter<Class, ClassAdapter.ItemViewholder>(DiffCallback()) {
    private var selectedClasses = mutableSetOf<Class>()


    private var selectedMode: Boolean = false


    interface ClassActions {
        fun onDelete(clase: Class)
        fun onStartSelectMode(clase: Class)
        fun itemHasClicked(clase: Class)
    }

    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }

    fun resetSelectMode() {
        selectedMode = false
        selectedClasses.clear()
        notifyDataSetChanged()
    }


    fun setSelectedItems(classesSeleccionadas: List<Class>) {
        selectedClasses.clear()
        selectedClasses.addAll(classesSeleccionadas)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_class, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    //todo: cambiar la clase a la de class del fxml con el binding
    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewClassBinding.bind(itemView)

        fun bind(item: Class) {
            listemers(item, itemView, binding)
            with(binding) {
                tvNombre.text = item.name
                tvId.text = item.id.toString()
                if (selectedMode)
                    selected.visibility = View.VISIBLE
                else {
                    item.isSelected = false
                    selected.visibility = View.GONE
                }

                if (selectedClasses.contains(item)) {
                    itemView.setBackgroundResource(R.drawable.item_background_selected);
                    binding.selected.isChecked = true;
                } else {
                    itemView.setBackgroundResource(R.drawable.item_background);
                    binding.selected.isChecked = false;
                }

            }
        }
    }

    private fun listemers(item: Class, itemView: View, binding: ViewClassBinding) {
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
            else{ //cambiar pantalla si no está en modo seleccion, es el usuario que se ha seleccionado
                val context = it.context
                val intent = Intent(context, DetailedActivity::class.java)
                intent.putExtra(ConstantesFramework.CLASSDETAILED, item.id)
                context.startActivity(intent)
            }
        }

        with(binding){
            selected.setOnClickListener {
                if (selectedMode) {

                    if (binding.selected.isChecked) {
                        item.isSelected = true
                        itemView.setBackgroundColor(android.graphics.Color.DKGRAY)
                        selectedClasses.add(item)
                    } else {
                        item.isSelected = false
                        itemView.setBackgroundColor(android.graphics.Color.argb(255, 0, 255, 255))
                        selectedClasses.remove(item)
                    }
                    actions.itemHasClicked(item)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Class>() {

        override fun areItemsTheSame(oldItem: Class, newItem: Class): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Class, newItem: Class): Boolean {
            return oldItem == newItem
        }

    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    val position = viewHolder.bindingAdapterPosition
                    selectedClasses.remove(currentList[position])
                    actions.onDelete(currentList[position])
                    if (selectedMode)
                        actions.itemHasClicked(currentList[position])
                }
            }
        }
    }
}


