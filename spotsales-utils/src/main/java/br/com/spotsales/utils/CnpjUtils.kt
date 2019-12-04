package br.com.spotsales.utils

object CnpjUtils {

    fun validateCnpj(cnpj: String): Boolean {
        var document = cnpj

        return if (document.substring(0, 1) != "") {
            try {
                document = document.replace('.', ' ')
                document = document.replace('/', ' ')
                document = document.replace('-', ' ')
                document = document.replace(" ".toRegex(), "")

                var soma = 0
                var dig: Int
                var cnpjCalc = document.substring(0, 12)

                if (document.length != 14) {
                    return false
                }

                val chrCnpj = document.toCharArray()

                for (i in 0..3) {
                    if (chrCnpj[i].toInt() - 48 in 0..9) {
                        soma += (chrCnpj[i].toInt() - 48) * (6 - (i + 1))
                    }
                }

                for (i in 0..7) {
                    if (chrCnpj[i + 4].toInt() - 48 in 0..9) {
                        soma += (chrCnpj[i + 4].toInt() - 48) * (10 - (i + 1))
                    }
                }

                dig = 11 - soma % 11
                cnpjCalc += if (dig == 10 || dig == 11) "0" else dig.toString()

                soma = 0
                for (i in 0..4) {
                    if (chrCnpj[i].toInt() - 48 in 0..9) {
                        soma += (chrCnpj[i].toInt() - 48) * (7 - (i + 1))
                    }
                }
                for (i in 0..7) {
                    if (chrCnpj[i + 5].toInt() - 48 in 0..9) {
                        soma += (chrCnpj[i + 5].toInt() - 48) * (10 - (i + 1))
                    }
                }
                dig = 11 - soma % 11
                cnpjCalc += if (dig == 10 || dig == 11) "0" else Integer.toString(dig)

                document == cnpjCalc
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }
}
