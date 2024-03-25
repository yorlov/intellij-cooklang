package it.orlov.cooklang.psi

import com.intellij.psi.PsiElement

object CooklangPsiUtil {

    @JvmStatic
    fun getName(ingredient: CooklangIngredient): String? {
        return getNameIdentifier(ingredient)?.text
    }

    @JvmStatic
    fun setName(ingredient: CooklangIngredient, name: String): PsiElement {
        TODO("setName not implemented")
    }

    @JvmStatic
    fun getNameIdentifier(ingredient: CooklangIngredient): PsiElement? {
        val single = ingredient.singleIngredient?.singleName
        val multi = ingredient.multiIngredient?.multiName

        return single ?: multi
    }
}