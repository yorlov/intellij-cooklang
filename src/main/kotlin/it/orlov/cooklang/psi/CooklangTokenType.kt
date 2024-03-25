package it.orlov.cooklang.psi

import com.intellij.psi.tree.IElementType
import it.orlov.cooklang.CooklangLanguage

class CooklangTokenType(debugName: String) : IElementType(debugName, CooklangLanguage) {
    override fun toString() = "CooklangTokenType.${super.toString()}"
}