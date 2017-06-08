package jp.co.mstyle.loverecipe.presentation.adapter


import android.support.v7.widget.RecyclerView
import java.util.*


abstract class RecyclerArrayAdapter<T, VH : RecyclerView.ViewHolder> constructor(private val items: MutableList<T> = ArrayList<T>()) : RecyclerView.Adapter<VH>() {

    fun getPosition(item: T): Int = items.indexOf(item)

    fun getItem(position: Int): T = items[position]

    fun getItems(): MutableList<T> = items

    override fun getItemCount(): Int = items.size

    fun add(item: T) {
        val position = items.size
        items.add(item)
        notifyItemInserted(position)
    }

    fun insert(item: T, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun change(position: Int) {
        notifyItemChanged(position)
    }

    fun change(item: T) {
        val position = items.indexOf(item)
        if (position != -1) {
            items[position] = item
            notifyItemChanged(position)
        }
    }

    fun change(item: T, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun addAll(collection: Collection<T>) {
        val itemCount = collection.size
        val positionStart = items.size
        items.addAll(collection)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun remove(item: T) {
        val position = items.indexOf(item)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun remove(position: Int): T {
        val prev: T = items.removeAt(position)
        notifyItemRemoved(position)
        return prev
    }

    fun removeNotNotify(position: Int): T {
        val prev: T = items.removeAt(position)
        return prev
    }

    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun move(from: Int, to: Int) {
        val prev = items.removeAt(from)
        items.add(if (to > from) to - 1 else to, prev)
        notifyItemMoved(from, to)
    }

    fun sort(comparator: Comparator<in T>) {
        Collections.sort(items, comparator)
        notifyDataSetChanged()
    }
}