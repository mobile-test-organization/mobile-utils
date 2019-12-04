package br.com.spotsales.utils.masks

import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import java.lang.ref.WeakReference

class CpfCnpjMaskedChangedListener(field: EditText,
                                   valueListener: MaskTextListener?) : MaskedTextChangedListener.ValueListener {

    companion object {
        private const val CPF_MASK = "[000]{.}[000]{.}[000]{-}[00]"
        private const val CNPJ_MASK = "[00]{.}[000]{.}[000]{/}[0000]{-}[00]"
        private const val CPF_FULL_LENGTH = 14

        private val AFFINITY_MASKS = listOf("[000]{.}[000]{.}[000]{-}[009]")

        fun installOn(field: EditText, valueListener: MaskTextListener? = null): CpfCnpjMaskedChangedListener {
            val listener = CpfCnpjMaskedChangedListener(field, valueListener)

            field.addTextChangedListener(listener.cpfMask)
            field.onFocusChangeListener = listener.cpfMask

            return listener
        }
    }

    private val cpfMask: MaskedTextChangedListener = MaskedTextChangedListener(CPF_MASK, AFFINITY_MASKS, field, this)
    private val cnpjMask: MaskedTextChangedListener = MaskedTextChangedListener(CNPJ_MASK, field, this)
    private val textField: WeakReference<EditText> = WeakReference(field)
    private val listener: WeakReference<MaskTextListener?> = WeakReference(valueListener)

    private var currentMask: MaskedTextChangedListener = cpfMask

    private fun findMaskArrangement(filled: Boolean,
                                    value: String): Pair<MaskedTextChangedListener, MaskedTextChangedListener>? =
            if (filled && value.length > CPF_FULL_LENGTH && currentMask != cnpjMask) {
                cnpjMask to cpfMask
            } else if (value.length <= CPF_FULL_LENGTH && currentMask != cpfMask) {
                cpfMask to cnpjMask
            } else {
                null
            }

    private fun changeMasks(newMask: MaskedTextChangedListener, oldMask: MaskedTextChangedListener, value: String) {
        textField.get()?.also { field ->
            currentMask = newMask

            field.removeTextChangedListener(oldMask)
            field.addTextChangedListener(newMask)

            newMask.setText(value)

            field.onFocusChangeListener = newMask
        }
    }

    private fun triggerFilledListeners(maskFilled: Boolean, extractedValue: String) =
            listener.get()?.onTextChanged(currentMask == cnpjMask, maskFilled, extractedValue)

    //region ~~~~ MaskedTextChangedListener.ValueListener ~~~~
    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
        findMaskArrangement(maskFilled, extractedValue)?.also { (newMask, oldMask) ->
            changeMasks(newMask, oldMask, extractedValue)
        } ?: triggerFilledListeners(maskFilled, extractedValue)
    }
    //endregion

    //region ~~~~ MaskValueListener ~~~~
    interface MaskTextListener {
        fun onTextChanged(isCnpj: Boolean, maskFilled: Boolean, extractedValue: String)
    }
    //endregion
}
