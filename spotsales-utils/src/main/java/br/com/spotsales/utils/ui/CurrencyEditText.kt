package br.com.spotsales.utils.ui

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText
import br.com.spotsales.utils.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Currency
import java.util.Locale
import kotlin.math.pow

class CurrencyEditText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private var mGroupDivider: Char = ' '
    private var mMonetaryDivider: Char = ' '
    private var mLocale: String? = ""
    private var mShowSymbol: Boolean = false
    private var mEraseWhenZero: Boolean = false
    private var mDecimalPoints: Int = 0

    private var groupDivider: Char = ' '
    private var monetaryDivider: Char = ' '

    private var locale: Locale? = null
    private var numberFormat: DecimalFormat? = null

    private var currencySymbol: String? = null

    /**
     * @return Int value of count of monetary decimals.
     */
    var fractionDigit: Int = 0
        private set

    private val defaultLocale: Locale
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context.resources.configuration.locales.get(0)
        else
            context.resources.configuration.locale

    private val onTextChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.isEmpty())
                return

            removeTextChangedListener(this)

            /*
             * Clear input to get clean text before format
             * '\u0020' is empty character
             */
            var text = s.toString()
            text = text.replace("[^\\d]".toRegex(), "")

            if (checkErasable(text)) {
                getText().clear()
            } else {
                try {
                    text = format(text)
                } catch (e: NumberFormatException) {
                    Log.e(javaClass.canonicalName, e.message)
                } catch (e: NullPointerException) {
                    Log.e(javaClass.canonicalName, e.message)
                }

                setText(text)
                setSelection(text.length)
            }

            addTextChangedListener(this)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    /**
     * @return double value for current text
     */
    val currencyDouble: Double
        @Throws(NumberFormatException::class, NullPointerException::class)
        get() {
            var text = text.toString()
            text = text.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                    .replace(".", "").replace(" ", "")
                    .replace(currencySymbol!!, "").trim { it <= ' ' }

            return if (showSymbol())
                java.lang.Double.parseDouble(text.replace(currencySymbol!!, "")) / 10.0.pow(fractionDigit.toDouble())
            else
                java.lang.Double.parseDouble(text) / 10.0.pow(fractionDigit.toDouble())
        }

    /**
     * @return String value for current text
     */
    val currencyText: String
        get() = if (showSymbol())
            text.toString().replace(currencySymbol!!, "")
        else
            text.toString()

    init {
        this.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.currencyEditText, 0, 0)

        try {
            if (a.getString(R.styleable.currencyEditText_groupDivider) != null) {
                this.mGroupDivider = a.getString(R.styleable.currencyEditText_groupDivider)!![0]
                this.groupDivider = mGroupDivider
            }

            if (a.getString(R.styleable.currencyEditText_monetaryDivider) != null) {
                this.mMonetaryDivider = a.getString(R.styleable.currencyEditText_monetaryDivider)!![0]
                this.monetaryDivider = mMonetaryDivider
            }

            if (a.getString(R.styleable.currencyEditText_locale) == null)
                this.locale = defaultLocale
            else
                this.mLocale = a.getString(R.styleable.currencyEditText_locale)

            if (a.getString(R.styleable.currencyEditText_showSymbol) != null)
                this.mShowSymbol = a.getBoolean(R.styleable.currencyEditText_showSymbol, false)

            if (a.getString(R.styleable.currencyEditText_eraseWhenZero) != null)
                this.mEraseWhenZero = a.getBoolean(R.styleable.currencyEditText_eraseWhenZero, false)

            if (a.getString(R.styleable.currencyEditText_decimalPoints) != null) {
                this.mDecimalPoints = a.getInt(R.styleable.currencyEditText_decimalPoints, 0)
                this.fractionDigit = mDecimalPoints
            }

            if (mLocale == "") {
                locale = defaultLocale
            } else {
                if (mLocale!!.contains("-"))
                    mLocale = mLocale!!.replace("-", "_")

                val l = mLocale!!.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                locale = if (l.size > 1) {
                    Locale(l[0], l[1])
                } else {
                    Locale("", mLocale)
                }
            }

            initSettings()
        } finally {
            a.recycle()
        }

        this.addTextChangedListener(onTextChangeListener)
    }

    /***
     * If user does not provide a valid locale it throws IllegalArgumentException.
     *
     * If throws an IllegalArgumentException the locale sets to default locale
     */
    private fun initSettings() {
        var success = false
        while (!success) {
            try {
                if (fractionDigit == 0) {
                    fractionDigit = Currency.getInstance(locale).defaultFractionDigits
                }

                val symbols = DecimalFormatSymbols.getInstance(locale)
                if (mGroupDivider.toInt() > 0)
                    symbols.groupingSeparator = mGroupDivider
                groupDivider = symbols.groupingSeparator

                if (mMonetaryDivider.toInt() > 0)
                    symbols.monetaryDecimalSeparator = mMonetaryDivider
                monetaryDivider = symbols.monetaryDecimalSeparator

                currencySymbol = symbols.currencySymbol

                val df = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
                numberFormat = DecimalFormat(df.toPattern(), symbols)

                if (mDecimalPoints > 0) {
                    numberFormat!!.minimumFractionDigits = mDecimalPoints
                }

                success = true
            } catch (e: IllegalArgumentException) {
                Log.e(javaClass.canonicalName, e.message)
                locale = defaultLocale
            }

        }
    }

    /***
     * It resets text currently displayed If user changes separators or locale etc.
     */
    private fun resetText() {
        var s = text.toString()
        if (s.isEmpty()) {
            initSettings()
            return
        }

        s = s.replace(groupDivider, '\u0020').replace(monetaryDivider, '\u0020')
                .replace(".", "").replace(" ", "")
                .replace(currencySymbol!!, "").trim { it <= ' ' }
        try {
            initSettings()
            s = format(s)
            removeTextChangedListener(onTextChangeListener)
            setText(s)
            setSelection(s.length)
            addTextChangedListener(onTextChangeListener)
        } catch (e: NumberFormatException) {
            Log.e(javaClass.canonicalName, e.message)
        } catch (e: NullPointerException) {
            Log.e(javaClass.canonicalName, e.message)
        }

    }

    @Throws(NumberFormatException::class, NullPointerException::class)
    private fun format(text: String): String {
        return if (mShowSymbol)
            numberFormat!!.format(java.lang.Double.parseDouble(text) / 10.0.pow(fractionDigit.toDouble()))
        else
            numberFormat!!.format(java.lang.Double.parseDouble(text) / 10.0.pow(fractionDigit.toDouble()))
                    .replace(currencySymbol.orEmpty(), "")
    }

    private fun checkErasable(text: String): Boolean {
        if (mEraseWhenZero) {
            try {
                return Integer.valueOf(text) == 0
            } catch (e: NumberFormatException) {
                Log.e(javaClass.canonicalName, e.message)
            }

        }

        return false
    }

    /***
     * returns the decimal separator for current locale
     * for example; input value 1,234.56
     * returns ','
     *
     * @return decimal separator char
     */
    fun getGroupDivider(): Char = groupDivider

    /***
     * sets how to divide decimal value and fractions
     * for example; If you want formatting like this
     * for input value 1,234.56
     * set ','
     * @param groupDivider char
     */
    fun setGroupDivider(groupDivider: Char) {
        this.mGroupDivider = groupDivider
        resetText()
    }

    /***
     * returns the monetary separator for current locale
     * for example; input value 1,234.56
     * returns '.'
     *
     * @return monetary separator char
     */
    fun getMonetaryDivider(): Char = monetaryDivider

    /***
     * sets how to divide decimal value and fractions
     * for example; If you want formatting like this
     * for input value 1,234.56
     * set '.'
     * @param monetaryDivider char
     */
    fun setMonetaryDivider(monetaryDivider: Char) {
        this.mMonetaryDivider = monetaryDivider
        resetText()
    }

    /***
     *
     * @return current locale
     */
    fun getLocale(): Locale? = locale

    /***
     * Sets locale which desired currency format
     *
     * @param locale Desired locale
     */
    fun setLocale(locale: Locale) {
        this.locale = locale
        resetText()
    }

    /**
     * @return true if currency symbol of current locale is showing
     */
    fun showSymbol(): Boolean = this.mShowSymbol

    /***
     * Sets if currency symbol of current locale shows
     *
     * @param showSymbol Set true if you want to see currency symbol
     */
    fun showSymbol(showSymbol: Boolean) {
        this.mShowSymbol = showSymbol
        resetText()
    }

    /**
     * Changes count of monetary decimal.
     * For instance;
     * if you set 4, it formats like 12,3456 for 123456
     *
     * @param decimalPoints The count of monetary decimals
     */
    fun setDecimals(decimalPoints: Int) {
        this.mDecimalPoints = decimalPoints
        this.fractionDigit = decimalPoints
        resetText()
    }
}