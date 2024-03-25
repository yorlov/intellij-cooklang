package it.orlov.cooklang

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoType.TEXT_ATTRIBUTES
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.*
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import it.orlov.cooklang.psi.*

class CooklangHighlightVisitor : HighlightVisitor {

    private var highlightInfoHolder: HighlightInfoHolder? = null

    override fun suitableForFile(file: PsiFile) = file is CooklangPsiFile

    override fun visit(element: PsiElement) {
        val color = when (element) {
            is CooklangSingleName, is CooklangMultiName -> FUNCTION_DECLARATION
            is CooklangComment -> LINE_COMMENT
            is CooklangQuantity, is CooklangMetadataValue -> NUMBER
            is CooklangMetadataKey -> STRING
            else -> return
        }

        val highlightInfo = HighlightInfo.newHighlightInfo(TEXT_ATTRIBUTES)
            .range(element)
            .textAttributes(color)
            .create()

        highlightInfoHolder?.add(highlightInfo)
    }

    override fun analyze(file: PsiFile, updateWholeFile: Boolean, holder: HighlightInfoHolder, action: Runnable): Boolean {
        this.highlightInfoHolder = holder
        action.run()
        return true
    }

    override fun clone() = CooklangHighlightVisitor()
}