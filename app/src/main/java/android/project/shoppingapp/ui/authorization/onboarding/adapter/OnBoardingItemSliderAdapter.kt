package android.project.shoppingapp.ui.authorization.onboarding.adapter

import android.project.shoppingapp.data.model.OnBoardingItem
import android.project.shoppingapp.databinding.OnboardingItemLayoutBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnBoardingItemSliderAdapter(private val introSlides: List<OnBoardingItem>)
    : RecyclerView.Adapter<OnBoardingItemSliderAdapter.IntroSlideViewHolder>(){
    var onTextPassed: ((textView: TextView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            OnboardingItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    inner class IntroSlideViewHolder(private val binding: OnboardingItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(introSlide: OnBoardingItem) {
            binding.textTitle.text = introSlide.title
            binding.textDescription.text = introSlide.description
            onTextPassed?.invoke(binding.textTitle)
        }
    }
}