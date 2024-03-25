package it.orlov.cooklang

import com.intellij.openapi.vfs.findPsiFile
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.childrenOfType
import it.orlov.cooklang.psi.CooklangIngredient
import it.orlov.cooklang.psi.CooklangStep

object CooklangUtil {
    fun findSameIngredient(current: CooklangIngredient): List<CooklangIngredient> {
        return FileTypeIndex.getFiles(CooklangFileType, GlobalSearchScope.allScope(current.project))
            .mapNotNull { virtualFile -> virtualFile.findPsiFile(current.project) }
            .flatMap { cookFile -> cookFile.childrenOfType<CooklangStep>() }
            .flatMap { step -> step.childrenOfType<CooklangIngredient>() }
            .filter { ingredient -> current.name == ingredient.name }
            .distinctBy { ingredient -> ingredient.containingFile }
    }
}