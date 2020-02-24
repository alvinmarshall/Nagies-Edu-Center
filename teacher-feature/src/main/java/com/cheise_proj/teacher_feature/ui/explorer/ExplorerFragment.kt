package com.cheise_proj.teacher_feature.ui.explorer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cheise_proj.teacher_feature.R
import kotlinx.android.synthetic.main.fragment_explorer.*

/**
 * A simple [Fragment] subclass.
 */
class ExplorerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explorer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_explore_assignment.setOnClickListener {
            navigateToAssignmentExplorer()
        }
        btn_explorer_report.setOnClickListener {
            navigateToReportExplorer()
        }
    }

    private fun navigateToReportExplorer() {
        val action = ExplorerFragmentDirections.actionExplorerFragmentToReportExplorerFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAssignmentExplorer() {
        val action = ExplorerFragmentDirections.actionExplorerFragmentToAssignmentExplorerFragment()
        findNavController().navigate(action)
    }


}
