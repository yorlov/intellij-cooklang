package it.orlov.cooklang

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class CooklangPsiFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, CooklangLanguage) {
    override fun getFileType() = CooklangFileType
    override fun toString() = "Cooklang File"
}