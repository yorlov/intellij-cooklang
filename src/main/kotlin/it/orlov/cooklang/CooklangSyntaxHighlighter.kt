package it.orlov.cooklang

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.KEYWORD
import com.intellij.openapi.editor.HighlighterColors.BAD_CHARACTER
import com.intellij.openapi.editor.HighlighterColors.TEXT
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import it.orlov.cooklang.psi.CooklangTypes

class CooklangSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer() = CooklangLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType) = when (tokenType) {
        CooklangTypes.AT, CooklangTypes.SIGN, CooklangTypes.TILDE, CooklangTypes.PERCENT, CooklangTypes.LEFT_BRACE, CooklangTypes.RIGHT_BRACE, CooklangTypes.COLON -> arrayOf(KEYWORD)
        CooklangTypes.PLAIN_WORD -> arrayOf(TEXT)
        TokenType.BAD_CHARACTER -> arrayOf(BAD_CHARACTER)
        else -> emptyArray()
    }
}