package com.example.proyecto_final
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.databinding.ItemUsuarioBinding

class UsuarioAdapter: RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    private var usuarios: ArrayList<Usuario> = ArrayList()
    private var OnClickItem: ((Usuario) -> Unit)? = null
    private var OnClickDeleteItem: ((Usuario) -> Unit)? = null

    fun setOnClickItem(callback: (Usuario) -> Unit) {
        this.OnClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (Usuario) -> Unit) {
        this.OnClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.bindViewEstudiante(usuario)

        holder.itemView.setOnClickListener {
            OnClickItem?.invoke(usuario)
        }

        holder.binding.btnEliminar.setOnClickListener {
            OnClickDeleteItem?.invoke(usuario)
        }
    }

    override fun getItemCount(): Int = usuarios.size

    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUsuarioBinding.bind(itemView)
        fun bindViewEstudiante(usuario: Usuario) {
            with(binding) {
                nombreUsuario.text = usuario.nombre
            }
        }
    }

    fun refreshUsuario(usuarios: ArrayList<Usuario>)
    {
        this.usuarios = usuarios
        notifyDataSetChanged() //recarga el adaptador
    }
}
