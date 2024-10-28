package com.example.petfinderdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PetAdapter(private var breedList : List<String>, private var imageUrls : List<String>): RecyclerView.Adapter<PetAdapter.PetViewHolder>(){

    inner class PetViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val breedName : TextView = itemView.findViewById(R.id.petName)
        private val breedImage : ImageView = itemView.findViewById(R.id.petImage)
        private val breedAge : TextView = itemView.findViewById(R.id.petAge)
        fun bindItem(name : String, imageUrl : String){
            breedName.text = "Breed: $name"
            breedAge.text = "Age : ${getRandomAge()}"
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_pets_logo)
                .error(R.drawable.ic_pets_logo)
                .into(breedImage)
        }
        private fun getRandomAge() : Int {
            return (1..5).random()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pet_item,parent,false)
        return PetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return breedList.size
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val name = breedList[position]
        val image = imageUrls[position]
        holder.bindItem(name,image)
    }
    fun updateData(newBreedList: List<String>, newUrlsList : List<String>) {
        breedList = newBreedList
        imageUrls = newUrlsList
        notifyDataSetChanged()
    }

}


