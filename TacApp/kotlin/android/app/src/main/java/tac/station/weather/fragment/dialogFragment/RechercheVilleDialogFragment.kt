package tac.station.weather.fragment.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import tac.station.weather.databinding.RechercheDialogFragmentBinding
import tac.station.weather.listener.RechercheListener


class RechercheVilleDialogFragment(private var listener: RechercheListener) : DialogFragment() {

    private lateinit var binding: RechercheDialogFragmentBinding

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        binding = RechercheDialogFragmentBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)

        binding.recherche.doOnTextChanged { text, _, _, _ -> listener.onTextChanged(text.toString()) }

        return builder.create()
    }


}
