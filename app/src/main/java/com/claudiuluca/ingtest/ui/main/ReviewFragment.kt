package com.claudiuluca.ingtest.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.claudiuluca.ingtest.R
import com.claudiuluca.ingtest.databinding.ReviewFragmentBinding
import kotlinx.android.synthetic.main.review_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReviewFragment : Fragment() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var reviewFragmentBinding: ReviewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reviewFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.review_fragment, container, false)


        return reviewFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reviewFragmentBinding.viewmodel = viewModel

        btSend.setOnClickListener {
            composeEmail(arrayOf("bogus@bogus.com"))
        }
    }

    private fun composeEmail(addresses: Array<String>) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, "Test ING")
            putExtra(Intent.EXTRA_TEXT, tvReview.text)
        }
        if (activity?.packageManager?.let {
                intent.resolveActivity(it)
            } != null) {
            startActivity(intent)
        }
    }
}