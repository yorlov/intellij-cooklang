package it.orlov.cooklang.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.ContributedReferenceHost
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceService
import it.orlov.cooklang.CooklangIcons
import javax.swing.Icon

abstract class CooklangIngredientImplMixin(node: ASTNode) : ASTWrapperPsiElement(node), ContributedReferenceHost, CooklangIngredient {

    override fun getName(): String? {
        return CooklangPsiUtil.getName(this)
    }

    override fun setName(name: String): PsiElement {
        return CooklangPsiUtil.setName(this, name)
    }

    override fun getNameIdentifier(): PsiElement? {
        return CooklangPsiUtil.getNameIdentifier(this)
    }

    override fun getReferences(): Array<PsiReference> {
        return PsiReferenceService.getService().getContributedReferences(this)
    }

    override fun getPresentation(): ItemPresentation {
        return object : ItemPresentation {
            override fun getPresentableText() = name
            override fun getLocationString() = node.psi.containingFile.virtualFile.nameWithoutExtension
            override fun getIcon(unused: Boolean) = getIcon(ICON_FLAG_VISIBILITY)
        }
    }

    override fun getIcon(flags: Int): Icon? {
        return CooklangIcons.INGREDIENT
    }
}