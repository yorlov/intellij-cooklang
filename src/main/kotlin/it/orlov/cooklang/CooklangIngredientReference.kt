package it.orlov.cooklang

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult
import it.orlov.cooklang.psi.CooklangIngredient


class CooklangIngredientReference(element: CooklangIngredient) : PsiReferenceBase.Poly<CooklangIngredient>(element, TextRange.from(1, element.name!!.length), true) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val ingredients = CooklangUtil.findSameIngredient(element)
        return ingredients.map(::PsiElementResolveResult).toTypedArray()
    }

}